package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.Confrontation;
import com.kidole.sport.repository.ConfrontationRepository;
import com.kidole.sport.repository.search.ConfrontationSearchRepository;
import com.kidole.sport.service.ConfrontationService;
import com.kidole.sport.service.dto.ConfrontationDTO;
import com.kidole.sport.service.mapper.ConfrontationMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ConfrontationResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class ConfrontationResourceIT {

    private static final String DEFAULT_CONFRONTATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONFRONTATION_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CONFRONTATION_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_CONFRONTATION_DETAILS = "BBBBBBBBBB";

    @Autowired
    private ConfrontationRepository confrontationRepository;

    @Autowired
    private ConfrontationMapper confrontationMapper;

    @Autowired
    private ConfrontationService confrontationService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.ConfrontationSearchRepositoryMockConfiguration
     */
    @Autowired
    private ConfrontationSearchRepository mockConfrontationSearchRepository;

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

    private MockMvc restConfrontationMockMvc;

    private Confrontation confrontation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConfrontationResource confrontationResource = new ConfrontationResource(confrontationService);
        this.restConfrontationMockMvc = MockMvcBuilders.standaloneSetup(confrontationResource)
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
    public static Confrontation createEntity(EntityManager em) {
        Confrontation confrontation = new Confrontation()
            .confrontationName(DEFAULT_CONFRONTATION_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .confrontationDetails(DEFAULT_CONFRONTATION_DETAILS);
        return confrontation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Confrontation createUpdatedEntity(EntityManager em) {
        Confrontation confrontation = new Confrontation()
            .confrontationName(UPDATED_CONFRONTATION_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .confrontationDetails(UPDATED_CONFRONTATION_DETAILS);
        return confrontation;
    }

    @BeforeEach
    public void initTest() {
        confrontation = createEntity(em);
    }

    @Test
    @Transactional
    public void createConfrontation() throws Exception {
        int databaseSizeBeforeCreate = confrontationRepository.findAll().size();

        // Create the Confrontation
        ConfrontationDTO confrontationDTO = confrontationMapper.toDto(confrontation);
        restConfrontationMockMvc.perform(post("/api/confrontations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confrontationDTO)))
            .andExpect(status().isCreated());

        // Validate the Confrontation in the database
        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeCreate + 1);
        Confrontation testConfrontation = confrontationList.get(confrontationList.size() - 1);
        assertThat(testConfrontation.getConfrontationName()).isEqualTo(DEFAULT_CONFRONTATION_NAME);
        assertThat(testConfrontation.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testConfrontation.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testConfrontation.getConfrontationDetails()).isEqualTo(DEFAULT_CONFRONTATION_DETAILS);

        // Validate the Confrontation in Elasticsearch
        verify(mockConfrontationSearchRepository, times(1)).save(testConfrontation);
    }

    @Test
    @Transactional
    public void createConfrontationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = confrontationRepository.findAll().size();

        // Create the Confrontation with an existing ID
        confrontation.setId(1L);
        ConfrontationDTO confrontationDTO = confrontationMapper.toDto(confrontation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfrontationMockMvc.perform(post("/api/confrontations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confrontationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Confrontation in the database
        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Confrontation in Elasticsearch
        verify(mockConfrontationSearchRepository, times(0)).save(confrontation);
    }


    @Test
    @Transactional
    public void checkConfrontationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = confrontationRepository.findAll().size();
        // set the field null
        confrontation.setConfrontationName(null);

        // Create the Confrontation, which fails.
        ConfrontationDTO confrontationDTO = confrontationMapper.toDto(confrontation);

        restConfrontationMockMvc.perform(post("/api/confrontations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confrontationDTO)))
            .andExpect(status().isBadRequest());

        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = confrontationRepository.findAll().size();
        // set the field null
        confrontation.setStartDate(null);

        // Create the Confrontation, which fails.
        ConfrontationDTO confrontationDTO = confrontationMapper.toDto(confrontation);

        restConfrontationMockMvc.perform(post("/api/confrontations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confrontationDTO)))
            .andExpect(status().isBadRequest());

        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = confrontationRepository.findAll().size();
        // set the field null
        confrontation.setEndDate(null);

        // Create the Confrontation, which fails.
        ConfrontationDTO confrontationDTO = confrontationMapper.toDto(confrontation);

        restConfrontationMockMvc.perform(post("/api/confrontations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confrontationDTO)))
            .andExpect(status().isBadRequest());

        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConfrontations() throws Exception {
        // Initialize the database
        confrontationRepository.saveAndFlush(confrontation);

        // Get all the confrontationList
        restConfrontationMockMvc.perform(get("/api/confrontations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(confrontation.getId().intValue())))
            .andExpect(jsonPath("$.[*].confrontationName").value(hasItem(DEFAULT_CONFRONTATION_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].confrontationDetails").value(hasItem(DEFAULT_CONFRONTATION_DETAILS.toString())));
    }
    
    @Test
    @Transactional
    public void getConfrontation() throws Exception {
        // Initialize the database
        confrontationRepository.saveAndFlush(confrontation);

        // Get the confrontation
        restConfrontationMockMvc.perform(get("/api/confrontations/{id}", confrontation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(confrontation.getId().intValue()))
            .andExpect(jsonPath("$.confrontationName").value(DEFAULT_CONFRONTATION_NAME))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.confrontationDetails").value(DEFAULT_CONFRONTATION_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConfrontation() throws Exception {
        // Get the confrontation
        restConfrontationMockMvc.perform(get("/api/confrontations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConfrontation() throws Exception {
        // Initialize the database
        confrontationRepository.saveAndFlush(confrontation);

        int databaseSizeBeforeUpdate = confrontationRepository.findAll().size();

        // Update the confrontation
        Confrontation updatedConfrontation = confrontationRepository.findById(confrontation.getId()).get();
        // Disconnect from session so that the updates on updatedConfrontation are not directly saved in db
        em.detach(updatedConfrontation);
        updatedConfrontation
            .confrontationName(UPDATED_CONFRONTATION_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .confrontationDetails(UPDATED_CONFRONTATION_DETAILS);
        ConfrontationDTO confrontationDTO = confrontationMapper.toDto(updatedConfrontation);

        restConfrontationMockMvc.perform(put("/api/confrontations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confrontationDTO)))
            .andExpect(status().isOk());

        // Validate the Confrontation in the database
        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeUpdate);
        Confrontation testConfrontation = confrontationList.get(confrontationList.size() - 1);
        assertThat(testConfrontation.getConfrontationName()).isEqualTo(UPDATED_CONFRONTATION_NAME);
        assertThat(testConfrontation.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testConfrontation.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testConfrontation.getConfrontationDetails()).isEqualTo(UPDATED_CONFRONTATION_DETAILS);

        // Validate the Confrontation in Elasticsearch
        verify(mockConfrontationSearchRepository, times(1)).save(testConfrontation);
    }

    @Test
    @Transactional
    public void updateNonExistingConfrontation() throws Exception {
        int databaseSizeBeforeUpdate = confrontationRepository.findAll().size();

        // Create the Confrontation
        ConfrontationDTO confrontationDTO = confrontationMapper.toDto(confrontation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfrontationMockMvc.perform(put("/api/confrontations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confrontationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Confrontation in the database
        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Confrontation in Elasticsearch
        verify(mockConfrontationSearchRepository, times(0)).save(confrontation);
    }

    @Test
    @Transactional
    public void deleteConfrontation() throws Exception {
        // Initialize the database
        confrontationRepository.saveAndFlush(confrontation);

        int databaseSizeBeforeDelete = confrontationRepository.findAll().size();

        // Delete the confrontation
        restConfrontationMockMvc.perform(delete("/api/confrontations/{id}", confrontation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Confrontation in Elasticsearch
        verify(mockConfrontationSearchRepository, times(1)).deleteById(confrontation.getId());
    }

    @Test
    @Transactional
    public void searchConfrontation() throws Exception {
        // Initialize the database
        confrontationRepository.saveAndFlush(confrontation);
        when(mockConfrontationSearchRepository.search(queryStringQuery("id:" + confrontation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(confrontation), PageRequest.of(0, 1), 1));
        // Search the confrontation
        restConfrontationMockMvc.perform(get("/api/_search/confrontations?query=id:" + confrontation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(confrontation.getId().intValue())))
            .andExpect(jsonPath("$.[*].confrontationName").value(hasItem(DEFAULT_CONFRONTATION_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].confrontationDetails").value(hasItem(DEFAULT_CONFRONTATION_DETAILS.toString())));
    }
}
