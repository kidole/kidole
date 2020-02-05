package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.Options;
import com.kidole.sport.repository.OptionsRepository;
import com.kidole.sport.repository.search.OptionsSearchRepository;
import com.kidole.sport.service.OptionsService;
import com.kidole.sport.service.dto.OptionsDTO;
import com.kidole.sport.service.mapper.OptionsMapper;
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
 * Integration tests for the {@link OptionsResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class OptionsResourceIT {

    private static final String DEFAULT_OPTIONS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OPTIONS_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_OPTIONS_VALUE_1 = false;
    private static final Boolean UPDATED_OPTIONS_VALUE_1 = true;

    private static final Integer DEFAULT_OPTIONS_VALUE_2 = 0;
    private static final Integer UPDATED_OPTIONS_VALUE_2 = 1;

    @Autowired
    private OptionsRepository optionsRepository;

    @Autowired
    private OptionsMapper optionsMapper;

    @Autowired
    private OptionsService optionsService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.OptionsSearchRepositoryMockConfiguration
     */
    @Autowired
    private OptionsSearchRepository mockOptionsSearchRepository;

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

    private MockMvc restOptionsMockMvc;

    private Options options;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OptionsResource optionsResource = new OptionsResource(optionsService);
        this.restOptionsMockMvc = MockMvcBuilders.standaloneSetup(optionsResource)
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
    public static Options createEntity(EntityManager em) {
        Options options = new Options()
            .optionsName(DEFAULT_OPTIONS_NAME)
            .optionsValue1(DEFAULT_OPTIONS_VALUE_1)
            .optionsValue2(DEFAULT_OPTIONS_VALUE_2);
        return options;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Options createUpdatedEntity(EntityManager em) {
        Options options = new Options()
            .optionsName(UPDATED_OPTIONS_NAME)
            .optionsValue1(UPDATED_OPTIONS_VALUE_1)
            .optionsValue2(UPDATED_OPTIONS_VALUE_2);
        return options;
    }

    @BeforeEach
    public void initTest() {
        options = createEntity(em);
    }

    @Test
    @Transactional
    public void createOptions() throws Exception {
        int databaseSizeBeforeCreate = optionsRepository.findAll().size();

        // Create the Options
        OptionsDTO optionsDTO = optionsMapper.toDto(options);
        restOptionsMockMvc.perform(post("/api/options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeCreate + 1);
        Options testOptions = optionsList.get(optionsList.size() - 1);
        assertThat(testOptions.getOptionsName()).isEqualTo(DEFAULT_OPTIONS_NAME);
        assertThat(testOptions.isOptionsValue1()).isEqualTo(DEFAULT_OPTIONS_VALUE_1);
        assertThat(testOptions.getOptionsValue2()).isEqualTo(DEFAULT_OPTIONS_VALUE_2);

        // Validate the Options in Elasticsearch
        verify(mockOptionsSearchRepository, times(1)).save(testOptions);
    }

    @Test
    @Transactional
    public void createOptionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = optionsRepository.findAll().size();

        // Create the Options with an existing ID
        options.setId(1L);
        OptionsDTO optionsDTO = optionsMapper.toDto(options);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionsMockMvc.perform(post("/api/options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Options in Elasticsearch
        verify(mockOptionsSearchRepository, times(0)).save(options);
    }


    @Test
    @Transactional
    public void checkOptionsNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = optionsRepository.findAll().size();
        // set the field null
        options.setOptionsName(null);

        // Create the Options, which fails.
        OptionsDTO optionsDTO = optionsMapper.toDto(options);

        restOptionsMockMvc.perform(post("/api/options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionsDTO)))
            .andExpect(status().isBadRequest());

        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOptionsValue1IsRequired() throws Exception {
        int databaseSizeBeforeTest = optionsRepository.findAll().size();
        // set the field null
        options.setOptionsValue1(null);

        // Create the Options, which fails.
        OptionsDTO optionsDTO = optionsMapper.toDto(options);

        restOptionsMockMvc.perform(post("/api/options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionsDTO)))
            .andExpect(status().isBadRequest());

        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOptionsValue2IsRequired() throws Exception {
        int databaseSizeBeforeTest = optionsRepository.findAll().size();
        // set the field null
        options.setOptionsValue2(null);

        // Create the Options, which fails.
        OptionsDTO optionsDTO = optionsMapper.toDto(options);

        restOptionsMockMvc.perform(post("/api/options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionsDTO)))
            .andExpect(status().isBadRequest());

        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOptions() throws Exception {
        // Initialize the database
        optionsRepository.saveAndFlush(options);

        // Get all the optionsList
        restOptionsMockMvc.perform(get("/api/options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(options.getId().intValue())))
            .andExpect(jsonPath("$.[*].optionsName").value(hasItem(DEFAULT_OPTIONS_NAME)))
            .andExpect(jsonPath("$.[*].optionsValue1").value(hasItem(DEFAULT_OPTIONS_VALUE_1.booleanValue())))
            .andExpect(jsonPath("$.[*].optionsValue2").value(hasItem(DEFAULT_OPTIONS_VALUE_2)));
    }
    
    @Test
    @Transactional
    public void getOptions() throws Exception {
        // Initialize the database
        optionsRepository.saveAndFlush(options);

        // Get the options
        restOptionsMockMvc.perform(get("/api/options/{id}", options.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(options.getId().intValue()))
            .andExpect(jsonPath("$.optionsName").value(DEFAULT_OPTIONS_NAME))
            .andExpect(jsonPath("$.optionsValue1").value(DEFAULT_OPTIONS_VALUE_1.booleanValue()))
            .andExpect(jsonPath("$.optionsValue2").value(DEFAULT_OPTIONS_VALUE_2));
    }

    @Test
    @Transactional
    public void getNonExistingOptions() throws Exception {
        // Get the options
        restOptionsMockMvc.perform(get("/api/options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOptions() throws Exception {
        // Initialize the database
        optionsRepository.saveAndFlush(options);

        int databaseSizeBeforeUpdate = optionsRepository.findAll().size();

        // Update the options
        Options updatedOptions = optionsRepository.findById(options.getId()).get();
        // Disconnect from session so that the updates on updatedOptions are not directly saved in db
        em.detach(updatedOptions);
        updatedOptions
            .optionsName(UPDATED_OPTIONS_NAME)
            .optionsValue1(UPDATED_OPTIONS_VALUE_1)
            .optionsValue2(UPDATED_OPTIONS_VALUE_2);
        OptionsDTO optionsDTO = optionsMapper.toDto(updatedOptions);

        restOptionsMockMvc.perform(put("/api/options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionsDTO)))
            .andExpect(status().isOk());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeUpdate);
        Options testOptions = optionsList.get(optionsList.size() - 1);
        assertThat(testOptions.getOptionsName()).isEqualTo(UPDATED_OPTIONS_NAME);
        assertThat(testOptions.isOptionsValue1()).isEqualTo(UPDATED_OPTIONS_VALUE_1);
        assertThat(testOptions.getOptionsValue2()).isEqualTo(UPDATED_OPTIONS_VALUE_2);

        // Validate the Options in Elasticsearch
        verify(mockOptionsSearchRepository, times(1)).save(testOptions);
    }

    @Test
    @Transactional
    public void updateNonExistingOptions() throws Exception {
        int databaseSizeBeforeUpdate = optionsRepository.findAll().size();

        // Create the Options
        OptionsDTO optionsDTO = optionsMapper.toDto(options);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionsMockMvc.perform(put("/api/options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Options in the database
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Options in Elasticsearch
        verify(mockOptionsSearchRepository, times(0)).save(options);
    }

    @Test
    @Transactional
    public void deleteOptions() throws Exception {
        // Initialize the database
        optionsRepository.saveAndFlush(options);

        int databaseSizeBeforeDelete = optionsRepository.findAll().size();

        // Delete the options
        restOptionsMockMvc.perform(delete("/api/options/{id}", options.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Options> optionsList = optionsRepository.findAll();
        assertThat(optionsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Options in Elasticsearch
        verify(mockOptionsSearchRepository, times(1)).deleteById(options.getId());
    }

    @Test
    @Transactional
    public void searchOptions() throws Exception {
        // Initialize the database
        optionsRepository.saveAndFlush(options);
        when(mockOptionsSearchRepository.search(queryStringQuery("id:" + options.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(options), PageRequest.of(0, 1), 1));
        // Search the options
        restOptionsMockMvc.perform(get("/api/_search/options?query=id:" + options.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(options.getId().intValue())))
            .andExpect(jsonPath("$.[*].optionsName").value(hasItem(DEFAULT_OPTIONS_NAME)))
            .andExpect(jsonPath("$.[*].optionsValue1").value(hasItem(DEFAULT_OPTIONS_VALUE_1.booleanValue())))
            .andExpect(jsonPath("$.[*].optionsValue2").value(hasItem(DEFAULT_OPTIONS_VALUE_2)));
    }
}
