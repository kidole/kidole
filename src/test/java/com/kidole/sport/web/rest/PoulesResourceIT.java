package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.Poules;
import com.kidole.sport.repository.PoulesRepository;
import com.kidole.sport.repository.search.PoulesSearchRepository;
import com.kidole.sport.service.PoulesService;
import com.kidole.sport.service.dto.PoulesDTO;
import com.kidole.sport.service.mapper.PoulesMapper;
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
 * Integration tests for the {@link PoulesResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class PoulesResourceIT {

    private static final String DEFAULT_POULES_NAME = "AAAAAAAAAA";
    private static final String UPDATED_POULES_NAME = "BBBBBBBBBB";

    @Autowired
    private PoulesRepository poulesRepository;

    @Autowired
    private PoulesMapper poulesMapper;

    @Autowired
    private PoulesService poulesService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.PoulesSearchRepositoryMockConfiguration
     */
    @Autowired
    private PoulesSearchRepository mockPoulesSearchRepository;

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

    private MockMvc restPoulesMockMvc;

    private Poules poules;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PoulesResource poulesResource = new PoulesResource(poulesService);
        this.restPoulesMockMvc = MockMvcBuilders.standaloneSetup(poulesResource)
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
    public static Poules createEntity(EntityManager em) {
        Poules poules = new Poules()
            .poulesName(DEFAULT_POULES_NAME);
        return poules;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Poules createUpdatedEntity(EntityManager em) {
        Poules poules = new Poules()
            .poulesName(UPDATED_POULES_NAME);
        return poules;
    }

    @BeforeEach
    public void initTest() {
        poules = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoules() throws Exception {
        int databaseSizeBeforeCreate = poulesRepository.findAll().size();

        // Create the Poules
        PoulesDTO poulesDTO = poulesMapper.toDto(poules);
        restPoulesMockMvc.perform(post("/api/poules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poulesDTO)))
            .andExpect(status().isCreated());

        // Validate the Poules in the database
        List<Poules> poulesList = poulesRepository.findAll();
        assertThat(poulesList).hasSize(databaseSizeBeforeCreate + 1);
        Poules testPoules = poulesList.get(poulesList.size() - 1);
        assertThat(testPoules.getPoulesName()).isEqualTo(DEFAULT_POULES_NAME);

        // Validate the Poules in Elasticsearch
        verify(mockPoulesSearchRepository, times(1)).save(testPoules);
    }

    @Test
    @Transactional
    public void createPoulesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poulesRepository.findAll().size();

        // Create the Poules with an existing ID
        poules.setId(1L);
        PoulesDTO poulesDTO = poulesMapper.toDto(poules);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoulesMockMvc.perform(post("/api/poules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poulesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Poules in the database
        List<Poules> poulesList = poulesRepository.findAll();
        assertThat(poulesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Poules in Elasticsearch
        verify(mockPoulesSearchRepository, times(0)).save(poules);
    }


    @Test
    @Transactional
    public void checkPoulesNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = poulesRepository.findAll().size();
        // set the field null
        poules.setPoulesName(null);

        // Create the Poules, which fails.
        PoulesDTO poulesDTO = poulesMapper.toDto(poules);

        restPoulesMockMvc.perform(post("/api/poules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poulesDTO)))
            .andExpect(status().isBadRequest());

        List<Poules> poulesList = poulesRepository.findAll();
        assertThat(poulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPoules() throws Exception {
        // Initialize the database
        poulesRepository.saveAndFlush(poules);

        // Get all the poulesList
        restPoulesMockMvc.perform(get("/api/poules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poules.getId().intValue())))
            .andExpect(jsonPath("$.[*].poulesName").value(hasItem(DEFAULT_POULES_NAME)));
    }
    
    @Test
    @Transactional
    public void getPoules() throws Exception {
        // Initialize the database
        poulesRepository.saveAndFlush(poules);

        // Get the poules
        restPoulesMockMvc.perform(get("/api/poules/{id}", poules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(poules.getId().intValue()))
            .andExpect(jsonPath("$.poulesName").value(DEFAULT_POULES_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingPoules() throws Exception {
        // Get the poules
        restPoulesMockMvc.perform(get("/api/poules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoules() throws Exception {
        // Initialize the database
        poulesRepository.saveAndFlush(poules);

        int databaseSizeBeforeUpdate = poulesRepository.findAll().size();

        // Update the poules
        Poules updatedPoules = poulesRepository.findById(poules.getId()).get();
        // Disconnect from session so that the updates on updatedPoules are not directly saved in db
        em.detach(updatedPoules);
        updatedPoules
            .poulesName(UPDATED_POULES_NAME);
        PoulesDTO poulesDTO = poulesMapper.toDto(updatedPoules);

        restPoulesMockMvc.perform(put("/api/poules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poulesDTO)))
            .andExpect(status().isOk());

        // Validate the Poules in the database
        List<Poules> poulesList = poulesRepository.findAll();
        assertThat(poulesList).hasSize(databaseSizeBeforeUpdate);
        Poules testPoules = poulesList.get(poulesList.size() - 1);
        assertThat(testPoules.getPoulesName()).isEqualTo(UPDATED_POULES_NAME);

        // Validate the Poules in Elasticsearch
        verify(mockPoulesSearchRepository, times(1)).save(testPoules);
    }

    @Test
    @Transactional
    public void updateNonExistingPoules() throws Exception {
        int databaseSizeBeforeUpdate = poulesRepository.findAll().size();

        // Create the Poules
        PoulesDTO poulesDTO = poulesMapper.toDto(poules);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoulesMockMvc.perform(put("/api/poules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poulesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Poules in the database
        List<Poules> poulesList = poulesRepository.findAll();
        assertThat(poulesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Poules in Elasticsearch
        verify(mockPoulesSearchRepository, times(0)).save(poules);
    }

    @Test
    @Transactional
    public void deletePoules() throws Exception {
        // Initialize the database
        poulesRepository.saveAndFlush(poules);

        int databaseSizeBeforeDelete = poulesRepository.findAll().size();

        // Delete the poules
        restPoulesMockMvc.perform(delete("/api/poules/{id}", poules.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Poules> poulesList = poulesRepository.findAll();
        assertThat(poulesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Poules in Elasticsearch
        verify(mockPoulesSearchRepository, times(1)).deleteById(poules.getId());
    }

    @Test
    @Transactional
    public void searchPoules() throws Exception {
        // Initialize the database
        poulesRepository.saveAndFlush(poules);
        when(mockPoulesSearchRepository.search(queryStringQuery("id:" + poules.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(poules), PageRequest.of(0, 1), 1));
        // Search the poules
        restPoulesMockMvc.perform(get("/api/_search/poules?query=id:" + poules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poules.getId().intValue())))
            .andExpect(jsonPath("$.[*].poulesName").value(hasItem(DEFAULT_POULES_NAME)));
    }
}
