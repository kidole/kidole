package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.Journee;
import com.kidole.sport.repository.JourneeRepository;
import com.kidole.sport.repository.search.JourneeSearchRepository;
import com.kidole.sport.service.JourneeService;
import com.kidole.sport.service.dto.JourneeDTO;
import com.kidole.sport.service.mapper.JourneeMapper;
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
 * Integration tests for the {@link JourneeResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class JourneeResourceIT {

    private static final String DEFAULT_JOURNEE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_JOURNEE_NAME = "BBBBBBBBBB";

    @Autowired
    private JourneeRepository journeeRepository;

    @Autowired
    private JourneeMapper journeeMapper;

    @Autowired
    private JourneeService journeeService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.JourneeSearchRepositoryMockConfiguration
     */
    @Autowired
    private JourneeSearchRepository mockJourneeSearchRepository;

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
            .journeeName(DEFAULT_JOURNEE_NAME);
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
            .journeeName(UPDATED_JOURNEE_NAME);
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
        assertThat(testJournee.getJourneeName()).isEqualTo(DEFAULT_JOURNEE_NAME);

        // Validate the Journee in Elasticsearch
        verify(mockJourneeSearchRepository, times(1)).save(testJournee);
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

        // Validate the Journee in Elasticsearch
        verify(mockJourneeSearchRepository, times(0)).save(journee);
    }


    @Test
    @Transactional
    public void checkJourneeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = journeeRepository.findAll().size();
        // set the field null
        journee.setJourneeName(null);

        // Create the Journee, which fails.
        JourneeDTO journeeDTO = journeeMapper.toDto(journee);

        restJourneeMockMvc.perform(post("/api/journees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journeeDTO)))
            .andExpect(status().isBadRequest());

        List<Journee> journeeList = journeeRepository.findAll();
        assertThat(journeeList).hasSize(databaseSizeBeforeTest);
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
            .andExpect(jsonPath("$.[*].journeeName").value(hasItem(DEFAULT_JOURNEE_NAME)));
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
            .andExpect(jsonPath("$.journeeName").value(DEFAULT_JOURNEE_NAME));
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
            .journeeName(UPDATED_JOURNEE_NAME);
        JourneeDTO journeeDTO = journeeMapper.toDto(updatedJournee);

        restJourneeMockMvc.perform(put("/api/journees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journeeDTO)))
            .andExpect(status().isOk());

        // Validate the Journee in the database
        List<Journee> journeeList = journeeRepository.findAll();
        assertThat(journeeList).hasSize(databaseSizeBeforeUpdate);
        Journee testJournee = journeeList.get(journeeList.size() - 1);
        assertThat(testJournee.getJourneeName()).isEqualTo(UPDATED_JOURNEE_NAME);

        // Validate the Journee in Elasticsearch
        verify(mockJourneeSearchRepository, times(1)).save(testJournee);
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

        // Validate the Journee in Elasticsearch
        verify(mockJourneeSearchRepository, times(0)).save(journee);
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

        // Validate the Journee in Elasticsearch
        verify(mockJourneeSearchRepository, times(1)).deleteById(journee.getId());
    }

    @Test
    @Transactional
    public void searchJournee() throws Exception {
        // Initialize the database
        journeeRepository.saveAndFlush(journee);
        when(mockJourneeSearchRepository.search(queryStringQuery("id:" + journee.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(journee), PageRequest.of(0, 1), 1));
        // Search the journee
        restJourneeMockMvc.perform(get("/api/_search/journees?query=id:" + journee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journee.getId().intValue())))
            .andExpect(jsonPath("$.[*].journeeName").value(hasItem(DEFAULT_JOURNEE_NAME)));
    }
}
