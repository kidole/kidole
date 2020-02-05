package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.AccreditationStep;
import com.kidole.sport.repository.AccreditationStepRepository;
import com.kidole.sport.repository.search.AccreditationStepSearchRepository;
import com.kidole.sport.service.AccreditationStepService;
import com.kidole.sport.service.dto.AccreditationStepDTO;
import com.kidole.sport.service.mapper.AccreditationStepMapper;
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

import com.kidole.sport.domain.enumeration.AccreditationList;
/**
 * Integration tests for the {@link AccreditationStepResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class AccreditationStepResourceIT {

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ACCREDITATION_STEPNUMBER = 1;
    private static final Integer UPDATED_ACCREDITATION_STEPNUMBER = 2;

    private static final AccreditationList DEFAULT_ACCREDITATION_TYPE = AccreditationList.ATHLETE;
    private static final AccreditationList UPDATED_ACCREDITATION_TYPE = AccreditationList.SPARING;

    private static final Boolean DEFAULT_IS_PUBLIC = false;
    private static final Boolean UPDATED_IS_PUBLIC = true;

    private static final Boolean DEFAULT_URI = false;
    private static final Boolean UPDATED_URI = true;

    private static final String DEFAULT_FIELDS = "AAAAAAAAAA";
    private static final String UPDATED_FIELDS = "BBBBBBBBBB";

    @Autowired
    private AccreditationStepRepository accreditationStepRepository;

    @Autowired
    private AccreditationStepMapper accreditationStepMapper;

    @Autowired
    private AccreditationStepService accreditationStepService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.AccreditationStepSearchRepositoryMockConfiguration
     */
    @Autowired
    private AccreditationStepSearchRepository mockAccreditationStepSearchRepository;

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

    private MockMvc restAccreditationStepMockMvc;

    private AccreditationStep accreditationStep;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccreditationStepResource accreditationStepResource = new AccreditationStepResource(accreditationStepService);
        this.restAccreditationStepMockMvc = MockMvcBuilders.standaloneSetup(accreditationStepResource)
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
    public static AccreditationStep createEntity(EntityManager em) {
        AccreditationStep accreditationStep = new AccreditationStep()
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .accreditationStepnumber(DEFAULT_ACCREDITATION_STEPNUMBER)
            .accreditationType(DEFAULT_ACCREDITATION_TYPE)
            .isPublic(DEFAULT_IS_PUBLIC)
            .uri(DEFAULT_URI)
            .fields(DEFAULT_FIELDS);
        return accreditationStep;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccreditationStep createUpdatedEntity(EntityManager em) {
        AccreditationStep accreditationStep = new AccreditationStep()
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .accreditationStepnumber(UPDATED_ACCREDITATION_STEPNUMBER)
            .accreditationType(UPDATED_ACCREDITATION_TYPE)
            .isPublic(UPDATED_IS_PUBLIC)
            .uri(UPDATED_URI)
            .fields(UPDATED_FIELDS);
        return accreditationStep;
    }

    @BeforeEach
    public void initTest() {
        accreditationStep = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccreditationStep() throws Exception {
        int databaseSizeBeforeCreate = accreditationStepRepository.findAll().size();

        // Create the AccreditationStep
        AccreditationStepDTO accreditationStepDTO = accreditationStepMapper.toDto(accreditationStep);
        restAccreditationStepMockMvc.perform(post("/api/accreditation-steps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationStepDTO)))
            .andExpect(status().isCreated());

        // Validate the AccreditationStep in the database
        List<AccreditationStep> accreditationStepList = accreditationStepRepository.findAll();
        assertThat(accreditationStepList).hasSize(databaseSizeBeforeCreate + 1);
        AccreditationStep testAccreditationStep = accreditationStepList.get(accreditationStepList.size() - 1);
        assertThat(testAccreditationStep.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testAccreditationStep.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testAccreditationStep.getAccreditationStepnumber()).isEqualTo(DEFAULT_ACCREDITATION_STEPNUMBER);
        assertThat(testAccreditationStep.getAccreditationType()).isEqualTo(DEFAULT_ACCREDITATION_TYPE);
        assertThat(testAccreditationStep.isIsPublic()).isEqualTo(DEFAULT_IS_PUBLIC);
        assertThat(testAccreditationStep.isUri()).isEqualTo(DEFAULT_URI);
        assertThat(testAccreditationStep.getFields()).isEqualTo(DEFAULT_FIELDS);

        // Validate the AccreditationStep in Elasticsearch
        verify(mockAccreditationStepSearchRepository, times(1)).save(testAccreditationStep);
    }

    @Test
    @Transactional
    public void createAccreditationStepWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accreditationStepRepository.findAll().size();

        // Create the AccreditationStep with an existing ID
        accreditationStep.setId(1L);
        AccreditationStepDTO accreditationStepDTO = accreditationStepMapper.toDto(accreditationStep);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccreditationStepMockMvc.perform(post("/api/accreditation-steps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationStepDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccreditationStep in the database
        List<AccreditationStep> accreditationStepList = accreditationStepRepository.findAll();
        assertThat(accreditationStepList).hasSize(databaseSizeBeforeCreate);

        // Validate the AccreditationStep in Elasticsearch
        verify(mockAccreditationStepSearchRepository, times(0)).save(accreditationStep);
    }


    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = accreditationStepRepository.findAll().size();
        // set the field null
        accreditationStep.setStartTime(null);

        // Create the AccreditationStep, which fails.
        AccreditationStepDTO accreditationStepDTO = accreditationStepMapper.toDto(accreditationStep);

        restAccreditationStepMockMvc.perform(post("/api/accreditation-steps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationStepDTO)))
            .andExpect(status().isBadRequest());

        List<AccreditationStep> accreditationStepList = accreditationStepRepository.findAll();
        assertThat(accreditationStepList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = accreditationStepRepository.findAll().size();
        // set the field null
        accreditationStep.setEndTime(null);

        // Create the AccreditationStep, which fails.
        AccreditationStepDTO accreditationStepDTO = accreditationStepMapper.toDto(accreditationStep);

        restAccreditationStepMockMvc.perform(post("/api/accreditation-steps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationStepDTO)))
            .andExpect(status().isBadRequest());

        List<AccreditationStep> accreditationStepList = accreditationStepRepository.findAll();
        assertThat(accreditationStepList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccreditationStepnumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = accreditationStepRepository.findAll().size();
        // set the field null
        accreditationStep.setAccreditationStepnumber(null);

        // Create the AccreditationStep, which fails.
        AccreditationStepDTO accreditationStepDTO = accreditationStepMapper.toDto(accreditationStep);

        restAccreditationStepMockMvc.perform(post("/api/accreditation-steps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationStepDTO)))
            .andExpect(status().isBadRequest());

        List<AccreditationStep> accreditationStepList = accreditationStepRepository.findAll();
        assertThat(accreditationStepList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccreditationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = accreditationStepRepository.findAll().size();
        // set the field null
        accreditationStep.setAccreditationType(null);

        // Create the AccreditationStep, which fails.
        AccreditationStepDTO accreditationStepDTO = accreditationStepMapper.toDto(accreditationStep);

        restAccreditationStepMockMvc.perform(post("/api/accreditation-steps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationStepDTO)))
            .andExpect(status().isBadRequest());

        List<AccreditationStep> accreditationStepList = accreditationStepRepository.findAll();
        assertThat(accreditationStepList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccreditationSteps() throws Exception {
        // Initialize the database
        accreditationStepRepository.saveAndFlush(accreditationStep);

        // Get all the accreditationStepList
        restAccreditationStepMockMvc.perform(get("/api/accreditation-steps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accreditationStep.getId().intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].accreditationStepnumber").value(hasItem(DEFAULT_ACCREDITATION_STEPNUMBER)))
            .andExpect(jsonPath("$.[*].accreditationType").value(hasItem(DEFAULT_ACCREDITATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isPublic").value(hasItem(DEFAULT_IS_PUBLIC.booleanValue())))
            .andExpect(jsonPath("$.[*].uri").value(hasItem(DEFAULT_URI.booleanValue())))
            .andExpect(jsonPath("$.[*].fields").value(hasItem(DEFAULT_FIELDS.toString())));
    }
    
    @Test
    @Transactional
    public void getAccreditationStep() throws Exception {
        // Initialize the database
        accreditationStepRepository.saveAndFlush(accreditationStep);

        // Get the accreditationStep
        restAccreditationStepMockMvc.perform(get("/api/accreditation-steps/{id}", accreditationStep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accreditationStep.getId().intValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.accreditationStepnumber").value(DEFAULT_ACCREDITATION_STEPNUMBER))
            .andExpect(jsonPath("$.accreditationType").value(DEFAULT_ACCREDITATION_TYPE.toString()))
            .andExpect(jsonPath("$.isPublic").value(DEFAULT_IS_PUBLIC.booleanValue()))
            .andExpect(jsonPath("$.uri").value(DEFAULT_URI.booleanValue()))
            .andExpect(jsonPath("$.fields").value(DEFAULT_FIELDS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAccreditationStep() throws Exception {
        // Get the accreditationStep
        restAccreditationStepMockMvc.perform(get("/api/accreditation-steps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccreditationStep() throws Exception {
        // Initialize the database
        accreditationStepRepository.saveAndFlush(accreditationStep);

        int databaseSizeBeforeUpdate = accreditationStepRepository.findAll().size();

        // Update the accreditationStep
        AccreditationStep updatedAccreditationStep = accreditationStepRepository.findById(accreditationStep.getId()).get();
        // Disconnect from session so that the updates on updatedAccreditationStep are not directly saved in db
        em.detach(updatedAccreditationStep);
        updatedAccreditationStep
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .accreditationStepnumber(UPDATED_ACCREDITATION_STEPNUMBER)
            .accreditationType(UPDATED_ACCREDITATION_TYPE)
            .isPublic(UPDATED_IS_PUBLIC)
            .uri(UPDATED_URI)
            .fields(UPDATED_FIELDS);
        AccreditationStepDTO accreditationStepDTO = accreditationStepMapper.toDto(updatedAccreditationStep);

        restAccreditationStepMockMvc.perform(put("/api/accreditation-steps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationStepDTO)))
            .andExpect(status().isOk());

        // Validate the AccreditationStep in the database
        List<AccreditationStep> accreditationStepList = accreditationStepRepository.findAll();
        assertThat(accreditationStepList).hasSize(databaseSizeBeforeUpdate);
        AccreditationStep testAccreditationStep = accreditationStepList.get(accreditationStepList.size() - 1);
        assertThat(testAccreditationStep.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testAccreditationStep.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testAccreditationStep.getAccreditationStepnumber()).isEqualTo(UPDATED_ACCREDITATION_STEPNUMBER);
        assertThat(testAccreditationStep.getAccreditationType()).isEqualTo(UPDATED_ACCREDITATION_TYPE);
        assertThat(testAccreditationStep.isIsPublic()).isEqualTo(UPDATED_IS_PUBLIC);
        assertThat(testAccreditationStep.isUri()).isEqualTo(UPDATED_URI);
        assertThat(testAccreditationStep.getFields()).isEqualTo(UPDATED_FIELDS);

        // Validate the AccreditationStep in Elasticsearch
        verify(mockAccreditationStepSearchRepository, times(1)).save(testAccreditationStep);
    }

    @Test
    @Transactional
    public void updateNonExistingAccreditationStep() throws Exception {
        int databaseSizeBeforeUpdate = accreditationStepRepository.findAll().size();

        // Create the AccreditationStep
        AccreditationStepDTO accreditationStepDTO = accreditationStepMapper.toDto(accreditationStep);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccreditationStepMockMvc.perform(put("/api/accreditation-steps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationStepDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccreditationStep in the database
        List<AccreditationStep> accreditationStepList = accreditationStepRepository.findAll();
        assertThat(accreditationStepList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccreditationStep in Elasticsearch
        verify(mockAccreditationStepSearchRepository, times(0)).save(accreditationStep);
    }

    @Test
    @Transactional
    public void deleteAccreditationStep() throws Exception {
        // Initialize the database
        accreditationStepRepository.saveAndFlush(accreditationStep);

        int databaseSizeBeforeDelete = accreditationStepRepository.findAll().size();

        // Delete the accreditationStep
        restAccreditationStepMockMvc.perform(delete("/api/accreditation-steps/{id}", accreditationStep.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccreditationStep> accreditationStepList = accreditationStepRepository.findAll();
        assertThat(accreditationStepList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AccreditationStep in Elasticsearch
        verify(mockAccreditationStepSearchRepository, times(1)).deleteById(accreditationStep.getId());
    }

    @Test
    @Transactional
    public void searchAccreditationStep() throws Exception {
        // Initialize the database
        accreditationStepRepository.saveAndFlush(accreditationStep);
        when(mockAccreditationStepSearchRepository.search(queryStringQuery("id:" + accreditationStep.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(accreditationStep), PageRequest.of(0, 1), 1));
        // Search the accreditationStep
        restAccreditationStepMockMvc.perform(get("/api/_search/accreditation-steps?query=id:" + accreditationStep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accreditationStep.getId().intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].accreditationStepnumber").value(hasItem(DEFAULT_ACCREDITATION_STEPNUMBER)))
            .andExpect(jsonPath("$.[*].accreditationType").value(hasItem(DEFAULT_ACCREDITATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isPublic").value(hasItem(DEFAULT_IS_PUBLIC.booleanValue())))
            .andExpect(jsonPath("$.[*].uri").value(hasItem(DEFAULT_URI.booleanValue())))
            .andExpect(jsonPath("$.[*].fields").value(hasItem(DEFAULT_FIELDS.toString())));
    }
}
