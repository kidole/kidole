package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.Delegation;
import com.kidole.sport.repository.DelegationRepository;
import com.kidole.sport.repository.search.DelegationSearchRepository;
import com.kidole.sport.service.DelegationService;
import com.kidole.sport.service.dto.DelegationDTO;
import com.kidole.sport.service.mapper.DelegationMapper;
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
 * Integration tests for the {@link DelegationResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class DelegationResourceIT {

    private static final String DEFAULT_DELEGATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DELEGATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DELEGATION_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_DELEGATION_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_DELEGATION_LOCALITY = "AAAAAAAAAA";
    private static final String UPDATED_DELEGATION_LOCALITY = "BBBBBBBBBB";

    private static final String DEFAULT_DELEGATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DELEGATION_CODE = "BBBBBBBBBB";

    @Autowired
    private DelegationRepository delegationRepository;

    @Autowired
    private DelegationMapper delegationMapper;

    @Autowired
    private DelegationService delegationService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.DelegationSearchRepositoryMockConfiguration
     */
    @Autowired
    private DelegationSearchRepository mockDelegationSearchRepository;

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

    private MockMvc restDelegationMockMvc;

    private Delegation delegation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DelegationResource delegationResource = new DelegationResource(delegationService);
        this.restDelegationMockMvc = MockMvcBuilders.standaloneSetup(delegationResource)
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
    public static Delegation createEntity(EntityManager em) {
        Delegation delegation = new Delegation()
            .delegationName(DEFAULT_DELEGATION_NAME)
            .delegationCountry(DEFAULT_DELEGATION_COUNTRY)
            .delegationLocality(DEFAULT_DELEGATION_LOCALITY)
            .delegationCode(DEFAULT_DELEGATION_CODE);
        return delegation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Delegation createUpdatedEntity(EntityManager em) {
        Delegation delegation = new Delegation()
            .delegationName(UPDATED_DELEGATION_NAME)
            .delegationCountry(UPDATED_DELEGATION_COUNTRY)
            .delegationLocality(UPDATED_DELEGATION_LOCALITY)
            .delegationCode(UPDATED_DELEGATION_CODE);
        return delegation;
    }

    @BeforeEach
    public void initTest() {
        delegation = createEntity(em);
    }

    @Test
    @Transactional
    public void createDelegation() throws Exception {
        int databaseSizeBeforeCreate = delegationRepository.findAll().size();

        // Create the Delegation
        DelegationDTO delegationDTO = delegationMapper.toDto(delegation);
        restDelegationMockMvc.perform(post("/api/delegations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegationDTO)))
            .andExpect(status().isCreated());

        // Validate the Delegation in the database
        List<Delegation> delegationList = delegationRepository.findAll();
        assertThat(delegationList).hasSize(databaseSizeBeforeCreate + 1);
        Delegation testDelegation = delegationList.get(delegationList.size() - 1);
        assertThat(testDelegation.getDelegationName()).isEqualTo(DEFAULT_DELEGATION_NAME);
        assertThat(testDelegation.getDelegationCountry()).isEqualTo(DEFAULT_DELEGATION_COUNTRY);
        assertThat(testDelegation.getDelegationLocality()).isEqualTo(DEFAULT_DELEGATION_LOCALITY);
        assertThat(testDelegation.getDelegationCode()).isEqualTo(DEFAULT_DELEGATION_CODE);

        // Validate the Delegation in Elasticsearch
        verify(mockDelegationSearchRepository, times(1)).save(testDelegation);
    }

    @Test
    @Transactional
    public void createDelegationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = delegationRepository.findAll().size();

        // Create the Delegation with an existing ID
        delegation.setId(1L);
        DelegationDTO delegationDTO = delegationMapper.toDto(delegation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDelegationMockMvc.perform(post("/api/delegations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Delegation in the database
        List<Delegation> delegationList = delegationRepository.findAll();
        assertThat(delegationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Delegation in Elasticsearch
        verify(mockDelegationSearchRepository, times(0)).save(delegation);
    }


    @Test
    @Transactional
    public void checkDelegationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = delegationRepository.findAll().size();
        // set the field null
        delegation.setDelegationName(null);

        // Create the Delegation, which fails.
        DelegationDTO delegationDTO = delegationMapper.toDto(delegation);

        restDelegationMockMvc.perform(post("/api/delegations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegationDTO)))
            .andExpect(status().isBadRequest());

        List<Delegation> delegationList = delegationRepository.findAll();
        assertThat(delegationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDelegationCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = delegationRepository.findAll().size();
        // set the field null
        delegation.setDelegationCountry(null);

        // Create the Delegation, which fails.
        DelegationDTO delegationDTO = delegationMapper.toDto(delegation);

        restDelegationMockMvc.perform(post("/api/delegations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegationDTO)))
            .andExpect(status().isBadRequest());

        List<Delegation> delegationList = delegationRepository.findAll();
        assertThat(delegationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDelegationLocalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = delegationRepository.findAll().size();
        // set the field null
        delegation.setDelegationLocality(null);

        // Create the Delegation, which fails.
        DelegationDTO delegationDTO = delegationMapper.toDto(delegation);

        restDelegationMockMvc.perform(post("/api/delegations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegationDTO)))
            .andExpect(status().isBadRequest());

        List<Delegation> delegationList = delegationRepository.findAll();
        assertThat(delegationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDelegationCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = delegationRepository.findAll().size();
        // set the field null
        delegation.setDelegationCode(null);

        // Create the Delegation, which fails.
        DelegationDTO delegationDTO = delegationMapper.toDto(delegation);

        restDelegationMockMvc.perform(post("/api/delegations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegationDTO)))
            .andExpect(status().isBadRequest());

        List<Delegation> delegationList = delegationRepository.findAll();
        assertThat(delegationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDelegations() throws Exception {
        // Initialize the database
        delegationRepository.saveAndFlush(delegation);

        // Get all the delegationList
        restDelegationMockMvc.perform(get("/api/delegations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(delegation.getId().intValue())))
            .andExpect(jsonPath("$.[*].delegationName").value(hasItem(DEFAULT_DELEGATION_NAME)))
            .andExpect(jsonPath("$.[*].delegationCountry").value(hasItem(DEFAULT_DELEGATION_COUNTRY)))
            .andExpect(jsonPath("$.[*].delegationLocality").value(hasItem(DEFAULT_DELEGATION_LOCALITY)))
            .andExpect(jsonPath("$.[*].delegationCode").value(hasItem(DEFAULT_DELEGATION_CODE)));
    }
    
    @Test
    @Transactional
    public void getDelegation() throws Exception {
        // Initialize the database
        delegationRepository.saveAndFlush(delegation);

        // Get the delegation
        restDelegationMockMvc.perform(get("/api/delegations/{id}", delegation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(delegation.getId().intValue()))
            .andExpect(jsonPath("$.delegationName").value(DEFAULT_DELEGATION_NAME))
            .andExpect(jsonPath("$.delegationCountry").value(DEFAULT_DELEGATION_COUNTRY))
            .andExpect(jsonPath("$.delegationLocality").value(DEFAULT_DELEGATION_LOCALITY))
            .andExpect(jsonPath("$.delegationCode").value(DEFAULT_DELEGATION_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingDelegation() throws Exception {
        // Get the delegation
        restDelegationMockMvc.perform(get("/api/delegations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDelegation() throws Exception {
        // Initialize the database
        delegationRepository.saveAndFlush(delegation);

        int databaseSizeBeforeUpdate = delegationRepository.findAll().size();

        // Update the delegation
        Delegation updatedDelegation = delegationRepository.findById(delegation.getId()).get();
        // Disconnect from session so that the updates on updatedDelegation are not directly saved in db
        em.detach(updatedDelegation);
        updatedDelegation
            .delegationName(UPDATED_DELEGATION_NAME)
            .delegationCountry(UPDATED_DELEGATION_COUNTRY)
            .delegationLocality(UPDATED_DELEGATION_LOCALITY)
            .delegationCode(UPDATED_DELEGATION_CODE);
        DelegationDTO delegationDTO = delegationMapper.toDto(updatedDelegation);

        restDelegationMockMvc.perform(put("/api/delegations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegationDTO)))
            .andExpect(status().isOk());

        // Validate the Delegation in the database
        List<Delegation> delegationList = delegationRepository.findAll();
        assertThat(delegationList).hasSize(databaseSizeBeforeUpdate);
        Delegation testDelegation = delegationList.get(delegationList.size() - 1);
        assertThat(testDelegation.getDelegationName()).isEqualTo(UPDATED_DELEGATION_NAME);
        assertThat(testDelegation.getDelegationCountry()).isEqualTo(UPDATED_DELEGATION_COUNTRY);
        assertThat(testDelegation.getDelegationLocality()).isEqualTo(UPDATED_DELEGATION_LOCALITY);
        assertThat(testDelegation.getDelegationCode()).isEqualTo(UPDATED_DELEGATION_CODE);

        // Validate the Delegation in Elasticsearch
        verify(mockDelegationSearchRepository, times(1)).save(testDelegation);
    }

    @Test
    @Transactional
    public void updateNonExistingDelegation() throws Exception {
        int databaseSizeBeforeUpdate = delegationRepository.findAll().size();

        // Create the Delegation
        DelegationDTO delegationDTO = delegationMapper.toDto(delegation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDelegationMockMvc.perform(put("/api/delegations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Delegation in the database
        List<Delegation> delegationList = delegationRepository.findAll();
        assertThat(delegationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Delegation in Elasticsearch
        verify(mockDelegationSearchRepository, times(0)).save(delegation);
    }

    @Test
    @Transactional
    public void deleteDelegation() throws Exception {
        // Initialize the database
        delegationRepository.saveAndFlush(delegation);

        int databaseSizeBeforeDelete = delegationRepository.findAll().size();

        // Delete the delegation
        restDelegationMockMvc.perform(delete("/api/delegations/{id}", delegation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Delegation> delegationList = delegationRepository.findAll();
        assertThat(delegationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Delegation in Elasticsearch
        verify(mockDelegationSearchRepository, times(1)).deleteById(delegation.getId());
    }

    @Test
    @Transactional
    public void searchDelegation() throws Exception {
        // Initialize the database
        delegationRepository.saveAndFlush(delegation);
        when(mockDelegationSearchRepository.search(queryStringQuery("id:" + delegation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(delegation), PageRequest.of(0, 1), 1));
        // Search the delegation
        restDelegationMockMvc.perform(get("/api/_search/delegations?query=id:" + delegation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(delegation.getId().intValue())))
            .andExpect(jsonPath("$.[*].delegationName").value(hasItem(DEFAULT_DELEGATION_NAME)))
            .andExpect(jsonPath("$.[*].delegationCountry").value(hasItem(DEFAULT_DELEGATION_COUNTRY)))
            .andExpect(jsonPath("$.[*].delegationLocality").value(hasItem(DEFAULT_DELEGATION_LOCALITY)))
            .andExpect(jsonPath("$.[*].delegationCode").value(hasItem(DEFAULT_DELEGATION_CODE)));
    }
}
