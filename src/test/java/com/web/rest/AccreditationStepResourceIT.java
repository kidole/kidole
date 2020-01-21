package com.web.rest;

import com.KidoleApp;
import com.domain.AccreditationStep;
import com.repository.AccreditationStepRepository;
import com.service.AccreditationStepService;
import com.service.dto.AccreditationStepDTO;
import com.service.mapper.AccreditationStepMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.domain.enumeration.AccreditationList;
/**
 * Integration tests for the {@link AccreditationStepResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class AccreditationStepResourceIT {

    private static final Instant DEFAULT_DEBUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEBUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final AccreditationList DEFAULT_TYPE = AccreditationList.ATHLETE;
    private static final AccreditationList UPDATED_TYPE = AccreditationList.SPARING;

    @Autowired
    private AccreditationStepRepository accreditationStepRepository;

    @Autowired
    private AccreditationStepMapper accreditationStepMapper;

    @Autowired
    private AccreditationStepService accreditationStepService;

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

    private MockMvc restAccreditationStepMockMvc;

    private AccreditationStep accreditationStep;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccreditationStepResource accreditationStepResource = new AccreditationStepResource(accreditationStepService);
        this.restAccreditationStepMockMvc = MockMvcBuilders.standaloneSetup(accreditationStepResource)
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
    public static AccreditationStep createEntity(EntityManager em) {
        AccreditationStep accreditationStep = new AccreditationStep()
            .debut(DEFAULT_DEBUT)
            .fin(DEFAULT_FIN)
            .numero(DEFAULT_NUMERO)
            .type(DEFAULT_TYPE);
        return accreditationStep;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccreditationStep createUpdatedEntity(EntityManager em) {
        AccreditationStep accreditationStep = new AccreditationStep()
            .debut(UPDATED_DEBUT)
            .fin(UPDATED_FIN)
            .numero(UPDATED_NUMERO)
            .type(UPDATED_TYPE);
        return accreditationStep;
    }

    @BeforeEach
    public void initTest() {
        accreditationStep = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccreditationStep() throws Exception {
        int databaseSizeBeforeCreate = accreditationStepRepository.findAll().size();

        // Create the AccreditationStep
        AccreditationStepDTO accreditationStepDTO = accreditationStepMapper.toDto(accreditationStep);
        restAccreditationStepMockMvc.perform(post("/api/accreditation-steps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationStepDTO)))
            .andExpect(status().isCreated());

        // Validate the AccreditationStep in the database
        List<AccreditationStep> accreditationStepList = accreditationStepRepository.findAll();
        assertThat(accreditationStepList).hasSize(databaseSizeBeforeCreate + 1);
        AccreditationStep testAccreditationStep = accreditationStepList.get(accreditationStepList.size() - 1);
        assertThat(testAccreditationStep.getDebut()).isEqualTo(DEFAULT_DEBUT);
        assertThat(testAccreditationStep.getFin()).isEqualTo(DEFAULT_FIN);
        assertThat(testAccreditationStep.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAccreditationStep.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createAccreditationStepWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accreditationStepRepository.findAll().size();

        // Create the AccreditationStep with an existing ID
        accreditationStep.setId(1L);
        AccreditationStepDTO accreditationStepDTO = accreditationStepMapper.toDto(accreditationStep);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccreditationStepMockMvc.perform(post("/api/accreditation-steps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationStepDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccreditationStep in the database
        List<AccreditationStep> accreditationStepList = accreditationStepRepository.findAll();
        assertThat(accreditationStepList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAccreditationSteps() throws Exception {
        // Initialize the database
        accreditationStepRepository.saveAndFlush(accreditationStep);

        // Get all the accreditationStepList
        restAccreditationStepMockMvc.perform(get("/api/accreditation-steps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accreditationStep.getId().intValue())))
            .andExpect(jsonPath("$.[*].debut").value(hasItem(DEFAULT_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getAccreditationStep() throws Exception {
        // Initialize the database
        accreditationStepRepository.saveAndFlush(accreditationStep);

        // Get the accreditationStep
        restAccreditationStepMockMvc.perform(get("/api/accreditation-steps/{id}", accreditationStep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accreditationStep.getId().intValue()))
            .andExpect(jsonPath("$.debut").value(DEFAULT_DEBUT.toString()))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAccreditationStep() throws Exception {
        // Get the accreditationStep
        restAccreditationStepMockMvc.perform(get("/api/accreditation-steps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccreditationStep() throws Exception {
        // Initialize the database
        accreditationStepRepository.saveAndFlush(accreditationStep);

        int databaseSizeBeforeUpdate = accreditationStepRepository.findAll().size();

        // Update the accreditationStep
        AccreditationStep updatedAccreditationStep = accreditationStepRepository.findById(accreditationStep.getId()).get();
        // Disconnect from session so that the updates on updatedAccreditationStep are not directly saved in db
        em.detach(updatedAccreditationStep);
        updatedAccreditationStep
            .debut(UPDATED_DEBUT)
            .fin(UPDATED_FIN)
            .numero(UPDATED_NUMERO)
            .type(UPDATED_TYPE);
        AccreditationStepDTO accreditationStepDTO = accreditationStepMapper.toDto(updatedAccreditationStep);

        restAccreditationStepMockMvc.perform(put("/api/accreditation-steps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationStepDTO)))
            .andExpect(status().isOk());

        // Validate the AccreditationStep in the database
        List<AccreditationStep> accreditationStepList = accreditationStepRepository.findAll();
        assertThat(accreditationStepList).hasSize(databaseSizeBeforeUpdate);
        AccreditationStep testAccreditationStep = accreditationStepList.get(accreditationStepList.size() - 1);
        assertThat(testAccreditationStep.getDebut()).isEqualTo(UPDATED_DEBUT);
        assertThat(testAccreditationStep.getFin()).isEqualTo(UPDATED_FIN);
        assertThat(testAccreditationStep.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAccreditationStep.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAccreditationStep() throws Exception {
        int databaseSizeBeforeUpdate = accreditationStepRepository.findAll().size();

        // Create the AccreditationStep
        AccreditationStepDTO accreditationStepDTO = accreditationStepMapper.toDto(accreditationStep);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccreditationStepMockMvc.perform(put("/api/accreditation-steps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationStepDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccreditationStep in the database
        List<AccreditationStep> accreditationStepList = accreditationStepRepository.findAll();
        assertThat(accreditationStepList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccreditationStep() throws Exception {
        // Initialize the database
        accreditationStepRepository.saveAndFlush(accreditationStep);

        int databaseSizeBeforeDelete = accreditationStepRepository.findAll().size();

        // Delete the accreditationStep
        restAccreditationStepMockMvc.perform(delete("/api/accreditation-steps/{id}", accreditationStep.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccreditationStep> accreditationStepList = accreditationStepRepository.findAll();
        assertThat(accreditationStepList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
