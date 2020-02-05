package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.Accreditation;
import com.kidole.sport.repository.AccreditationRepository;
import com.kidole.sport.repository.search.AccreditationSearchRepository;
import com.kidole.sport.service.AccreditationService;
import com.kidole.sport.service.dto.AccreditationDTO;
import com.kidole.sport.service.mapper.AccreditationMapper;
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

import com.kidole.sport.domain.enumeration.AccreditationList;
import com.kidole.sport.domain.enumeration.AccreditationState;
/**
 * Integration tests for the {@link AccreditationResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class AccreditationResourceIT {

    private static final AccreditationList DEFAULT_ACCREDITATION_NAME = AccreditationList.ATHLETE;
    private static final AccreditationList UPDATED_ACCREDITATION_NAME = AccreditationList.SPARING;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCREDITATION_EMAIL = "L}Y@PeKF.wA*\\a";
    private static final String UPDATED_ACCREDITATION_EMAIL = "6rT@T>Q.21>Cp";

    private static final AccreditationState DEFAULT_ACCREDITATION_STATUS = AccreditationState.ACTIVE;
    private static final AccreditationState UPDATED_ACCREDITATION_STATUS = AccreditationState.WAITTING;

    private static final String DEFAULT_ACCREDITATION_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_ACCREDITATION_DETAIL = "BBBBBBBBBB";

    @Autowired
    private AccreditationRepository accreditationRepository;

    @Autowired
    private AccreditationMapper accreditationMapper;

    @Autowired
    private AccreditationService accreditationService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.AccreditationSearchRepositoryMockConfiguration
     */
    @Autowired
    private AccreditationSearchRepository mockAccreditationSearchRepository;

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

    private MockMvc restAccreditationMockMvc;

    private Accreditation accreditation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccreditationResource accreditationResource = new AccreditationResource(accreditationService);
        this.restAccreditationMockMvc = MockMvcBuilders.standaloneSetup(accreditationResource)
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
    public static Accreditation createEntity(EntityManager em) {
        Accreditation accreditation = new Accreditation()
            .accreditationName(DEFAULT_ACCREDITATION_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .accreditationEmail(DEFAULT_ACCREDITATION_EMAIL)
            .accreditationStatus(DEFAULT_ACCREDITATION_STATUS)
            .accreditationDetail(DEFAULT_ACCREDITATION_DETAIL);
        return accreditation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accreditation createUpdatedEntity(EntityManager em) {
        Accreditation accreditation = new Accreditation()
            .accreditationName(UPDATED_ACCREDITATION_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .accreditationEmail(UPDATED_ACCREDITATION_EMAIL)
            .accreditationStatus(UPDATED_ACCREDITATION_STATUS)
            .accreditationDetail(UPDATED_ACCREDITATION_DETAIL);
        return accreditation;
    }

    @BeforeEach
    public void initTest() {
        accreditation = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccreditation() throws Exception {
        int databaseSizeBeforeCreate = accreditationRepository.findAll().size();

        // Create the Accreditation
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);
        restAccreditationMockMvc.perform(post("/api/accreditations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationDTO)))
            .andExpect(status().isCreated());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeCreate + 1);
        Accreditation testAccreditation = accreditationList.get(accreditationList.size() - 1);
        assertThat(testAccreditation.getAccreditationName()).isEqualTo(DEFAULT_ACCREDITATION_NAME);
        assertThat(testAccreditation.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAccreditation.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAccreditation.getAccreditationEmail()).isEqualTo(DEFAULT_ACCREDITATION_EMAIL);
        assertThat(testAccreditation.getAccreditationStatus()).isEqualTo(DEFAULT_ACCREDITATION_STATUS);
        assertThat(testAccreditation.getAccreditationDetail()).isEqualTo(DEFAULT_ACCREDITATION_DETAIL);

        // Validate the Accreditation in Elasticsearch
        verify(mockAccreditationSearchRepository, times(1)).save(testAccreditation);
    }

    @Test
    @Transactional
    public void createAccreditationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accreditationRepository.findAll().size();

        // Create the Accreditation with an existing ID
        accreditation.setId(1L);
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccreditationMockMvc.perform(post("/api/accreditations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Accreditation in Elasticsearch
        verify(mockAccreditationSearchRepository, times(0)).save(accreditation);
    }


    @Test
    @Transactional
    public void checkAccreditationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = accreditationRepository.findAll().size();
        // set the field null
        accreditation.setAccreditationName(null);

        // Create the Accreditation, which fails.
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        restAccreditationMockMvc.perform(post("/api/accreditations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationDTO)))
            .andExpect(status().isBadRequest());

        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = accreditationRepository.findAll().size();
        // set the field null
        accreditation.setFirstName(null);

        // Create the Accreditation, which fails.
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        restAccreditationMockMvc.perform(post("/api/accreditations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationDTO)))
            .andExpect(status().isBadRequest());

        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = accreditationRepository.findAll().size();
        // set the field null
        accreditation.setLastName(null);

        // Create the Accreditation, which fails.
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        restAccreditationMockMvc.perform(post("/api/accreditations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationDTO)))
            .andExpect(status().isBadRequest());

        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccreditationEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = accreditationRepository.findAll().size();
        // set the field null
        accreditation.setAccreditationEmail(null);

        // Create the Accreditation, which fails.
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        restAccreditationMockMvc.perform(post("/api/accreditations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationDTO)))
            .andExpect(status().isBadRequest());

        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccreditationStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = accreditationRepository.findAll().size();
        // set the field null
        accreditation.setAccreditationStatus(null);

        // Create the Accreditation, which fails.
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        restAccreditationMockMvc.perform(post("/api/accreditations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationDTO)))
            .andExpect(status().isBadRequest());

        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccreditations() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList
        restAccreditationMockMvc.perform(get("/api/accreditations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accreditation.getId().intValue())))
            .andExpect(jsonPath("$.[*].accreditationName").value(hasItem(DEFAULT_ACCREDITATION_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].accreditationEmail").value(hasItem(DEFAULT_ACCREDITATION_EMAIL)))
            .andExpect(jsonPath("$.[*].accreditationStatus").value(hasItem(DEFAULT_ACCREDITATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].accreditationDetail").value(hasItem(DEFAULT_ACCREDITATION_DETAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getAccreditation() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get the accreditation
        restAccreditationMockMvc.perform(get("/api/accreditations/{id}", accreditation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accreditation.getId().intValue()))
            .andExpect(jsonPath("$.accreditationName").value(DEFAULT_ACCREDITATION_NAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.accreditationEmail").value(DEFAULT_ACCREDITATION_EMAIL))
            .andExpect(jsonPath("$.accreditationStatus").value(DEFAULT_ACCREDITATION_STATUS.toString()))
            .andExpect(jsonPath("$.accreditationDetail").value(DEFAULT_ACCREDITATION_DETAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAccreditation() throws Exception {
        // Get the accreditation
        restAccreditationMockMvc.perform(get("/api/accreditations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccreditation() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        int databaseSizeBeforeUpdate = accreditationRepository.findAll().size();

        // Update the accreditation
        Accreditation updatedAccreditation = accreditationRepository.findById(accreditation.getId()).get();
        // Disconnect from session so that the updates on updatedAccreditation are not directly saved in db
        em.detach(updatedAccreditation);
        updatedAccreditation
            .accreditationName(UPDATED_ACCREDITATION_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .accreditationEmail(UPDATED_ACCREDITATION_EMAIL)
            .accreditationStatus(UPDATED_ACCREDITATION_STATUS)
            .accreditationDetail(UPDATED_ACCREDITATION_DETAIL);
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(updatedAccreditation);

        restAccreditationMockMvc.perform(put("/api/accreditations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationDTO)))
            .andExpect(status().isOk());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeUpdate);
        Accreditation testAccreditation = accreditationList.get(accreditationList.size() - 1);
        assertThat(testAccreditation.getAccreditationName()).isEqualTo(UPDATED_ACCREDITATION_NAME);
        assertThat(testAccreditation.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAccreditation.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAccreditation.getAccreditationEmail()).isEqualTo(UPDATED_ACCREDITATION_EMAIL);
        assertThat(testAccreditation.getAccreditationStatus()).isEqualTo(UPDATED_ACCREDITATION_STATUS);
        assertThat(testAccreditation.getAccreditationDetail()).isEqualTo(UPDATED_ACCREDITATION_DETAIL);

        // Validate the Accreditation in Elasticsearch
        verify(mockAccreditationSearchRepository, times(1)).save(testAccreditation);
    }

    @Test
    @Transactional
    public void updateNonExistingAccreditation() throws Exception {
        int databaseSizeBeforeUpdate = accreditationRepository.findAll().size();

        // Create the Accreditation
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccreditationMockMvc.perform(put("/api/accreditations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Accreditation in Elasticsearch
        verify(mockAccreditationSearchRepository, times(0)).save(accreditation);
    }

    @Test
    @Transactional
    public void deleteAccreditation() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        int databaseSizeBeforeDelete = accreditationRepository.findAll().size();

        // Delete the accreditation
        restAccreditationMockMvc.perform(delete("/api/accreditations/{id}", accreditation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Accreditation in Elasticsearch
        verify(mockAccreditationSearchRepository, times(1)).deleteById(accreditation.getId());
    }

    @Test
    @Transactional
    public void searchAccreditation() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);
        when(mockAccreditationSearchRepository.search(queryStringQuery("id:" + accreditation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(accreditation), PageRequest.of(0, 1), 1));
        // Search the accreditation
        restAccreditationMockMvc.perform(get("/api/_search/accreditations?query=id:" + accreditation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accreditation.getId().intValue())))
            .andExpect(jsonPath("$.[*].accreditationName").value(hasItem(DEFAULT_ACCREDITATION_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].accreditationEmail").value(hasItem(DEFAULT_ACCREDITATION_EMAIL)))
            .andExpect(jsonPath("$.[*].accreditationStatus").value(hasItem(DEFAULT_ACCREDITATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].accreditationDetail").value(hasItem(DEFAULT_ACCREDITATION_DETAIL.toString())));
    }
}
