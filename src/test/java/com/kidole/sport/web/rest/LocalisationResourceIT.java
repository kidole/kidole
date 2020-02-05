package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.Localisation;
import com.kidole.sport.repository.LocalisationRepository;
import com.kidole.sport.repository.search.LocalisationSearchRepository;
import com.kidole.sport.service.LocalisationService;
import com.kidole.sport.service.dto.LocalisationDTO;
import com.kidole.sport.service.mapper.LocalisationMapper;
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
 * Integration tests for the {@link LocalisationResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class LocalisationResourceIT {

    private static final String DEFAULT_LOCALISATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCALISATION_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_LOCALISATION_LATITUDE = 1D;
    private static final Double UPDATED_LOCALISATION_LATITUDE = 2D;

    private static final Double DEFAULT_LOCALISATION_LONGITUDE = 1D;
    private static final Double UPDATED_LOCALISATION_LONGITUDE = 2D;

    private static final String DEFAULT_LOCALISATION_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_LOCALISATION_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALISATION_TOWN = "AAAAAAAAAA";
    private static final String UPDATED_LOCALISATION_TOWN = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALISATION_REGION = "AAAAAAAAAA";
    private static final String UPDATED_LOCALISATION_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALISATION_LOCALITY = "AAAAAAAAAA";
    private static final String UPDATED_LOCALISATION_LOCALITY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SITE = false;
    private static final Boolean UPDATED_IS_SITE = true;

    @Autowired
    private LocalisationRepository localisationRepository;

    @Autowired
    private LocalisationMapper localisationMapper;

    @Autowired
    private LocalisationService localisationService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.LocalisationSearchRepositoryMockConfiguration
     */
    @Autowired
    private LocalisationSearchRepository mockLocalisationSearchRepository;

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

    private MockMvc restLocalisationMockMvc;

    private Localisation localisation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocalisationResource localisationResource = new LocalisationResource(localisationService);
        this.restLocalisationMockMvc = MockMvcBuilders.standaloneSetup(localisationResource)
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
    public static Localisation createEntity(EntityManager em) {
        Localisation localisation = new Localisation()
            .localisationName(DEFAULT_LOCALISATION_NAME)
            .localisationLatitude(DEFAULT_LOCALISATION_LATITUDE)
            .localisationLongitude(DEFAULT_LOCALISATION_LONGITUDE)
            .localisationCountry(DEFAULT_LOCALISATION_COUNTRY)
            .localisationTown(DEFAULT_LOCALISATION_TOWN)
            .localisationRegion(DEFAULT_LOCALISATION_REGION)
            .localisationLocality(DEFAULT_LOCALISATION_LOCALITY)
            .isSite(DEFAULT_IS_SITE);
        return localisation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localisation createUpdatedEntity(EntityManager em) {
        Localisation localisation = new Localisation()
            .localisationName(UPDATED_LOCALISATION_NAME)
            .localisationLatitude(UPDATED_LOCALISATION_LATITUDE)
            .localisationLongitude(UPDATED_LOCALISATION_LONGITUDE)
            .localisationCountry(UPDATED_LOCALISATION_COUNTRY)
            .localisationTown(UPDATED_LOCALISATION_TOWN)
            .localisationRegion(UPDATED_LOCALISATION_REGION)
            .localisationLocality(UPDATED_LOCALISATION_LOCALITY)
            .isSite(UPDATED_IS_SITE);
        return localisation;
    }

    @BeforeEach
    public void initTest() {
        localisation = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalisation() throws Exception {
        int databaseSizeBeforeCreate = localisationRepository.findAll().size();

        // Create the Localisation
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);
        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisationDTO)))
            .andExpect(status().isCreated());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeCreate + 1);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getLocalisationName()).isEqualTo(DEFAULT_LOCALISATION_NAME);
        assertThat(testLocalisation.getLocalisationLatitude()).isEqualTo(DEFAULT_LOCALISATION_LATITUDE);
        assertThat(testLocalisation.getLocalisationLongitude()).isEqualTo(DEFAULT_LOCALISATION_LONGITUDE);
        assertThat(testLocalisation.getLocalisationCountry()).isEqualTo(DEFAULT_LOCALISATION_COUNTRY);
        assertThat(testLocalisation.getLocalisationTown()).isEqualTo(DEFAULT_LOCALISATION_TOWN);
        assertThat(testLocalisation.getLocalisationRegion()).isEqualTo(DEFAULT_LOCALISATION_REGION);
        assertThat(testLocalisation.getLocalisationLocality()).isEqualTo(DEFAULT_LOCALISATION_LOCALITY);
        assertThat(testLocalisation.isIsSite()).isEqualTo(DEFAULT_IS_SITE);

        // Validate the Localisation in Elasticsearch
        verify(mockLocalisationSearchRepository, times(1)).save(testLocalisation);
    }

    @Test
    @Transactional
    public void createLocalisationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localisationRepository.findAll().size();

        // Create the Localisation with an existing ID
        localisation.setId(1L);
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Localisation in Elasticsearch
        verify(mockLocalisationSearchRepository, times(0)).save(localisation);
    }


    @Test
    @Transactional
    public void checkLocalisationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = localisationRepository.findAll().size();
        // set the field null
        localisation.setLocalisationName(null);

        // Create the Localisation, which fails.
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisationDTO)))
            .andExpect(status().isBadRequest());

        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocalisationLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = localisationRepository.findAll().size();
        // set the field null
        localisation.setLocalisationLatitude(null);

        // Create the Localisation, which fails.
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisationDTO)))
            .andExpect(status().isBadRequest());

        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocalisationLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = localisationRepository.findAll().size();
        // set the field null
        localisation.setLocalisationLongitude(null);

        // Create the Localisation, which fails.
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisationDTO)))
            .andExpect(status().isBadRequest());

        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocalisationCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = localisationRepository.findAll().size();
        // set the field null
        localisation.setLocalisationCountry(null);

        // Create the Localisation, which fails.
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisationDTO)))
            .andExpect(status().isBadRequest());

        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocalisationTownIsRequired() throws Exception {
        int databaseSizeBeforeTest = localisationRepository.findAll().size();
        // set the field null
        localisation.setLocalisationTown(null);

        // Create the Localisation, which fails.
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisationDTO)))
            .andExpect(status().isBadRequest());

        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocalisationRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = localisationRepository.findAll().size();
        // set the field null
        localisation.setLocalisationRegion(null);

        // Create the Localisation, which fails.
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisationDTO)))
            .andExpect(status().isBadRequest());

        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocalisationLocalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = localisationRepository.findAll().size();
        // set the field null
        localisation.setLocalisationLocality(null);

        // Create the Localisation, which fails.
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisationDTO)))
            .andExpect(status().isBadRequest());

        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsSiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = localisationRepository.findAll().size();
        // set the field null
        localisation.setIsSite(null);

        // Create the Localisation, which fails.
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        restLocalisationMockMvc.perform(post("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisationDTO)))
            .andExpect(status().isBadRequest());

        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocalisations() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);

        // Get all the localisationList
        restLocalisationMockMvc.perform(get("/api/localisations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].localisationName").value(hasItem(DEFAULT_LOCALISATION_NAME)))
            .andExpect(jsonPath("$.[*].localisationLatitude").value(hasItem(DEFAULT_LOCALISATION_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].localisationLongitude").value(hasItem(DEFAULT_LOCALISATION_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].localisationCountry").value(hasItem(DEFAULT_LOCALISATION_COUNTRY)))
            .andExpect(jsonPath("$.[*].localisationTown").value(hasItem(DEFAULT_LOCALISATION_TOWN)))
            .andExpect(jsonPath("$.[*].localisationRegion").value(hasItem(DEFAULT_LOCALISATION_REGION)))
            .andExpect(jsonPath("$.[*].localisationLocality").value(hasItem(DEFAULT_LOCALISATION_LOCALITY)))
            .andExpect(jsonPath("$.[*].isSite").value(hasItem(DEFAULT_IS_SITE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);

        // Get the localisation
        restLocalisationMockMvc.perform(get("/api/localisations/{id}", localisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localisation.getId().intValue()))
            .andExpect(jsonPath("$.localisationName").value(DEFAULT_LOCALISATION_NAME))
            .andExpect(jsonPath("$.localisationLatitude").value(DEFAULT_LOCALISATION_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.localisationLongitude").value(DEFAULT_LOCALISATION_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.localisationCountry").value(DEFAULT_LOCALISATION_COUNTRY))
            .andExpect(jsonPath("$.localisationTown").value(DEFAULT_LOCALISATION_TOWN))
            .andExpect(jsonPath("$.localisationRegion").value(DEFAULT_LOCALISATION_REGION))
            .andExpect(jsonPath("$.localisationLocality").value(DEFAULT_LOCALISATION_LOCALITY))
            .andExpect(jsonPath("$.isSite").value(DEFAULT_IS_SITE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLocalisation() throws Exception {
        // Get the localisation
        restLocalisationMockMvc.perform(get("/api/localisations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);

        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();

        // Update the localisation
        Localisation updatedLocalisation = localisationRepository.findById(localisation.getId()).get();
        // Disconnect from session so that the updates on updatedLocalisation are not directly saved in db
        em.detach(updatedLocalisation);
        updatedLocalisation
            .localisationName(UPDATED_LOCALISATION_NAME)
            .localisationLatitude(UPDATED_LOCALISATION_LATITUDE)
            .localisationLongitude(UPDATED_LOCALISATION_LONGITUDE)
            .localisationCountry(UPDATED_LOCALISATION_COUNTRY)
            .localisationTown(UPDATED_LOCALISATION_TOWN)
            .localisationRegion(UPDATED_LOCALISATION_REGION)
            .localisationLocality(UPDATED_LOCALISATION_LOCALITY)
            .isSite(UPDATED_IS_SITE);
        LocalisationDTO localisationDTO = localisationMapper.toDto(updatedLocalisation);

        restLocalisationMockMvc.perform(put("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisationDTO)))
            .andExpect(status().isOk());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);
        Localisation testLocalisation = localisationList.get(localisationList.size() - 1);
        assertThat(testLocalisation.getLocalisationName()).isEqualTo(UPDATED_LOCALISATION_NAME);
        assertThat(testLocalisation.getLocalisationLatitude()).isEqualTo(UPDATED_LOCALISATION_LATITUDE);
        assertThat(testLocalisation.getLocalisationLongitude()).isEqualTo(UPDATED_LOCALISATION_LONGITUDE);
        assertThat(testLocalisation.getLocalisationCountry()).isEqualTo(UPDATED_LOCALISATION_COUNTRY);
        assertThat(testLocalisation.getLocalisationTown()).isEqualTo(UPDATED_LOCALISATION_TOWN);
        assertThat(testLocalisation.getLocalisationRegion()).isEqualTo(UPDATED_LOCALISATION_REGION);
        assertThat(testLocalisation.getLocalisationLocality()).isEqualTo(UPDATED_LOCALISATION_LOCALITY);
        assertThat(testLocalisation.isIsSite()).isEqualTo(UPDATED_IS_SITE);

        // Validate the Localisation in Elasticsearch
        verify(mockLocalisationSearchRepository, times(1)).save(testLocalisation);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalisation() throws Exception {
        int databaseSizeBeforeUpdate = localisationRepository.findAll().size();

        // Create the Localisation
        LocalisationDTO localisationDTO = localisationMapper.toDto(localisation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalisationMockMvc.perform(put("/api/localisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localisationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Localisation in the database
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Localisation in Elasticsearch
        verify(mockLocalisationSearchRepository, times(0)).save(localisation);
    }

    @Test
    @Transactional
    public void deleteLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);

        int databaseSizeBeforeDelete = localisationRepository.findAll().size();

        // Delete the localisation
        restLocalisationMockMvc.perform(delete("/api/localisations/{id}", localisation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Localisation> localisationList = localisationRepository.findAll();
        assertThat(localisationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Localisation in Elasticsearch
        verify(mockLocalisationSearchRepository, times(1)).deleteById(localisation.getId());
    }

    @Test
    @Transactional
    public void searchLocalisation() throws Exception {
        // Initialize the database
        localisationRepository.saveAndFlush(localisation);
        when(mockLocalisationSearchRepository.search(queryStringQuery("id:" + localisation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(localisation), PageRequest.of(0, 1), 1));
        // Search the localisation
        restLocalisationMockMvc.perform(get("/api/_search/localisations?query=id:" + localisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].localisationName").value(hasItem(DEFAULT_LOCALISATION_NAME)))
            .andExpect(jsonPath("$.[*].localisationLatitude").value(hasItem(DEFAULT_LOCALISATION_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].localisationLongitude").value(hasItem(DEFAULT_LOCALISATION_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].localisationCountry").value(hasItem(DEFAULT_LOCALISATION_COUNTRY)))
            .andExpect(jsonPath("$.[*].localisationTown").value(hasItem(DEFAULT_LOCALISATION_TOWN)))
            .andExpect(jsonPath("$.[*].localisationRegion").value(hasItem(DEFAULT_LOCALISATION_REGION)))
            .andExpect(jsonPath("$.[*].localisationLocality").value(hasItem(DEFAULT_LOCALISATION_LOCALITY)))
            .andExpect(jsonPath("$.[*].isSite").value(hasItem(DEFAULT_IS_SITE.booleanValue())));
    }
}
