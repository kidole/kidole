package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.Format;
import com.kidole.sport.repository.FormatRepository;
import com.kidole.sport.repository.search.FormatSearchRepository;
import com.kidole.sport.service.FormatService;
import com.kidole.sport.service.dto.FormatDTO;
import com.kidole.sport.service.mapper.FormatMapper;
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
 * Integration tests for the {@link FormatResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class FormatResourceIT {

    private static final String DEFAULT_FORMAT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FORMAT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_WINER_QTY = 0;
    private static final Integer UPDATED_WINER_QTY = 1;

    @Autowired
    private FormatRepository formatRepository;

    @Autowired
    private FormatMapper formatMapper;

    @Autowired
    private FormatService formatService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.FormatSearchRepositoryMockConfiguration
     */
    @Autowired
    private FormatSearchRepository mockFormatSearchRepository;

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

    private MockMvc restFormatMockMvc;

    private Format format;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormatResource formatResource = new FormatResource(formatService);
        this.restFormatMockMvc = MockMvcBuilders.standaloneSetup(formatResource)
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
    public static Format createEntity(EntityManager em) {
        Format format = new Format()
            .formatName(DEFAULT_FORMAT_NAME)
            .winerQty(DEFAULT_WINER_QTY);
        return format;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Format createUpdatedEntity(EntityManager em) {
        Format format = new Format()
            .formatName(UPDATED_FORMAT_NAME)
            .winerQty(UPDATED_WINER_QTY);
        return format;
    }

    @BeforeEach
    public void initTest() {
        format = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormat() throws Exception {
        int databaseSizeBeforeCreate = formatRepository.findAll().size();

        // Create the Format
        FormatDTO formatDTO = formatMapper.toDto(format);
        restFormatMockMvc.perform(post("/api/formats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formatDTO)))
            .andExpect(status().isCreated());

        // Validate the Format in the database
        List<Format> formatList = formatRepository.findAll();
        assertThat(formatList).hasSize(databaseSizeBeforeCreate + 1);
        Format testFormat = formatList.get(formatList.size() - 1);
        assertThat(testFormat.getFormatName()).isEqualTo(DEFAULT_FORMAT_NAME);
        assertThat(testFormat.getWinerQty()).isEqualTo(DEFAULT_WINER_QTY);

        // Validate the Format in Elasticsearch
        verify(mockFormatSearchRepository, times(1)).save(testFormat);
    }

    @Test
    @Transactional
    public void createFormatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formatRepository.findAll().size();

        // Create the Format with an existing ID
        format.setId(1L);
        FormatDTO formatDTO = formatMapper.toDto(format);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormatMockMvc.perform(post("/api/formats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Format in the database
        List<Format> formatList = formatRepository.findAll();
        assertThat(formatList).hasSize(databaseSizeBeforeCreate);

        // Validate the Format in Elasticsearch
        verify(mockFormatSearchRepository, times(0)).save(format);
    }


    @Test
    @Transactional
    public void checkFormatNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = formatRepository.findAll().size();
        // set the field null
        format.setFormatName(null);

        // Create the Format, which fails.
        FormatDTO formatDTO = formatMapper.toDto(format);

        restFormatMockMvc.perform(post("/api/formats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formatDTO)))
            .andExpect(status().isBadRequest());

        List<Format> formatList = formatRepository.findAll();
        assertThat(formatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormats() throws Exception {
        // Initialize the database
        formatRepository.saveAndFlush(format);

        // Get all the formatList
        restFormatMockMvc.perform(get("/api/formats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(format.getId().intValue())))
            .andExpect(jsonPath("$.[*].formatName").value(hasItem(DEFAULT_FORMAT_NAME)))
            .andExpect(jsonPath("$.[*].winerQty").value(hasItem(DEFAULT_WINER_QTY)));
    }
    
    @Test
    @Transactional
    public void getFormat() throws Exception {
        // Initialize the database
        formatRepository.saveAndFlush(format);

        // Get the format
        restFormatMockMvc.perform(get("/api/formats/{id}", format.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(format.getId().intValue()))
            .andExpect(jsonPath("$.formatName").value(DEFAULT_FORMAT_NAME))
            .andExpect(jsonPath("$.winerQty").value(DEFAULT_WINER_QTY));
    }

    @Test
    @Transactional
    public void getNonExistingFormat() throws Exception {
        // Get the format
        restFormatMockMvc.perform(get("/api/formats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormat() throws Exception {
        // Initialize the database
        formatRepository.saveAndFlush(format);

        int databaseSizeBeforeUpdate = formatRepository.findAll().size();

        // Update the format
        Format updatedFormat = formatRepository.findById(format.getId()).get();
        // Disconnect from session so that the updates on updatedFormat are not directly saved in db
        em.detach(updatedFormat);
        updatedFormat
            .formatName(UPDATED_FORMAT_NAME)
            .winerQty(UPDATED_WINER_QTY);
        FormatDTO formatDTO = formatMapper.toDto(updatedFormat);

        restFormatMockMvc.perform(put("/api/formats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formatDTO)))
            .andExpect(status().isOk());

        // Validate the Format in the database
        List<Format> formatList = formatRepository.findAll();
        assertThat(formatList).hasSize(databaseSizeBeforeUpdate);
        Format testFormat = formatList.get(formatList.size() - 1);
        assertThat(testFormat.getFormatName()).isEqualTo(UPDATED_FORMAT_NAME);
        assertThat(testFormat.getWinerQty()).isEqualTo(UPDATED_WINER_QTY);

        // Validate the Format in Elasticsearch
        verify(mockFormatSearchRepository, times(1)).save(testFormat);
    }

    @Test
    @Transactional
    public void updateNonExistingFormat() throws Exception {
        int databaseSizeBeforeUpdate = formatRepository.findAll().size();

        // Create the Format
        FormatDTO formatDTO = formatMapper.toDto(format);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormatMockMvc.perform(put("/api/formats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Format in the database
        List<Format> formatList = formatRepository.findAll();
        assertThat(formatList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Format in Elasticsearch
        verify(mockFormatSearchRepository, times(0)).save(format);
    }

    @Test
    @Transactional
    public void deleteFormat() throws Exception {
        // Initialize the database
        formatRepository.saveAndFlush(format);

        int databaseSizeBeforeDelete = formatRepository.findAll().size();

        // Delete the format
        restFormatMockMvc.perform(delete("/api/formats/{id}", format.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Format> formatList = formatRepository.findAll();
        assertThat(formatList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Format in Elasticsearch
        verify(mockFormatSearchRepository, times(1)).deleteById(format.getId());
    }

    @Test
    @Transactional
    public void searchFormat() throws Exception {
        // Initialize the database
        formatRepository.saveAndFlush(format);
        when(mockFormatSearchRepository.search(queryStringQuery("id:" + format.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(format), PageRequest.of(0, 1), 1));
        // Search the format
        restFormatMockMvc.perform(get("/api/_search/formats?query=id:" + format.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(format.getId().intValue())))
            .andExpect(jsonPath("$.[*].formatName").value(hasItem(DEFAULT_FORMAT_NAME)))
            .andExpect(jsonPath("$.[*].winerQty").value(hasItem(DEFAULT_WINER_QTY)));
    }
}
