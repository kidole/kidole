package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.Rubrique;
import com.kidole.sport.repository.RubriqueRepository;
import com.kidole.sport.repository.search.RubriqueSearchRepository;
import com.kidole.sport.service.RubriqueService;
import com.kidole.sport.service.dto.RubriqueDTO;
import com.kidole.sport.service.mapper.RubriqueMapper;
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

/**
 * Integration tests for the {@link RubriqueResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class RubriqueResourceIT {

    private static final String DEFAULT_RUBRIQUE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RUBRIQUE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RUBRIQUE_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_RUBRIQUE_DETAILS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_RUBRIQUE_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RUBRIQUE_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RUBRIQUE_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RUBRIQUE_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private RubriqueRepository rubriqueRepository;

    @Autowired
    private RubriqueMapper rubriqueMapper;

    @Autowired
    private RubriqueService rubriqueService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.RubriqueSearchRepositoryMockConfiguration
     */
    @Autowired
    private RubriqueSearchRepository mockRubriqueSearchRepository;

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

    private MockMvc restRubriqueMockMvc;

    private Rubrique rubrique;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RubriqueResource rubriqueResource = new RubriqueResource(rubriqueService);
        this.restRubriqueMockMvc = MockMvcBuilders.standaloneSetup(rubriqueResource)
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
    public static Rubrique createEntity(EntityManager em) {
        Rubrique rubrique = new Rubrique()
            .rubriqueName(DEFAULT_RUBRIQUE_NAME)
            .rubriqueDetails(DEFAULT_RUBRIQUE_DETAILS)
            .rubriqueImage(DEFAULT_RUBRIQUE_IMAGE)
            .rubriqueImageContentType(DEFAULT_RUBRIQUE_IMAGE_CONTENT_TYPE);
        return rubrique;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rubrique createUpdatedEntity(EntityManager em) {
        Rubrique rubrique = new Rubrique()
            .rubriqueName(UPDATED_RUBRIQUE_NAME)
            .rubriqueDetails(UPDATED_RUBRIQUE_DETAILS)
            .rubriqueImage(UPDATED_RUBRIQUE_IMAGE)
            .rubriqueImageContentType(UPDATED_RUBRIQUE_IMAGE_CONTENT_TYPE);
        return rubrique;
    }

    @BeforeEach
    public void initTest() {
        rubrique = createEntity(em);
    }

    @Test
    @Transactional
    public void createRubrique() throws Exception {
        int databaseSizeBeforeCreate = rubriqueRepository.findAll().size();

        // Create the Rubrique
        RubriqueDTO rubriqueDTO = rubriqueMapper.toDto(rubrique);
        restRubriqueMockMvc.perform(post("/api/rubriques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubriqueDTO)))
            .andExpect(status().isCreated());

        // Validate the Rubrique in the database
        List<Rubrique> rubriqueList = rubriqueRepository.findAll();
        assertThat(rubriqueList).hasSize(databaseSizeBeforeCreate + 1);
        Rubrique testRubrique = rubriqueList.get(rubriqueList.size() - 1);
        assertThat(testRubrique.getRubriqueName()).isEqualTo(DEFAULT_RUBRIQUE_NAME);
        assertThat(testRubrique.getRubriqueDetails()).isEqualTo(DEFAULT_RUBRIQUE_DETAILS);
        assertThat(testRubrique.getRubriqueImage()).isEqualTo(DEFAULT_RUBRIQUE_IMAGE);
        assertThat(testRubrique.getRubriqueImageContentType()).isEqualTo(DEFAULT_RUBRIQUE_IMAGE_CONTENT_TYPE);

        // Validate the Rubrique in Elasticsearch
        verify(mockRubriqueSearchRepository, times(1)).save(testRubrique);
    }

    @Test
    @Transactional
    public void createRubriqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rubriqueRepository.findAll().size();

        // Create the Rubrique with an existing ID
        rubrique.setId(1L);
        RubriqueDTO rubriqueDTO = rubriqueMapper.toDto(rubrique);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRubriqueMockMvc.perform(post("/api/rubriques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubriqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rubrique in the database
        List<Rubrique> rubriqueList = rubriqueRepository.findAll();
        assertThat(rubriqueList).hasSize(databaseSizeBeforeCreate);

        // Validate the Rubrique in Elasticsearch
        verify(mockRubriqueSearchRepository, times(0)).save(rubrique);
    }


    @Test
    @Transactional
    public void checkRubriqueNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rubriqueRepository.findAll().size();
        // set the field null
        rubrique.setRubriqueName(null);

        // Create the Rubrique, which fails.
        RubriqueDTO rubriqueDTO = rubriqueMapper.toDto(rubrique);

        restRubriqueMockMvc.perform(post("/api/rubriques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubriqueDTO)))
            .andExpect(status().isBadRequest());

        List<Rubrique> rubriqueList = rubriqueRepository.findAll();
        assertThat(rubriqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRubriques() throws Exception {
        // Initialize the database
        rubriqueRepository.saveAndFlush(rubrique);

        // Get all the rubriqueList
        restRubriqueMockMvc.perform(get("/api/rubriques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rubrique.getId().intValue())))
            .andExpect(jsonPath("$.[*].rubriqueName").value(hasItem(DEFAULT_RUBRIQUE_NAME)))
            .andExpect(jsonPath("$.[*].rubriqueDetails").value(hasItem(DEFAULT_RUBRIQUE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].rubriqueImageContentType").value(hasItem(DEFAULT_RUBRIQUE_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].rubriqueImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_RUBRIQUE_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getRubrique() throws Exception {
        // Initialize the database
        rubriqueRepository.saveAndFlush(rubrique);

        // Get the rubrique
        restRubriqueMockMvc.perform(get("/api/rubriques/{id}", rubrique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rubrique.getId().intValue()))
            .andExpect(jsonPath("$.rubriqueName").value(DEFAULT_RUBRIQUE_NAME))
            .andExpect(jsonPath("$.rubriqueDetails").value(DEFAULT_RUBRIQUE_DETAILS.toString()))
            .andExpect(jsonPath("$.rubriqueImageContentType").value(DEFAULT_RUBRIQUE_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.rubriqueImage").value(Base64Utils.encodeToString(DEFAULT_RUBRIQUE_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingRubrique() throws Exception {
        // Get the rubrique
        restRubriqueMockMvc.perform(get("/api/rubriques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRubrique() throws Exception {
        // Initialize the database
        rubriqueRepository.saveAndFlush(rubrique);

        int databaseSizeBeforeUpdate = rubriqueRepository.findAll().size();

        // Update the rubrique
        Rubrique updatedRubrique = rubriqueRepository.findById(rubrique.getId()).get();
        // Disconnect from session so that the updates on updatedRubrique are not directly saved in db
        em.detach(updatedRubrique);
        updatedRubrique
            .rubriqueName(UPDATED_RUBRIQUE_NAME)
            .rubriqueDetails(UPDATED_RUBRIQUE_DETAILS)
            .rubriqueImage(UPDATED_RUBRIQUE_IMAGE)
            .rubriqueImageContentType(UPDATED_RUBRIQUE_IMAGE_CONTENT_TYPE);
        RubriqueDTO rubriqueDTO = rubriqueMapper.toDto(updatedRubrique);

        restRubriqueMockMvc.perform(put("/api/rubriques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubriqueDTO)))
            .andExpect(status().isOk());

        // Validate the Rubrique in the database
        List<Rubrique> rubriqueList = rubriqueRepository.findAll();
        assertThat(rubriqueList).hasSize(databaseSizeBeforeUpdate);
        Rubrique testRubrique = rubriqueList.get(rubriqueList.size() - 1);
        assertThat(testRubrique.getRubriqueName()).isEqualTo(UPDATED_RUBRIQUE_NAME);
        assertThat(testRubrique.getRubriqueDetails()).isEqualTo(UPDATED_RUBRIQUE_DETAILS);
        assertThat(testRubrique.getRubriqueImage()).isEqualTo(UPDATED_RUBRIQUE_IMAGE);
        assertThat(testRubrique.getRubriqueImageContentType()).isEqualTo(UPDATED_RUBRIQUE_IMAGE_CONTENT_TYPE);

        // Validate the Rubrique in Elasticsearch
        verify(mockRubriqueSearchRepository, times(1)).save(testRubrique);
    }

    @Test
    @Transactional
    public void updateNonExistingRubrique() throws Exception {
        int databaseSizeBeforeUpdate = rubriqueRepository.findAll().size();

        // Create the Rubrique
        RubriqueDTO rubriqueDTO = rubriqueMapper.toDto(rubrique);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRubriqueMockMvc.perform(put("/api/rubriques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubriqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rubrique in the database
        List<Rubrique> rubriqueList = rubriqueRepository.findAll();
        assertThat(rubriqueList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Rubrique in Elasticsearch
        verify(mockRubriqueSearchRepository, times(0)).save(rubrique);
    }

    @Test
    @Transactional
    public void deleteRubrique() throws Exception {
        // Initialize the database
        rubriqueRepository.saveAndFlush(rubrique);

        int databaseSizeBeforeDelete = rubriqueRepository.findAll().size();

        // Delete the rubrique
        restRubriqueMockMvc.perform(delete("/api/rubriques/{id}", rubrique.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rubrique> rubriqueList = rubriqueRepository.findAll();
        assertThat(rubriqueList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Rubrique in Elasticsearch
        verify(mockRubriqueSearchRepository, times(1)).deleteById(rubrique.getId());
    }

    @Test
    @Transactional
    public void searchRubrique() throws Exception {
        // Initialize the database
        rubriqueRepository.saveAndFlush(rubrique);
        when(mockRubriqueSearchRepository.search(queryStringQuery("id:" + rubrique.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(rubrique), PageRequest.of(0, 1), 1));
        // Search the rubrique
        restRubriqueMockMvc.perform(get("/api/_search/rubriques?query=id:" + rubrique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rubrique.getId().intValue())))
            .andExpect(jsonPath("$.[*].rubriqueName").value(hasItem(DEFAULT_RUBRIQUE_NAME)))
            .andExpect(jsonPath("$.[*].rubriqueDetails").value(hasItem(DEFAULT_RUBRIQUE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].rubriqueImageContentType").value(hasItem(DEFAULT_RUBRIQUE_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].rubriqueImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_RUBRIQUE_IMAGE))));
    }
}
