package com.web.rest;

import com.KidoleApp;
import com.domain.Files;
import com.repository.FilesRepository;
import com.service.FilesService;
import com.service.dto.FilesDTO;
import com.service.mapper.FilesMapper;
import com.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FilesResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class FilesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLIQUE = "AAAAAAAAAA";
    private static final String UPDATED_PUBLIQUE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private FilesMapper filesMapper;

    @Autowired
    private FilesService filesService;

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
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .publique(DEFAULT_PUBLIQUE)
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE);
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
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .publique(UPDATED_PUBLIQUE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE);
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
        assertThat(testFiles.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFiles.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFiles.getPublique()).isEqualTo(DEFAULT_PUBLIQUE);
        assertThat(testFiles.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testFiles.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].publique").value(hasItem(DEFAULT_PUBLIQUE)))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))));
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.publique").value(DEFAULT_PUBLIQUE))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)));
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
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .publique(UPDATED_PUBLIQUE)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE);
        FilesDTO filesDTO = filesMapper.toDto(updatedFiles);

        restFilesMockMvc.perform(put("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesDTO)))
            .andExpect(status().isOk());

        // Validate the Files in the database
        List<Files> filesList = filesRepository.findAll();
        assertThat(filesList).hasSize(databaseSizeBeforeUpdate);
        Files testFiles = filesList.get(filesList.size() - 1);
        assertThat(testFiles.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFiles.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFiles.getPublique()).isEqualTo(UPDATED_PUBLIQUE);
        assertThat(testFiles.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testFiles.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
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
    }
}
