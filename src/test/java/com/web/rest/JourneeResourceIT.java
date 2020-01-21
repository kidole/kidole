package com.web.rest;

import com.KidoleApp;
import com.domain.Journee;
import com.repository.JourneeRepository;
import com.service.JourneeService;
import com.service.dto.JourneeDTO;
import com.service.mapper.JourneeMapper;
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
 * Integration tests for the {@link JourneeResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class JourneeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private JourneeRepository journeeRepository;

    @Autowired
    private JourneeMapper journeeMapper;

    @Autowired
    private JourneeService journeeService;

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

    private MockMvc restJourneeMockMvc;

    private Journee journee;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JourneeResource journeeResource = new JourneeResource(journeeService);
        this.restJourneeMockMvc = MockMvcBuilders.standaloneSetup(journeeResource)
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
    public static Journee createEntity(EntityManager em) {
        Journee journee = new Journee()
            .name(DEFAULT_NAME);
        return journee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Journee createUpdatedEntity(EntityManager em) {
        Journee journee = new Journee()
            .name(UPDATED_NAME);
        return journee;
    }

    @BeforeEach
    public void initTest() {
        journee = createEntity(em);
    }

    @Test
    @Transactional
    public void createJournee() throws Exception {
        int databaseSizeBeforeCreate = journeeRepository.findAll().size();

        // Create the Journee
        JourneeDTO journeeDTO = journeeMapper.toDto(journee);
        restJourneeMockMvc.perform(post("/api/journees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Journee in the database
        List<Journee> journeeList = journeeRepository.findAll();
        assertThat(journeeList).hasSize(databaseSizeBeforeCreate + 1);
        Journee testJournee = journeeList.get(journeeList.size() - 1);
        assertThat(testJournee.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createJourneeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = journeeRepository.findAll().size();

        // Create the Journee with an existing ID
        journee.setId(1L);
        JourneeDTO journeeDTO = journeeMapper.toDto(journee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJourneeMockMvc.perform(post("/api/journees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Journee in the database
        List<Journee> journeeList = journeeRepository.findAll();
        assertThat(journeeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJournees() throws Exception {
        // Initialize the database
        journeeRepository.saveAndFlush(journee);

        // Get all the journeeList
        restJourneeMockMvc.perform(get("/api/journees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journee.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getJournee() throws Exception {
        // Initialize the database
        journeeRepository.saveAndFlush(journee);

        // Get the journee
        restJourneeMockMvc.perform(get("/api/journees/{id}", journee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(journee.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingJournee() throws Exception {
        // Get the journee
        restJourneeMockMvc.perform(get("/api/journees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJournee() throws Exception {
        // Initialize the database
        journeeRepository.saveAndFlush(journee);

        int databaseSizeBeforeUpdate = journeeRepository.findAll().size();

        // Update the journee
        Journee updatedJournee = journeeRepository.findById(journee.getId()).get();
        // Disconnect from session so that the updates on updatedJournee are not directly saved in db
        em.detach(updatedJournee);
        updatedJournee
            .name(UPDATED_NAME);
        JourneeDTO journeeDTO = journeeMapper.toDto(updatedJournee);

        restJourneeMockMvc.perform(put("/api/journees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journeeDTO)))
            .andExpect(status().isOk());

        // Validate the Journee in the database
        List<Journee> journeeList = journeeRepository.findAll();
        assertThat(journeeList).hasSize(databaseSizeBeforeUpdate);
        Journee testJournee = journeeList.get(journeeList.size() - 1);
        assertThat(testJournee.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingJournee() throws Exception {
        int databaseSizeBeforeUpdate = journeeRepository.findAll().size();

        // Create the Journee
        JourneeDTO journeeDTO = journeeMapper.toDto(journee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourneeMockMvc.perform(put("/api/journees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Journee in the database
        List<Journee> journeeList = journeeRepository.findAll();
        assertThat(journeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJournee() throws Exception {
        // Initialize the database
        journeeRepository.saveAndFlush(journee);

        int databaseSizeBeforeDelete = journeeRepository.findAll().size();

        // Delete the journee
        restJourneeMockMvc.perform(delete("/api/journees/{id}", journee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Journee> journeeList = journeeRepository.findAll();
        assertThat(journeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
