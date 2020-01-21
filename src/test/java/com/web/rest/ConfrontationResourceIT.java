package com.web.rest;

import com.KidoleApp;
import com.domain.Confrontation;
import com.repository.ConfrontationRepository;
import com.service.ConfrontationService;
import com.service.dto.ConfrontationDTO;
import com.service.mapper.ConfrontationMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ConfrontationResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class ConfrontationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DEBUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEBUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    @Autowired
    private ConfrontationRepository confrontationRepository;

    @Autowired
    private ConfrontationMapper confrontationMapper;

    @Autowired
    private ConfrontationService confrontationService;

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

    private MockMvc restConfrontationMockMvc;

    private Confrontation confrontation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConfrontationResource confrontationResource = new ConfrontationResource(confrontationService);
        this.restConfrontationMockMvc = MockMvcBuilders.standaloneSetup(confrontationResource)
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
    public static Confrontation createEntity(EntityManager em) {
        Confrontation confrontation = new Confrontation()
            .name(DEFAULT_NAME)
            .debut(DEFAULT_DEBUT)
            .fin(DEFAULT_FIN)
            .details(DEFAULT_DETAILS);
        return confrontation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Confrontation createUpdatedEntity(EntityManager em) {
        Confrontation confrontation = new Confrontation()
            .name(UPDATED_NAME)
            .debut(UPDATED_DEBUT)
            .fin(UPDATED_FIN)
            .details(UPDATED_DETAILS);
        return confrontation;
    }

    @BeforeEach
    public void initTest() {
        confrontation = createEntity(em);
    }

    @Test
    @Transactional
    public void createConfrontation() throws Exception {
        int databaseSizeBeforeCreate = confrontationRepository.findAll().size();

        // Create the Confrontation
        ConfrontationDTO confrontationDTO = confrontationMapper.toDto(confrontation);
        restConfrontationMockMvc.perform(post("/api/confrontations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confrontationDTO)))
            .andExpect(status().isCreated());

        // Validate the Confrontation in the database
        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeCreate + 1);
        Confrontation testConfrontation = confrontationList.get(confrontationList.size() - 1);
        assertThat(testConfrontation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConfrontation.getDebut()).isEqualTo(DEFAULT_DEBUT);
        assertThat(testConfrontation.getFin()).isEqualTo(DEFAULT_FIN);
        assertThat(testConfrontation.getDetails()).isEqualTo(DEFAULT_DETAILS);
    }

    @Test
    @Transactional
    public void createConfrontationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = confrontationRepository.findAll().size();

        // Create the Confrontation with an existing ID
        confrontation.setId(1L);
        ConfrontationDTO confrontationDTO = confrontationMapper.toDto(confrontation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfrontationMockMvc.perform(post("/api/confrontations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confrontationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Confrontation in the database
        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConfrontations() throws Exception {
        // Initialize the database
        confrontationRepository.saveAndFlush(confrontation);

        // Get all the confrontationList
        restConfrontationMockMvc.perform(get("/api/confrontations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(confrontation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].debut").value(hasItem(DEFAULT_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())));
    }
    
    @Test
    @Transactional
    public void getConfrontation() throws Exception {
        // Initialize the database
        confrontationRepository.saveAndFlush(confrontation);

        // Get the confrontation
        restConfrontationMockMvc.perform(get("/api/confrontations/{id}", confrontation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(confrontation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.debut").value(DEFAULT_DEBUT.toString()))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConfrontation() throws Exception {
        // Get the confrontation
        restConfrontationMockMvc.perform(get("/api/confrontations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConfrontation() throws Exception {
        // Initialize the database
        confrontationRepository.saveAndFlush(confrontation);

        int databaseSizeBeforeUpdate = confrontationRepository.findAll().size();

        // Update the confrontation
        Confrontation updatedConfrontation = confrontationRepository.findById(confrontation.getId()).get();
        // Disconnect from session so that the updates on updatedConfrontation are not directly saved in db
        em.detach(updatedConfrontation);
        updatedConfrontation
            .name(UPDATED_NAME)
            .debut(UPDATED_DEBUT)
            .fin(UPDATED_FIN)
            .details(UPDATED_DETAILS);
        ConfrontationDTO confrontationDTO = confrontationMapper.toDto(updatedConfrontation);

        restConfrontationMockMvc.perform(put("/api/confrontations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confrontationDTO)))
            .andExpect(status().isOk());

        // Validate the Confrontation in the database
        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeUpdate);
        Confrontation testConfrontation = confrontationList.get(confrontationList.size() - 1);
        assertThat(testConfrontation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConfrontation.getDebut()).isEqualTo(UPDATED_DEBUT);
        assertThat(testConfrontation.getFin()).isEqualTo(UPDATED_FIN);
        assertThat(testConfrontation.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void updateNonExistingConfrontation() throws Exception {
        int databaseSizeBeforeUpdate = confrontationRepository.findAll().size();

        // Create the Confrontation
        ConfrontationDTO confrontationDTO = confrontationMapper.toDto(confrontation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfrontationMockMvc.perform(put("/api/confrontations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(confrontationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Confrontation in the database
        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConfrontation() throws Exception {
        // Initialize the database
        confrontationRepository.saveAndFlush(confrontation);

        int databaseSizeBeforeDelete = confrontationRepository.findAll().size();

        // Delete the confrontation
        restConfrontationMockMvc.perform(delete("/api/confrontations/{id}", confrontation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Confrontation> confrontationList = confrontationRepository.findAll();
        assertThat(confrontationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
