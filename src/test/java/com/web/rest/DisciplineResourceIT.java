package com.web.rest;

import com.KidoleApp;
import com.domain.Discipline;
import com.repository.DisciplineRepository;
import com.service.DisciplineService;
import com.service.dto.DisciplineDTO;
import com.service.mapper.DisciplineMapper;
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

import com.domain.enumeration.GenderSex;
/**
 * Integration tests for the {@link DisciplineResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class DisciplineResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final GenderSex DEFAULT_SEX_GENDER = GenderSex.MALE;
    private static final GenderSex UPDATED_SEX_GENDER = GenderSex.FEMALE;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private DisciplineMapper disciplineMapper;

    @Autowired
    private DisciplineService disciplineService;

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

    private MockMvc restDisciplineMockMvc;

    private Discipline discipline;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DisciplineResource disciplineResource = new DisciplineResource(disciplineService);
        this.restDisciplineMockMvc = MockMvcBuilders.standaloneSetup(disciplineResource)
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
    public static Discipline createEntity(EntityManager em) {
        Discipline discipline = new Discipline()
            .name(DEFAULT_NAME)
            .sexGender(DEFAULT_SEX_GENDER);
        return discipline;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discipline createUpdatedEntity(EntityManager em) {
        Discipline discipline = new Discipline()
            .name(UPDATED_NAME)
            .sexGender(UPDATED_SEX_GENDER);
        return discipline;
    }

    @BeforeEach
    public void initTest() {
        discipline = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscipline() throws Exception {
        int databaseSizeBeforeCreate = disciplineRepository.findAll().size();

        // Create the Discipline
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);
        restDisciplineMockMvc.perform(post("/api/disciplines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isCreated());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeCreate + 1);
        Discipline testDiscipline = disciplineList.get(disciplineList.size() - 1);
        assertThat(testDiscipline.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDiscipline.getSexGender()).isEqualTo(DEFAULT_SEX_GENDER);
    }

    @Test
    @Transactional
    public void createDisciplineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disciplineRepository.findAll().size();

        // Create the Discipline with an existing ID
        discipline.setId(1L);
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisciplineMockMvc.perform(post("/api/disciplines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDisciplines() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList
        restDisciplineMockMvc.perform(get("/api/disciplines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discipline.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sexGender").value(hasItem(DEFAULT_SEX_GENDER.toString())));
    }
    
    @Test
    @Transactional
    public void getDiscipline() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get the discipline
        restDisciplineMockMvc.perform(get("/api/disciplines/{id}", discipline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(discipline.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.sexGender").value(DEFAULT_SEX_GENDER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDiscipline() throws Exception {
        // Get the discipline
        restDisciplineMockMvc.perform(get("/api/disciplines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscipline() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();

        // Update the discipline
        Discipline updatedDiscipline = disciplineRepository.findById(discipline.getId()).get();
        // Disconnect from session so that the updates on updatedDiscipline are not directly saved in db
        em.detach(updatedDiscipline);
        updatedDiscipline
            .name(UPDATED_NAME)
            .sexGender(UPDATED_SEX_GENDER);
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(updatedDiscipline);

        restDisciplineMockMvc.perform(put("/api/disciplines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isOk());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);
        Discipline testDiscipline = disciplineList.get(disciplineList.size() - 1);
        assertThat(testDiscipline.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDiscipline.getSexGender()).isEqualTo(UPDATED_SEX_GENDER);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscipline() throws Exception {
        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();

        // Create the Discipline
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisciplineMockMvc.perform(put("/api/disciplines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiscipline() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        int databaseSizeBeforeDelete = disciplineRepository.findAll().size();

        // Delete the discipline
        restDisciplineMockMvc.perform(delete("/api/disciplines/{id}", discipline.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
