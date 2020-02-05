package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.Files;
import com.kidole.sport.repository.FilesRepository;
import com.kidole.sport.repository.search.FilesSearchRepository;
import com.kidole.sport.service.FilesService;
import com.kidole.sport.service.dto.FilesDTO;
import com.kidole.sport.service.mapper.FilesMapper;
import com.kidole.sport.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.kidole.sport.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FilesResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class FilesResourceIT {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FILE_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FILE_PUBLIC = false;
    private static final Boolean UPDATED_FILE_PUBLIC = true;

    private static final byte[] DEFAULT_FILE_TO_UPLOAD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE_TO_UPLOAD = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_TO_UPLOAD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_TO_UPLOAD_CONTENT_TYPE = "image/png";

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private FilesMapper filesMapper;

    @Autowired
    private FilesService filesService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.FilesSearchRepositoryMockConfiguration
     */
    @Autowired
    private FilesSearchRepository mockFilesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restFilesMockMvc;

    private Files files;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilesResource filesResource = new FilesResource(filesService);
        this.restFilesMockMvc = MockMvcBuilders.standaloneSetup(filesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Files createEntity(EntityManager em) {
        Files files = new Files()
            .fileName(DEFAULT_FILE_NAME)
            .fileType(DEFAULT_FILE_TYPE)
            .filePublic(DEFAULT_FILE_PUBLIC)
            .fileToUpload(DEFAULT_FILE_TO_UPLOAD)
            .fileToUploadContentType(DEFAULT_FILE_TO_UPLOAD_CONTENT_TYPE);
        return files;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Files createUpdatedEntity(EntityManager em) {
        Files files = new Files()
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .filePublic(UPDATED_FILE_PUBLIC)
            .fileToUpload(UPDATED_FILE_TO_UPLOAD)
            .fileToUploadContentType(UPDATED_FILE_TO_UPLOAD_CONTENT_TYPE);
        return files;
    }

    @BeforeEach
    public void initTest() {
        files = createEntity(em);
    }

    @Test
    @Transactional
    public void createFiles() throws Exception {
        int databaseSizeBeforeCreate = filesRepository.findAll().size();

        // Create the Files
        FilesDTO filesDTO = filesMapper.toDto(files);
        restFilesMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isCreated());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeCreate + 1);
        Files testFiles = filesList.get(filesList.size() - 1);
        assertThat(testFiles.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testFiles.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testFiles.isFilePublic()).isEqualTo(DEFAULT_FILE_PUBLIC);
        assertThat(testFiles.getFileToUpload()).isEqualTo(DEFAULT_FILE_TO_UPLOAD);
        assertThat(testFiles.getFileToUploadContentType()).isEqualTo(DEFAULT_FILE_TO_UPLOAD_CONTENT_TYPE);

        // Validate the Files in Elasticsearch
        verify(mockFilesSearchRepository, times(1)).save(testFiles);
    }

    @Test
    @Transactional
    public void createFilesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filesRepository.findAll().size();

        // Create the Files with an existing ID
        files.setId(1L);
        FilesDTO filesDTO = filesMapper.toDto(files);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilesMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Files in Elasticsearch
        verify(mockFilesSearchRepository, times(0)).save(files);
    }


    @Test
    @Transactional
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = filesRepository.findAll().size();
        // set the field null
        files.setFileName(null);

        // Create the Files, which fails.
        FilesDTO filesDTO = filesMapper.toDto(files);

        restFilesMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isBadRequest());

        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = filesRepository.findAll().size();
        // set the field null
        files.setFileType(null);

        // Create the Files, which fails.
        FilesDTO filesDTO = filesMapper.toDto(files);

        restFilesMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isBadRequest());

        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFilePublicIsRequired() throws Exception {
        int databaseSizeBeforeTest = filesRepository.findAll().size();
        // set the field null
        files.setFilePublic(null);

        // Create the Files, which fails.
        FilesDTO filesDTO = filesMapper.toDto(files);

        restFilesMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isBadRequest());

        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get all the filesList
        restFilesMockMvc.perform(get("/api/files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(files.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].filePublic").value(hasItem(DEFAULT_FILE_PUBLIC.booleanValue())))
            .andExpect(jsonPath("$.[*].fileToUploadContentType").value(hasItem(DEFAULT_FILE_TO_UPLOAD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileToUpload").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_TO_UPLOAD))));
    }
    
    @Test
    @Transactional
    public void getFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        // Get the files
        restFilesMockMvc.perform(get("/api/files/{id}", files.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(files.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE))
            .andExpect(jsonPath("$.filePublic").value(DEFAULT_FILE_PUBLIC.booleanValue()))
            .andExpect(jsonPath("$.fileToUploadContentType").value(DEFAULT_FILE_TO_UPLOAD_CONTENT_TYPE))
            .andExpect(jsonPath("$.fileToUpload").value(Base64Utils.encodeToString(DEFAULT_FILE_TO_UPLOAD)));
    }

    @Test
    @Transactional
    public void getNonExistingFiles() throws Exception {
        // Get the files
        restFilesMockMvc.perform(get("/api/files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        int databaseSizeBeforeUpdate = filesRepository.findAll().size();

        // Update the files
        Files updatedFiles = filesRepository.findById(files.getId()).get();
        // Disconnect from session so that the updates on updatedFiles are not directly saved in db
        em.detach(updatedFiles);
        updatedFiles
            .fileName(UPDATED_FILE_NAME)
            .fileType(UPDATED_FILE_TYPE)
            .filePublic(UPDATED_FILE_PUBLIC)
            .fileToUpload(UPDATED_FILE_TO_UPLOAD)
            .fileToUploadContentType(UPDATED_FILE_TO_UPLOAD_CONTENT_TYPE);
        FilesDTO filesDTO = filesMapper.toDto(updatedFiles);

        restFilesMockMvc.perform(put("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isOk());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
        Files testFiles = filesList.get(filesList.size() - 1);
        assertThat(testFiles.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testFiles.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testFiles.isFilePublic()).isEqualTo(UPDATED_FILE_PUBLIC);
        assertThat(testFiles.getFileToUpload()).isEqualTo(UPDATED_FILE_TO_UPLOAD);
        assertThat(testFiles.getFileToUploadContentType()).isEqualTo(UPDATED_FILE_TO_UPLOAD_CONTENT_TYPE);

        // Validate the Files in Elasticsearch
        verify(mockFilesSearchRepository, times(1)).save(testFiles);
    }

    @Test
    @Transactional
    public void updateNonExistingFiles() throws Exception {
        int databaseSizeBeforeUpdate = filesRepository.findAll().size();

        // Create the Files
        FilesDTO filesDTO = filesMapper.toDto(files);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFilesMockMvc.perform(put("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Files in Elasticsearch
        verify(mockFilesSearchRepository, times(0)).save(files);
    }

    @Test
    @Transactional
    public void deleteFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);

        int databaseSizeBeforeDelete = filesRepository.findAll().size();

        // Delete the files
        restFilesMockMvc.perform(delete("/api/files/{id}", files.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Files in Elasticsearch
        verify(mockFilesSearchRepository, times(1)).deleteById(files.getId());
    }

    @Test
    @Transactional
    public void searchFiles() throws Exception {
        // Initialize the database
        filesRepository.saveAndFlush(files);
        when(mockFilesSearchRepository.search(queryStringQuery("id:" + files.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(files), PageRequest.of(0, 1), 1));
        // Search the files
        restFilesMockMvc.perform(get("/api/_search/files?query=id:" + files.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(files.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].filePublic").value(hasItem(DEFAULT_FILE_PUBLIC.booleanValue())))
            .andExpect(jsonPath("$.[*].fileToUploadContentType").value(hasItem(DEFAULT_FILE_TO_UPLOAD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileToUpload").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_TO_UPLOAD))));
    }
}
