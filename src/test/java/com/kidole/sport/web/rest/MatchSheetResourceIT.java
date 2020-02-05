package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.MatchSheet;
import com.kidole.sport.repository.MatchSheetRepository;
import com.kidole.sport.repository.search.MatchSheetSearchRepository;
import com.kidole.sport.service.MatchSheetService;
import com.kidole.sport.service.dto.MatchSheetDTO;
import com.kidole.sport.service.mapper.MatchSheetMapper;
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
 * Integration tests for the {@link MatchSheetResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class MatchSheetResourceIT {

    private static final String DEFAULT_MATCH_SHEET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MATCH_SHEET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MATCH_SHEET_RESUME = "AAAAAAAAAA";
    private static final String UPDATED_MATCH_SHEET_RESUME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISFIRST = false;
    private static final Boolean UPDATED_ISFIRST = true;

    @Autowired
    private MatchSheetRepository matchSheetRepository;

    @Autowired
    private MatchSheetMapper matchSheetMapper;

    @Autowired
    private MatchSheetService matchSheetService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.MatchSheetSearchRepositoryMockConfiguration
     */
    @Autowired
    private MatchSheetSearchRepository mockMatchSheetSearchRepository;

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

    private MockMvc restMatchSheetMockMvc;

    private MatchSheet matchSheet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatchSheetResource matchSheetResource = new MatchSheetResource(matchSheetService);
        this.restMatchSheetMockMvc = MockMvcBuilders.standaloneSetup(matchSheetResource)
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
    public static MatchSheet createEntity(EntityManager em) {
        MatchSheet matchSheet = new MatchSheet()
            .matchSheetName(DEFAULT_MATCH_SHEET_NAME)
            .matchSheetResume(DEFAULT_MATCH_SHEET_RESUME)
            .isfirst(DEFAULT_ISFIRST);
        return matchSheet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchSheet createUpdatedEntity(EntityManager em) {
        MatchSheet matchSheet = new MatchSheet()
            .matchSheetName(UPDATED_MATCH_SHEET_NAME)
            .matchSheetResume(UPDATED_MATCH_SHEET_RESUME)
            .isfirst(UPDATED_ISFIRST);
        return matchSheet;
    }

    @BeforeEach
    public void initTest() {
        matchSheet = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatchSheet() throws Exception {
        int databaseSizeBeforeCreate = matchSheetRepository.findAll().size();

        // Create the MatchSheet
        MatchSheetDTO matchSheetDTO = matchSheetMapper.toDto(matchSheet);
        restMatchSheetMockMvc.perform(post("/api/match-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchSheetDTO)))
            .andExpect(status().isCreated());

        // Validate the MatchSheet in the database
        List<MatchSheet> matchSheetList = matchSheetRepository.findAll();
        assertThat(matchSheetList).hasSize(databaseSizeBeforeCreate + 1);
        MatchSheet testMatchSheet = matchSheetList.get(matchSheetList.size() - 1);
        assertThat(testMatchSheet.getMatchSheetName()).isEqualTo(DEFAULT_MATCH_SHEET_NAME);
        assertThat(testMatchSheet.getMatchSheetResume()).isEqualTo(DEFAULT_MATCH_SHEET_RESUME);
        assertThat(testMatchSheet.isIsfirst()).isEqualTo(DEFAULT_ISFIRST);

        // Validate the MatchSheet in Elasticsearch
        verify(mockMatchSheetSearchRepository, times(1)).save(testMatchSheet);
    }

    @Test
    @Transactional
    public void createMatchSheetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchSheetRepository.findAll().size();

        // Create the MatchSheet with an existing ID
        matchSheet.setId(1L);
        MatchSheetDTO matchSheetDTO = matchSheetMapper.toDto(matchSheet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchSheetMockMvc.perform(post("/api/match-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchSheetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MatchSheet in the database
        List<MatchSheet> matchSheetList = matchSheetRepository.findAll();
        assertThat(matchSheetList).hasSize(databaseSizeBeforeCreate);

        // Validate the MatchSheet in Elasticsearch
        verify(mockMatchSheetSearchRepository, times(0)).save(matchSheet);
    }


    @Test
    @Transactional
    public void checkMatchSheetNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = matchSheetRepository.findAll().size();
        // set the field null
        matchSheet.setMatchSheetName(null);

        // Create the MatchSheet, which fails.
        MatchSheetDTO matchSheetDTO = matchSheetMapper.toDto(matchSheet);

        restMatchSheetMockMvc.perform(post("/api/match-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchSheetDTO)))
            .andExpect(status().isBadRequest());

        List<MatchSheet> matchSheetList = matchSheetRepository.findAll();
        assertThat(matchSheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMatchSheets() throws Exception {
        // Initialize the database
        matchSheetRepository.saveAndFlush(matchSheet);

        // Get all the matchSheetList
        restMatchSheetMockMvc.perform(get("/api/match-sheets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchSheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].matchSheetName").value(hasItem(DEFAULT_MATCH_SHEET_NAME)))
            .andExpect(jsonPath("$.[*].matchSheetResume").value(hasItem(DEFAULT_MATCH_SHEET_RESUME.toString())))
            .andExpect(jsonPath("$.[*].isfirst").value(hasItem(DEFAULT_ISFIRST.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMatchSheet() throws Exception {
        // Initialize the database
        matchSheetRepository.saveAndFlush(matchSheet);

        // Get the matchSheet
        restMatchSheetMockMvc.perform(get("/api/match-sheets/{id}", matchSheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matchSheet.getId().intValue()))
            .andExpect(jsonPath("$.matchSheetName").value(DEFAULT_MATCH_SHEET_NAME))
            .andExpect(jsonPath("$.matchSheetResume").value(DEFAULT_MATCH_SHEET_RESUME.toString()))
            .andExpect(jsonPath("$.isfirst").value(DEFAULT_ISFIRST.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMatchSheet() throws Exception {
        // Get the matchSheet
        restMatchSheetMockMvc.perform(get("/api/match-sheets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatchSheet() throws Exception {
        // Initialize the database
        matchSheetRepository.saveAndFlush(matchSheet);

        int databaseSizeBeforeUpdate = matchSheetRepository.findAll().size();

        // Update the matchSheet
        MatchSheet updatedMatchSheet = matchSheetRepository.findById(matchSheet.getId()).get();
        // Disconnect from session so that the updates on updatedMatchSheet are not directly saved in db
        em.detach(updatedMatchSheet);
        updatedMatchSheet
            .matchSheetName(UPDATED_MATCH_SHEET_NAME)
            .matchSheetResume(UPDATED_MATCH_SHEET_RESUME)
            .isfirst(UPDATED_ISFIRST);
        MatchSheetDTO matchSheetDTO = matchSheetMapper.toDto(updatedMatchSheet);

        restMatchSheetMockMvc.perform(put("/api/match-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchSheetDTO)))
            .andExpect(status().isOk());

        // Validate the MatchSheet in the database
        List<MatchSheet> matchSheetList = matchSheetRepository.findAll();
        assertThat(matchSheetList).hasSize(databaseSizeBeforeUpdate);
        MatchSheet testMatchSheet = matchSheetList.get(matchSheetList.size() - 1);
        assertThat(testMatchSheet.getMatchSheetName()).isEqualTo(UPDATED_MATCH_SHEET_NAME);
        assertThat(testMatchSheet.getMatchSheetResume()).isEqualTo(UPDATED_MATCH_SHEET_RESUME);
        assertThat(testMatchSheet.isIsfirst()).isEqualTo(UPDATED_ISFIRST);

        // Validate the MatchSheet in Elasticsearch
        verify(mockMatchSheetSearchRepository, times(1)).save(testMatchSheet);
    }

    @Test
    @Transactional
    public void updateNonExistingMatchSheet() throws Exception {
        int databaseSizeBeforeUpdate = matchSheetRepository.findAll().size();

        // Create the MatchSheet
        MatchSheetDTO matchSheetDTO = matchSheetMapper.toDto(matchSheet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchSheetMockMvc.perform(put("/api/match-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchSheetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MatchSheet in the database
        List<MatchSheet> matchSheetList = matchSheetRepository.findAll();
        assertThat(matchSheetList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MatchSheet in Elasticsearch
        verify(mockMatchSheetSearchRepository, times(0)).save(matchSheet);
    }

    @Test
    @Transactional
    public void deleteMatchSheet() throws Exception {
        // Initialize the database
        matchSheetRepository.saveAndFlush(matchSheet);

        int databaseSizeBeforeDelete = matchSheetRepository.findAll().size();

        // Delete the matchSheet
        restMatchSheetMockMvc.perform(delete("/api/match-sheets/{id}", matchSheet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MatchSheet> matchSheetList = matchSheetRepository.findAll();
        assertThat(matchSheetList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MatchSheet in Elasticsearch
        verify(mockMatchSheetSearchRepository, times(1)).deleteById(matchSheet.getId());
    }

    @Test
    @Transactional
    public void searchMatchSheet() throws Exception {
        // Initialize the database
        matchSheetRepository.saveAndFlush(matchSheet);
        when(mockMatchSheetSearchRepository.search(queryStringQuery("id:" + matchSheet.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(matchSheet), PageRequest.of(0, 1), 1));
        // Search the matchSheet
        restMatchSheetMockMvc.perform(get("/api/_search/match-sheets?query=id:" + matchSheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchSheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].matchSheetName").value(hasItem(DEFAULT_MATCH_SHEET_NAME)))
            .andExpect(jsonPath("$.[*].matchSheetResume").value(hasItem(DEFAULT_MATCH_SHEET_RESUME.toString())))
            .andExpect(jsonPath("$.[*].isfirst").value(hasItem(DEFAULT_ISFIRST.booleanValue())));
    }
}
