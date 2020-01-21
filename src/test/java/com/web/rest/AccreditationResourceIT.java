package com.web.rest;

import com.KidoleApp;
import com.domain.Accreditation;
import com.repository.AccreditationRepository;
import com.service.AccreditationService;
import com.service.dto.AccreditationDTO;
import com.service.mapper.AccreditationMapper;
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

import com.domain.enumeration.AccreditationList;
import com.domain.enumeration.AccreditationState;
/**
 * Integration tests for the {@link AccreditationResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class AccreditationResourceIT {

    private static final AccreditationList DEFAULT_NAME = AccreditationList.ATHLETE;
    private static final AccreditationList UPDATED_NAME = AccreditationList.SPARING;

    private static final AccreditationState DEFAULT_STATUS = AccreditationState.ACTIVE;
    private static final AccreditationState UPDATED_STATUS = AccreditationState.WAITTING;

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    @Autowired
    private AccreditationRepository accreditationRepository;

    @Autowired
    private AccreditationMapper accreditationMapper;

    @Autowired
    private AccreditationService accreditationService;

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

    private MockMvc restAccreditationMockMvc;

    private Accreditation accreditation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccreditationResource accreditationResource = new AccreditationResource(accreditationService);
        this.restAccreditationMockMvc = MockMvcBuilders.standaloneSetup(accreditationResource)
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
    public static Accreditation createEntity(EntityManager em) {
        Accreditation accreditation = new Accreditation()
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS)
            .details(DEFAULT_DETAILS);
        return accreditation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accreditation createUpdatedEntity(EntityManager em) {
        Accreditation accreditation = new Accreditation()
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .details(UPDATED_DETAILS);
        return accreditation;
    }

    @BeforeEach
    public void initTest() {
        accreditation = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccreditation() throws Exception {
        int databaseSizeBeforeCreate = accreditationRepository.findAll().size();

        // Create the Accreditation
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);
        restAccreditationMockMvc.perform(post("/api/accreditations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationDTO)))
            .andExpect(status().isCreated());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeCreate + 1);
        Accreditation testAccreditation = accreditationList.get(accreditationList.size() - 1);
        assertThat(testAccreditation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAccreditation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAccreditation.getDetails()).isEqualTo(DEFAULT_DETAILS);
    }

    @Test
    @Transactional
    public void createAccreditationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accreditationRepository.findAll().size();

        // Create the Accreditation with an existing ID
        accreditation.setId(1L);
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccreditationMockMvc.perform(post("/api/accreditations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAccreditations() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get all the accreditationList
        restAccreditationMockMvc.perform(get("/api/accreditations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accreditation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)));
    }
    
    @Test
    @Transactional
    public void getAccreditation() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        // Get the accreditation
        restAccreditationMockMvc.perform(get("/api/accreditations/{id}", accreditation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accreditation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS));
    }

    @Test
    @Transactional
    public void getNonExistingAccreditation() throws Exception {
        // Get the accreditation
        restAccreditationMockMvc.perform(get("/api/accreditations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccreditation() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        int databaseSizeBeforeUpdate = accreditationRepository.findAll().size();

        // Update the accreditation
        Accreditation updatedAccreditation = accreditationRepository.findById(accreditation.getId()).get();
        // Disconnect from session so that the updates on updatedAccreditation are not directly saved in db
        em.detach(updatedAccreditation);
        updatedAccreditation
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .details(UPDATED_DETAILS);
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(updatedAccreditation);

        restAccreditationMockMvc.perform(put("/api/accreditations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationDTO)))
            .andExpect(status().isOk());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeUpdate);
        Accreditation testAccreditation = accreditationList.get(accreditationList.size() - 1);
        assertThat(testAccreditation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAccreditation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAccreditation.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void updateNonExistingAccreditation() throws Exception {
        int databaseSizeBeforeUpdate = accreditationRepository.findAll().size();

        // Create the Accreditation
        AccreditationDTO accreditationDTO = accreditationMapper.toDto(accreditation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccreditationMockMvc.perform(put("/api/accreditations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accreditationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Accreditation in the database
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccreditation() throws Exception {
        // Initialize the database
        accreditationRepository.saveAndFlush(accreditation);

        int databaseSizeBeforeDelete = accreditationRepository.findAll().size();

        // Delete the accreditation
        restAccreditationMockMvc.perform(delete("/api/accreditations/{id}", accreditation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Accreditation> accreditationList = accreditationRepository.findAll();
        assertThat(accreditationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
