package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.Phase;
import com.kidole.sport.repository.PhaseRepository;
import com.kidole.sport.repository.search.PhaseSearchRepository;
import com.kidole.sport.service.PhaseService;
import com.kidole.sport.service.dto.PhaseDTO;
import com.kidole.sport.service.mapper.PhaseMapper;
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
 * Integration tests for the {@link PhaseResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class PhaseResourceIT {

    private static final String DEFAULT_PHASE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PHASE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PHASE_NUMBER = 1;
    private static final Integer UPDATED_PHASE_NUMBER = 2;

    private static final Integer DEFAULT_PHASE_DAY_NUMBER = 1;
    private static final Integer UPDATED_PHASE_DAY_NUMBER = 2;

    @Autowired
    private PhaseRepository phaseRepository;

    @Autowired
    private PhaseMapper phaseMapper;

    @Autowired
    private PhaseService phaseService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.PhaseSearchRepositoryMockConfiguration
     */
    @Autowired
    private PhaseSearchRepository mockPhaseSearchRepository;

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

    private MockMvc restPhaseMockMvc;

    private Phase phase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PhaseResource phaseResource = new PhaseResource(phaseService);
        this.restPhaseMockMvc = MockMvcBuilders.standaloneSetup(phaseResource)
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
    public static Phase createEntity(EntityManager em) {
        Phase phase = new Phase()
            .phaseName(DEFAULT_PHASE_NAME)
            .phaseNumber(DEFAULT_PHASE_NUMBER)
            .phaseDayNumber(DEFAULT_PHASE_DAY_NUMBER);
        return phase;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phase createUpdatedEntity(EntityManager em) {
        Phase phase = new Phase()
            .phaseName(UPDATED_PHASE_NAME)
            .phaseNumber(UPDATED_PHASE_NUMBER)
            .phaseDayNumber(UPDATED_PHASE_DAY_NUMBER);
        return phase;
    }

    @BeforeEach
    public void initTest() {
        phase = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhase() throws Exception {
        int databaseSizeBeforeCreate = phaseRepository.findAll().size();

        // Create the Phase
        PhaseDTO phaseDTO = phaseMapper.toDto(phase);
        restPhaseMockMvc.perform(post("/api/phases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phaseDTO)))
            .andExpect(status().isCreated());

        // Validate the Phase in the database
        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeCreate + 1);
        Phase testPhase = phaseList.get(phaseList.size() - 1);
        assertThat(testPhase.getPhaseName()).isEqualTo(DEFAULT_PHASE_NAME);
        assertThat(testPhase.getPhaseNumber()).isEqualTo(DEFAULT_PHASE_NUMBER);
        assertThat(testPhase.getPhaseDayNumber()).isEqualTo(DEFAULT_PHASE_DAY_NUMBER);

        // Validate the Phase in Elasticsearch
        verify(mockPhaseSearchRepository, times(1)).save(testPhase);
    }

    @Test
    @Transactional
    public void createPhaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phaseRepository.findAll().size();

        // Create the Phase with an existing ID
        phase.setId(1L);
        PhaseDTO phaseDTO = phaseMapper.toDto(phase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhaseMockMvc.perform(post("/api/phases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Phase in the database
        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeCreate);

        // Validate the Phase in Elasticsearch
        verify(mockPhaseSearchRepository, times(0)).save(phase);
    }


    @Test
    @Transactional
    public void checkPhaseNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = phaseRepository.findAll().size();
        // set the field null
        phase.setPhaseName(null);

        // Create the Phase, which fails.
        PhaseDTO phaseDTO = phaseMapper.toDto(phase);

        restPhaseMockMvc.perform(post("/api/phases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phaseDTO)))
            .andExpect(status().isBadRequest());

        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPhases() throws Exception {
        // Initialize the database
        phaseRepository.saveAndFlush(phase);

        // Get all the phaseList
        restPhaseMockMvc.perform(get("/api/phases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phase.getId().intValue())))
            .andExpect(jsonPath("$.[*].phaseName").value(hasItem(DEFAULT_PHASE_NAME)))
            .andExpect(jsonPath("$.[*].phaseNumber").value(hasItem(DEFAULT_PHASE_NUMBER)))
            .andExpect(jsonPath("$.[*].phaseDayNumber").value(hasItem(DEFAULT_PHASE_DAY_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getPhase() throws Exception {
        // Initialize the database
        phaseRepository.saveAndFlush(phase);

        // Get the phase
        restPhaseMockMvc.perform(get("/api/phases/{id}", phase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(phase.getId().intValue()))
            .andExpect(jsonPath("$.phaseName").value(DEFAULT_PHASE_NAME))
            .andExpect(jsonPath("$.phaseNumber").value(DEFAULT_PHASE_NUMBER))
            .andExpect(jsonPath("$.phaseDayNumber").value(DEFAULT_PHASE_DAY_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingPhase() throws Exception {
        // Get the phase
        restPhaseMockMvc.perform(get("/api/phases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhase() throws Exception {
        // Initialize the database
        phaseRepository.saveAndFlush(phase);

        int databaseSizeBeforeUpdate = phaseRepository.findAll().size();

        // Update the phase
        Phase updatedPhase = phaseRepository.findById(phase.getId()).get();
        // Disconnect from session so that the updates on updatedPhase are not directly saved in db
        em.detach(updatedPhase);
        updatedPhase
            .phaseName(UPDATED_PHASE_NAME)
            .phaseNumber(UPDATED_PHASE_NUMBER)
            .phaseDayNumber(UPDATED_PHASE_DAY_NUMBER);
        PhaseDTO phaseDTO = phaseMapper.toDto(updatedPhase);

        restPhaseMockMvc.perform(put("/api/phases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phaseDTO)))
            .andExpect(status().isOk());

        // Validate the Phase in the database
        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeUpdate);
        Phase testPhase = phaseList.get(phaseList.size() - 1);
        assertThat(testPhase.getPhaseName()).isEqualTo(UPDATED_PHASE_NAME);
        assertThat(testPhase.getPhaseNumber()).isEqualTo(UPDATED_PHASE_NUMBER);
        assertThat(testPhase.getPhaseDayNumber()).isEqualTo(UPDATED_PHASE_DAY_NUMBER);

        // Validate the Phase in Elasticsearch
        verify(mockPhaseSearchRepository, times(1)).save(testPhase);
    }

    @Test
    @Transactional
    public void updateNonExistingPhase() throws Exception {
        int databaseSizeBeforeUpdate = phaseRepository.findAll().size();

        // Create the Phase
        PhaseDTO phaseDTO = phaseMapper.toDto(phase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhaseMockMvc.perform(put("/api/phases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Phase in the database
        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Phase in Elasticsearch
        verify(mockPhaseSearchRepository, times(0)).save(phase);
    }

    @Test
    @Transactional
    public void deletePhase() throws Exception {
        // Initialize the database
        phaseRepository.saveAndFlush(phase);

        int databaseSizeBeforeDelete = phaseRepository.findAll().size();

        // Delete the phase
        restPhaseMockMvc.perform(delete("/api/phases/{id}", phase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Phase> phaseList = phaseRepository.findAll();
        assertThat(phaseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Phase in Elasticsearch
        verify(mockPhaseSearchRepository, times(1)).deleteById(phase.getId());
    }

    @Test
    @Transactional
    public void searchPhase() throws Exception {
        // Initialize the database
        phaseRepository.saveAndFlush(phase);
        when(mockPhaseSearchRepository.search(queryStringQuery("id:" + phase.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(phase), PageRequest.of(0, 1), 1));
        // Search the phase
        restPhaseMockMvc.perform(get("/api/_search/phases?query=id:" + phase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phase.getId().intValue())))
            .andExpect(jsonPath("$.[*].phaseName").value(hasItem(DEFAULT_PHASE_NAME)))
            .andExpect(jsonPath("$.[*].phaseNumber").value(hasItem(DEFAULT_PHASE_NUMBER)))
            .andExpect(jsonPath("$.[*].phaseDayNumber").value(hasItem(DEFAULT_PHASE_DAY_NUMBER)));
    }
}
