package com.web.rest;

import com.KidoleApp;
import com.domain.Delegation;
import com.repository.DelegationRepository;
import com.service.DelegationService;
import com.service.dto.DelegationDTO;
import com.service.mapper.DelegationMapper;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DelegationResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class DelegationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALITY = "AAAAAAAAAA";
    private static final String UPDATED_LOCALITY = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_1 = "AAAAAAAAAA";
    private static final String UPDATED_CODE_1 = "BBBBBBBBBB";

    @Autowired
    private DelegationRepository delegationRepository;

    @Autowired
    private DelegationMapper delegationMapper;

    @Autowired
    private DelegationService delegationService;

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
            .name(DEFAULT_NAME)
            .country(DEFAULT_COUNTRY)
            .locality(DEFAULT_LOCALITY)
            .code1(DEFAULT_CODE_1);
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
            .name(UPDATED_NAME)
            .country(UPDATED_COUNTRY)
            .locality(UPDATED_LOCALITY)
            .code1(UPDATED_CODE_1);
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
        assertThat(testDelegation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDelegation.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testDelegation.getLocality()).isEqualTo(DEFAULT_LOCALITY);
        assertThat(testDelegation.getCode1()).isEqualTo(DEFAULT_CODE_1);
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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].locality").value(hasItem(DEFAULT_LOCALITY)))
            .andExpect(jsonPath("$.[*].code1").value(hasItem(DEFAULT_CODE_1)));
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.locality").value(DEFAULT_LOCALITY))
            .andExpect(jsonPath("$.code1").value(DEFAULT_CODE_1));
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
            .name(UPDATED_NAME)
            .country(UPDATED_COUNTRY)
            .locality(UPDATED_LOCALITY)
            .code1(UPDATED_CODE_1);
        DelegationDTO delegationDTO = delegationMapper.toDto(updatedDelegation);

        restDelegationMockMvc.perform(put("/api/delegations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegationDTO)))
            .andExpect(status().isOk());

        // Validate the Delegation in the database
        List<Delegation> delegationList = delegationRepository.findAll();
        assertThat(delegationList).hasSize(databaseSizeBeforeUpdate);
        Delegation testDelegation = delegationList.get(delegationList.size() - 1);
        assertThat(testDelegation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDelegation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testDelegation.getLocality()).isEqualTo(UPDATED_LOCALITY);
        assertThat(testDelegation.getCode1()).isEqualTo(UPDATED_CODE_1);
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
    }
}
