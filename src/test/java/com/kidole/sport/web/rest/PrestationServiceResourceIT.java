package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.PrestationService;
import com.kidole.sport.repository.PrestationServiceRepository;
import com.kidole.sport.repository.search.PrestationServiceSearchRepository;
import com.kidole.sport.service.PrestationServiceService;
import com.kidole.sport.service.dto.PrestationServiceDTO;
import com.kidole.sport.service.mapper.PrestationServiceMapper;
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

import com.kidole.sport.domain.enumeration.ServicesState;
/**
 * Integration tests for the {@link PrestationServiceResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class PrestationServiceResourceIT {

    private static final String DEFAULT_PRESTATION_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRESTATION_SERVICE_NAME = "BBBBBBBBBB";

    private static final ServicesState DEFAULT_PRESTATION_SERVICE_NAME_STATE = ServicesState.ACCEPT;
    private static final ServicesState UPDATED_PRESTATION_SERVICE_NAME_STATE = ServicesState.VIEW;

    private static final String DEFAULT_PRESTATION_SERVICE_NAME_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_PRESTATION_SERVICE_NAME_DETAIL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PRESTATION_SERVICE_NAME_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PRESTATION_SERVICE_NAME_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PRESTATION_SERVICE_NAME_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PRESTATION_SERVICE_NAME_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private PrestationServiceRepository prestationServiceRepository;

    @Autowired
    private PrestationServiceMapper prestationServiceMapper;

    @Autowired
    private PrestationServiceService prestationServiceService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.PrestationServiceSearchRepositoryMockConfiguration
     */
    @Autowired
    private PrestationServiceSearchRepository mockPrestationServiceSearchRepository;

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

    private MockMvc restPrestationServiceMockMvc;

    private PrestationService prestationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrestationServiceResource prestationServiceResource = new PrestationServiceResource(prestationServiceService);
        this.restPrestationServiceMockMvc = MockMvcBuilders.standaloneSetup(prestationServiceResource)
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
    public static PrestationService createEntity(EntityManager em) {
        PrestationService prestationService = new PrestationService()
            .prestationServiceName(DEFAULT_PRESTATION_SERVICE_NAME)
            .prestationServiceNameState(DEFAULT_PRESTATION_SERVICE_NAME_STATE)
            .prestationServiceNameDetail(DEFAULT_PRESTATION_SERVICE_NAME_DETAIL)
            .prestationServiceNameImage(DEFAULT_PRESTATION_SERVICE_NAME_IMAGE)
            .prestationServiceNameImageContentType(DEFAULT_PRESTATION_SERVICE_NAME_IMAGE_CONTENT_TYPE);
        return prestationService;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrestationService createUpdatedEntity(EntityManager em) {
        PrestationService prestationService = new PrestationService()
            .prestationServiceName(UPDATED_PRESTATION_SERVICE_NAME)
            .prestationServiceNameState(UPDATED_PRESTATION_SERVICE_NAME_STATE)
            .prestationServiceNameDetail(UPDATED_PRESTATION_SERVICE_NAME_DETAIL)
            .prestationServiceNameImage(UPDATED_PRESTATION_SERVICE_NAME_IMAGE)
            .prestationServiceNameImageContentType(UPDATED_PRESTATION_SERVICE_NAME_IMAGE_CONTENT_TYPE);
        return prestationService;
    }

    @BeforeEach
    public void initTest() {
        prestationService = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrestationService() throws Exception {
        int databaseSizeBeforeCreate = prestationServiceRepository.findAll().size();

        // Create the PrestationService
        PrestationServiceDTO prestationServiceDTO = prestationServiceMapper.toDto(prestationService);
        restPrestationServiceMockMvc.perform(post("/api/prestation-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestationServiceDTO)))
            .andExpect(status().isCreated());

        // Validate the PrestationService in the database
        List<PrestationService> prestationServiceList = prestationServiceRepository.findAll();
        assertThat(prestationServiceList).hasSize(databaseSizeBeforeCreate + 1);
        PrestationService testPrestationService = prestationServiceList.get(prestationServiceList.size() - 1);
        assertThat(testPrestationService.getPrestationServiceName()).isEqualTo(DEFAULT_PRESTATION_SERVICE_NAME);
        assertThat(testPrestationService.getPrestationServiceNameState()).isEqualTo(DEFAULT_PRESTATION_SERVICE_NAME_STATE);
        assertThat(testPrestationService.getPrestationServiceNameDetail()).isEqualTo(DEFAULT_PRESTATION_SERVICE_NAME_DETAIL);
        assertThat(testPrestationService.getPrestationServiceNameImage()).isEqualTo(DEFAULT_PRESTATION_SERVICE_NAME_IMAGE);
        assertThat(testPrestationService.getPrestationServiceNameImageContentType()).isEqualTo(DEFAULT_PRESTATION_SERVICE_NAME_IMAGE_CONTENT_TYPE);

        // Validate the PrestationService in Elasticsearch
        verify(mockPrestationServiceSearchRepository, times(1)).save(testPrestationService);
    }

    @Test
    @Transactional
    public void createPrestationServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prestationServiceRepository.findAll().size();

        // Create the PrestationService with an existing ID
        prestationService.setId(1L);
        PrestationServiceDTO prestationServiceDTO = prestationServiceMapper.toDto(prestationService);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrestationServiceMockMvc.perform(post("/api/prestation-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestationServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrestationService in the database
        List<PrestationService> prestationServiceList = prestationServiceRepository.findAll();
        assertThat(prestationServiceList).hasSize(databaseSizeBeforeCreate);

        // Validate the PrestationService in Elasticsearch
        verify(mockPrestationServiceSearchRepository, times(0)).save(prestationService);
    }


    @Test
    @Transactional
    public void checkPrestationServiceNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = prestationServiceRepository.findAll().size();
        // set the field null
        prestationService.setPrestationServiceName(null);

        // Create the PrestationService, which fails.
        PrestationServiceDTO prestationServiceDTO = prestationServiceMapper.toDto(prestationService);

        restPrestationServiceMockMvc.perform(post("/api/prestation-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestationServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PrestationService> prestationServiceList = prestationServiceRepository.findAll();
        assertThat(prestationServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrestationServiceNameStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = prestationServiceRepository.findAll().size();
        // set the field null
        prestationService.setPrestationServiceNameState(null);

        // Create the PrestationService, which fails.
        PrestationServiceDTO prestationServiceDTO = prestationServiceMapper.toDto(prestationService);

        restPrestationServiceMockMvc.perform(post("/api/prestation-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestationServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PrestationService> prestationServiceList = prestationServiceRepository.findAll();
        assertThat(prestationServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrestationServices() throws Exception {
        // Initialize the database
        prestationServiceRepository.saveAndFlush(prestationService);

        // Get all the prestationServiceList
        restPrestationServiceMockMvc.perform(get("/api/prestation-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestationService.getId().intValue())))
            .andExpect(jsonPath("$.[*].prestationServiceName").value(hasItem(DEFAULT_PRESTATION_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].prestationServiceNameState").value(hasItem(DEFAULT_PRESTATION_SERVICE_NAME_STATE.toString())))
            .andExpect(jsonPath("$.[*].prestationServiceNameDetail").value(hasItem(DEFAULT_PRESTATION_SERVICE_NAME_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].prestationServiceNameImageContentType").value(hasItem(DEFAULT_PRESTATION_SERVICE_NAME_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].prestationServiceNameImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRESTATION_SERVICE_NAME_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getPrestationService() throws Exception {
        // Initialize the database
        prestationServiceRepository.saveAndFlush(prestationService);

        // Get the prestationService
        restPrestationServiceMockMvc.perform(get("/api/prestation-services/{id}", prestationService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prestationService.getId().intValue()))
            .andExpect(jsonPath("$.prestationServiceName").value(DEFAULT_PRESTATION_SERVICE_NAME))
            .andExpect(jsonPath("$.prestationServiceNameState").value(DEFAULT_PRESTATION_SERVICE_NAME_STATE.toString()))
            .andExpect(jsonPath("$.prestationServiceNameDetail").value(DEFAULT_PRESTATION_SERVICE_NAME_DETAIL.toString()))
            .andExpect(jsonPath("$.prestationServiceNameImageContentType").value(DEFAULT_PRESTATION_SERVICE_NAME_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.prestationServiceNameImage").value(Base64Utils.encodeToString(DEFAULT_PRESTATION_SERVICE_NAME_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingPrestationService() throws Exception {
        // Get the prestationService
        restPrestationServiceMockMvc.perform(get("/api/prestation-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrestationService() throws Exception {
        // Initialize the database
        prestationServiceRepository.saveAndFlush(prestationService);

        int databaseSizeBeforeUpdate = prestationServiceRepository.findAll().size();

        // Update the prestationService
        PrestationService updatedPrestationService = prestationServiceRepository.findById(prestationService.getId()).get();
        // Disconnect from session so that the updates on updatedPrestationService are not directly saved in db
        em.detach(updatedPrestationService);
        updatedPrestationService
            .prestationServiceName(UPDATED_PRESTATION_SERVICE_NAME)
            .prestationServiceNameState(UPDATED_PRESTATION_SERVICE_NAME_STATE)
            .prestationServiceNameDetail(UPDATED_PRESTATION_SERVICE_NAME_DETAIL)
            .prestationServiceNameImage(UPDATED_PRESTATION_SERVICE_NAME_IMAGE)
            .prestationServiceNameImageContentType(UPDATED_PRESTATION_SERVICE_NAME_IMAGE_CONTENT_TYPE);
        PrestationServiceDTO prestationServiceDTO = prestationServiceMapper.toDto(updatedPrestationService);

        restPrestationServiceMockMvc.perform(put("/api/prestation-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestationServiceDTO)))
            .andExpect(status().isOk());

        // Validate the PrestationService in the database
        List<PrestationService> prestationServiceList = prestationServiceRepository.findAll();
        assertThat(prestationServiceList).hasSize(databaseSizeBeforeUpdate);
        PrestationService testPrestationService = prestationServiceList.get(prestationServiceList.size() - 1);
        assertThat(testPrestationService.getPrestationServiceName()).isEqualTo(UPDATED_PRESTATION_SERVICE_NAME);
        assertThat(testPrestationService.getPrestationServiceNameState()).isEqualTo(UPDATED_PRESTATION_SERVICE_NAME_STATE);
        assertThat(testPrestationService.getPrestationServiceNameDetail()).isEqualTo(UPDATED_PRESTATION_SERVICE_NAME_DETAIL);
        assertThat(testPrestationService.getPrestationServiceNameImage()).isEqualTo(UPDATED_PRESTATION_SERVICE_NAME_IMAGE);
        assertThat(testPrestationService.getPrestationServiceNameImageContentType()).isEqualTo(UPDATED_PRESTATION_SERVICE_NAME_IMAGE_CONTENT_TYPE);

        // Validate the PrestationService in Elasticsearch
        verify(mockPrestationServiceSearchRepository, times(1)).save(testPrestationService);
    }

    @Test
    @Transactional
    public void updateNonExistingPrestationService() throws Exception {
        int databaseSizeBeforeUpdate = prestationServiceRepository.findAll().size();

        // Create the PrestationService
        PrestationServiceDTO prestationServiceDTO = prestationServiceMapper.toDto(prestationService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrestationServiceMockMvc.perform(put("/api/prestation-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestationServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrestationService in the database
        List<PrestationService> prestationServiceList = prestationServiceRepository.findAll();
        assertThat(prestationServiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PrestationService in Elasticsearch
        verify(mockPrestationServiceSearchRepository, times(0)).save(prestationService);
    }

    @Test
    @Transactional
    public void deletePrestationService() throws Exception {
        // Initialize the database
        prestationServiceRepository.saveAndFlush(prestationService);

        int databaseSizeBeforeDelete = prestationServiceRepository.findAll().size();

        // Delete the prestationService
        restPrestationServiceMockMvc.perform(delete("/api/prestation-services/{id}", prestationService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrestationService> prestationServiceList = prestationServiceRepository.findAll();
        assertThat(prestationServiceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PrestationService in Elasticsearch
        verify(mockPrestationServiceSearchRepository, times(1)).deleteById(prestationService.getId());
    }

    @Test
    @Transactional
    public void searchPrestationService() throws Exception {
        // Initialize the database
        prestationServiceRepository.saveAndFlush(prestationService);
        when(mockPrestationServiceSearchRepository.search(queryStringQuery("id:" + prestationService.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(prestationService), PageRequest.of(0, 1), 1));
        // Search the prestationService
        restPrestationServiceMockMvc.perform(get("/api/_search/prestation-services?query=id:" + prestationService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prestationService.getId().intValue())))
            .andExpect(jsonPath("$.[*].prestationServiceName").value(hasItem(DEFAULT_PRESTATION_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].prestationServiceNameState").value(hasItem(DEFAULT_PRESTATION_SERVICE_NAME_STATE.toString())))
            .andExpect(jsonPath("$.[*].prestationServiceNameDetail").value(hasItem(DEFAULT_PRESTATION_SERVICE_NAME_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].prestationServiceNameImageContentType").value(hasItem(DEFAULT_PRESTATION_SERVICE_NAME_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].prestationServiceNameImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRESTATION_SERVICE_NAME_IMAGE))));
    }
}
