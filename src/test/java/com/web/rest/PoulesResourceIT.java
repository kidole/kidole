package com.web.rest;

import com.KidoleApp;
import com.domain.Poules;
import com.repository.PoulesRepository;
import com.service.PoulesService;
import com.service.dto.PoulesDTO;
import com.service.mapper.PoulesMapper;
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
 * Integration tests for the {@link PoulesResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class PoulesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PoulesRepository poulesRepository;

    @Autowired
    private PoulesMapper poulesMapper;

    @Autowired
    private PoulesService poulesService;

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
            .name(DEFAULT_NAME);
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
            .name(UPDATED_NAME);
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
        assertThat(testPoules.getName()).isEqualTo(DEFAULT_NAME);
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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
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
            .name(UPDATED_NAME);
        PoulesDTO poulesDTO = poulesMapper.toDto(updatedPoules);

        restPoulesMockMvc.perform(put("/api/poules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poulesDTO)))
            .andExpect(status().isOk());

        // Validate the Poules in the database
        List<Poules> poulesList = poulesRepository.findAll();
        assertThat(poulesList).hasSize(databaseSizeBeforeUpdate);
        Poules testPoules = poulesList.get(poulesList.size() - 1);
        assertThat(testPoules.getName()).isEqualTo(UPDATED_NAME);
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
    }
}
