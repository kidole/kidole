package com.web.rest;

import com.KidoleApp;
import com.domain.PrestationService;
import com.repository.PrestationServiceRepository;
import com.service.PrestationServiceService;
import com.service.dto.PrestationServiceDTO;
import com.service.mapper.PrestationServiceMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PrestationServiceResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class PrestationServiceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private PrestationServiceRepository prestationServiceRepository;

    @Autowired
    private PrestationServiceMapper prestationServiceMapper;

    @Autowired
    private PrestationServiceService prestationServiceService;

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
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
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
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
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
        assertThat(testPrestationService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPrestationService.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testPrestationService.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testPrestationService.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
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
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        PrestationServiceDTO prestationServiceDTO = prestationServiceMapper.toDto(updatedPrestationService);

        restPrestationServiceMockMvc.perform(put("/api/prestation-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prestationServiceDTO)))
            .andExpect(status().isOk());

        // Validate the PrestationService in the database
        List<PrestationService> prestationServiceList = prestationServiceRepository.findAll();
        assertThat(prestationServiceList).hasSize(databaseSizeBeforeUpdate);
        PrestationService testPrestationService = prestationServiceList.get(prestationServiceList.size() - 1);
        assertThat(testPrestationService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPrestationService.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testPrestationService.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPrestationService.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
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
    }
}
