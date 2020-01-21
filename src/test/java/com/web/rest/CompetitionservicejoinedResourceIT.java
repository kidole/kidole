package com.web.rest;

import com.KidoleApp;
import com.domain.Competitionservicejoined;
import com.repository.CompetitionservicejoinedRepository;
import com.service.CompetitionservicejoinedService;
import com.service.dto.CompetitionservicejoinedDTO;
import com.service.mapper.CompetitionservicejoinedMapper;
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

import com.domain.enumeration.ServicesState;
/**
 * Integration tests for the {@link CompetitionservicejoinedResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class CompetitionservicejoinedResourceIT {

    private static final ServicesState DEFAULT_STATE = ServicesState.ACCEPT;
    private static final ServicesState UPDATED_STATE = ServicesState.VIEW;

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    @Autowired
    private CompetitionservicejoinedRepository competitionservicejoinedRepository;

    @Autowired
    private CompetitionservicejoinedMapper competitionservicejoinedMapper;

    @Autowired
    private CompetitionservicejoinedService competitionservicejoinedService;

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

    private MockMvc restCompetitionservicejoinedMockMvc;

    private Competitionservicejoined competitionservicejoined;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompetitionservicejoinedResource competitionservicejoinedResource = new CompetitionservicejoinedResource(competitionservicejoinedService);
        this.restCompetitionservicejoinedMockMvc = MockMvcBuilders.standaloneSetup(competitionservicejoinedResource)
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
    public static Competitionservicejoined createEntity(EntityManager em) {
        Competitionservicejoined competitionservicejoined = new Competitionservicejoined()
            .state(DEFAULT_STATE)
            .details(DEFAULT_DETAILS);
        return competitionservicejoined;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competitionservicejoined createUpdatedEntity(EntityManager em) {
        Competitionservicejoined competitionservicejoined = new Competitionservicejoined()
            .state(UPDATED_STATE)
            .details(UPDATED_DETAILS);
        return competitionservicejoined;
    }

    @BeforeEach
    public void initTest() {
        competitionservicejoined = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitionservicejoined() throws Exception {
        int databaseSizeBeforeCreate = competitionservicejoinedRepository.findAll().size();

        // Create the Competitionservicejoined
        CompetitionservicejoinedDTO competitionservicejoinedDTO = competitionservicejoinedMapper.toDto(competitionservicejoined);
        restCompetitionservicejoinedMockMvc.perform(post("/api/competitionservicejoineds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionservicejoinedDTO)))
            .andExpect(status().isCreated());

        // Validate the Competitionservicejoined in the database
        List<Competitionservicejoined> competitionservicejoinedList = competitionservicejoinedRepository.findAll();
        assertThat(competitionservicejoinedList).hasSize(databaseSizeBeforeCreate + 1);
        Competitionservicejoined testCompetitionservicejoined = competitionservicejoinedList.get(competitionservicejoinedList.size() - 1);
        assertThat(testCompetitionservicejoined.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCompetitionservicejoined.getDetails()).isEqualTo(DEFAULT_DETAILS);
    }

    @Test
    @Transactional
    public void createCompetitionservicejoinedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitionservicejoinedRepository.findAll().size();

        // Create the Competitionservicejoined with an existing ID
        competitionservicejoined.setId(1L);
        CompetitionservicejoinedDTO competitionservicejoinedDTO = competitionservicejoinedMapper.toDto(competitionservicejoined);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitionservicejoinedMockMvc.perform(post("/api/competitionservicejoineds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionservicejoinedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Competitionservicejoined in the database
        List<Competitionservicejoined> competitionservicejoinedList = competitionservicejoinedRepository.findAll();
        assertThat(competitionservicejoinedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompetitionservicejoineds() throws Exception {
        // Initialize the database
        competitionservicejoinedRepository.saveAndFlush(competitionservicejoined);

        // Get all the competitionservicejoinedList
        restCompetitionservicejoinedMockMvc.perform(get("/api/competitionservicejoineds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitionservicejoined.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)));
    }
    
    @Test
    @Transactional
    public void getCompetitionservicejoined() throws Exception {
        // Initialize the database
        competitionservicejoinedRepository.saveAndFlush(competitionservicejoined);

        // Get the competitionservicejoined
        restCompetitionservicejoinedMockMvc.perform(get("/api/competitionservicejoineds/{id}", competitionservicejoined.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competitionservicejoined.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS));
    }

    @Test
    @Transactional
    public void getNonExistingCompetitionservicejoined() throws Exception {
        // Get the competitionservicejoined
        restCompetitionservicejoinedMockMvc.perform(get("/api/competitionservicejoineds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitionservicejoined() throws Exception {
        // Initialize the database
        competitionservicejoinedRepository.saveAndFlush(competitionservicejoined);

        int databaseSizeBeforeUpdate = competitionservicejoinedRepository.findAll().size();

        // Update the competitionservicejoined
        Competitionservicejoined updatedCompetitionservicejoined = competitionservicejoinedRepository.findById(competitionservicejoined.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitionservicejoined are not directly saved in db
        em.detach(updatedCompetitionservicejoined);
        updatedCompetitionservicejoined
            .state(UPDATED_STATE)
            .details(UPDATED_DETAILS);
        CompetitionservicejoinedDTO competitionservicejoinedDTO = competitionservicejoinedMapper.toDto(updatedCompetitionservicejoined);

        restCompetitionservicejoinedMockMvc.perform(put("/api/competitionservicejoineds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionservicejoinedDTO)))
            .andExpect(status().isOk());

        // Validate the Competitionservicejoined in the database
        List<Competitionservicejoined> competitionservicejoinedList = competitionservicejoinedRepository.findAll();
        assertThat(competitionservicejoinedList).hasSize(databaseSizeBeforeUpdate);
        Competitionservicejoined testCompetitionservicejoined = competitionservicejoinedList.get(competitionservicejoinedList.size() - 1);
        assertThat(testCompetitionservicejoined.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCompetitionservicejoined.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetitionservicejoined() throws Exception {
        int databaseSizeBeforeUpdate = competitionservicejoinedRepository.findAll().size();

        // Create the Competitionservicejoined
        CompetitionservicejoinedDTO competitionservicejoinedDTO = competitionservicejoinedMapper.toDto(competitionservicejoined);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitionservicejoinedMockMvc.perform(put("/api/competitionservicejoineds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionservicejoinedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Competitionservicejoined in the database
        List<Competitionservicejoined> competitionservicejoinedList = competitionservicejoinedRepository.findAll();
        assertThat(competitionservicejoinedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompetitionservicejoined() throws Exception {
        // Initialize the database
        competitionservicejoinedRepository.saveAndFlush(competitionservicejoined);

        int databaseSizeBeforeDelete = competitionservicejoinedRepository.findAll().size();

        // Delete the competitionservicejoined
        restCompetitionservicejoinedMockMvc.perform(delete("/api/competitionservicejoineds/{id}", competitionservicejoined.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Competitionservicejoined> competitionservicejoinedList = competitionservicejoinedRepository.findAll();
        assertThat(competitionservicejoinedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
