package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.Competition;
import com.kidole.sport.repository.CompetitionRepository;
import com.kidole.sport.repository.search.CompetitionSearchRepository;
import com.kidole.sport.service.CompetitionService;
import com.kidole.sport.service.dto.CompetitionDTO;
import com.kidole.sport.service.mapper.CompetitionMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link CompetitionResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class CompetitionResourceIT {

    private static final String DEFAULT_COMPETITION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPETITION_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_LIMIT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_LIMIT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_RULE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RULE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RULE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RULE_CONTENT_TYPE = "image/png";

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private CompetitionMapper competitionMapper;

    @Autowired
    private CompetitionService competitionService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.CompetitionSearchRepositoryMockConfiguration
     */
    @Autowired
    private CompetitionSearchRepository mockCompetitionSearchRepository;

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

    private MockMvc restCompetitionMockMvc;

    private Competition competition;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompetitionResource competitionResource = new CompetitionResource(competitionService);
        this.restCompetitionMockMvc = MockMvcBuilders.standaloneSetup(competitionResource)
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
    public static Competition createEntity(EntityManager em) {
        Competition competition = new Competition()
            .competitionName(DEFAULT_COMPETITION_NAME)
            .start(DEFAULT_START)
            .end(DEFAULT_END)
            .dateLimit(DEFAULT_DATE_LIMIT)
            .detail(DEFAULT_DETAIL)
            .rule(DEFAULT_RULE)
            .ruleContentType(DEFAULT_RULE_CONTENT_TYPE);
        return competition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competition createUpdatedEntity(EntityManager em) {
        Competition competition = new Competition()
            .competitionName(UPDATED_COMPETITION_NAME)
            .start(UPDATED_START)
            .end(UPDATED_END)
            .dateLimit(UPDATED_DATE_LIMIT)
            .detail(UPDATED_DETAIL)
            .rule(UPDATED_RULE)
            .ruleContentType(UPDATED_RULE_CONTENT_TYPE);
        return competition;
    }

    @BeforeEach
    public void initTest() {
        competition = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetition() throws Exception {
        int databaseSizeBeforeCreate = competitionRepository.findAll().size();

        // Create the Competition
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);
        restCompetitionMockMvc.perform(post("/api/competitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionDTO)))
            .andExpect(status().isCreated());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeCreate + 1);
        Competition testCompetition = competitionList.get(competitionList.size() - 1);
        assertThat(testCompetition.getCompetitionName()).isEqualTo(DEFAULT_COMPETITION_NAME);
        assertThat(testCompetition.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testCompetition.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testCompetition.getDateLimit()).isEqualTo(DEFAULT_DATE_LIMIT);
        assertThat(testCompetition.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testCompetition.getRule()).isEqualTo(DEFAULT_RULE);
        assertThat(testCompetition.getRuleContentType()).isEqualTo(DEFAULT_RULE_CONTENT_TYPE);

        // Validate the Competition in Elasticsearch
        verify(mockCompetitionSearchRepository, times(1)).save(testCompetition);
    }

    @Test
    @Transactional
    public void createCompetitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitionRepository.findAll().size();

        // Create the Competition with an existing ID
        competition.setId(1L);
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitionMockMvc.perform(post("/api/competitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Competition in Elasticsearch
        verify(mockCompetitionSearchRepository, times(0)).save(competition);
    }


    @Test
    @Transactional
    public void checkCompetitionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitionRepository.findAll().size();
        // set the field null
        competition.setCompetitionName(null);

        // Create the Competition, which fails.
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        restCompetitionMockMvc.perform(post("/api/competitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionDTO)))
            .andExpect(status().isBadRequest());

        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitionRepository.findAll().size();
        // set the field null
        competition.setStart(null);

        // Create the Competition, which fails.
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        restCompetitionMockMvc.perform(post("/api/competitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionDTO)))
            .andExpect(status().isBadRequest());

        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitionRepository.findAll().size();
        // set the field null
        competition.setEnd(null);

        // Create the Competition, which fails.
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        restCompetitionMockMvc.perform(post("/api/competitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionDTO)))
            .andExpect(status().isBadRequest());

        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateLimitIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitionRepository.findAll().size();
        // set the field null
        competition.setDateLimit(null);

        // Create the Competition, which fails.
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        restCompetitionMockMvc.perform(post("/api/competitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionDTO)))
            .andExpect(status().isBadRequest());

        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompetitions() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList
        restCompetitionMockMvc.perform(get("/api/competitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competition.getId().intValue())))
            .andExpect(jsonPath("$.[*].competitionName").value(hasItem(DEFAULT_COMPETITION_NAME)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].dateLimit").value(hasItem(DEFAULT_DATE_LIMIT.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].ruleContentType").value(hasItem(DEFAULT_RULE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].rule").value(hasItem(Base64Utils.encodeToString(DEFAULT_RULE))));
    }
    
    @Test
    @Transactional
    public void getCompetition() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get the competition
        restCompetitionMockMvc.perform(get("/api/competitions/{id}", competition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competition.getId().intValue()))
            .andExpect(jsonPath("$.competitionName").value(DEFAULT_COMPETITION_NAME))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.dateLimit").value(DEFAULT_DATE_LIMIT.toString()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.ruleContentType").value(DEFAULT_RULE_CONTENT_TYPE))
            .andExpect(jsonPath("$.rule").value(Base64Utils.encodeToString(DEFAULT_RULE)));
    }

    @Test
    @Transactional
    public void getNonExistingCompetition() throws Exception {
        // Get the competition
        restCompetitionMockMvc.perform(get("/api/competitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetition() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        int databaseSizeBeforeUpdate = competitionRepository.findAll().size();

        // Update the competition
        Competition updatedCompetition = competitionRepository.findById(competition.getId()).get();
        // Disconnect from session so that the updates on updatedCompetition are not directly saved in db
        em.detach(updatedCompetition);
        updatedCompetition
            .competitionName(UPDATED_COMPETITION_NAME)
            .start(UPDATED_START)
            .end(UPDATED_END)
            .dateLimit(UPDATED_DATE_LIMIT)
            .detail(UPDATED_DETAIL)
            .rule(UPDATED_RULE)
            .ruleContentType(UPDATED_RULE_CONTENT_TYPE);
        CompetitionDTO competitionDTO = competitionMapper.toDto(updatedCompetition);

        restCompetitionMockMvc.perform(put("/api/competitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionDTO)))
            .andExpect(status().isOk());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeUpdate);
        Competition testCompetition = competitionList.get(competitionList.size() - 1);
        assertThat(testCompetition.getCompetitionName()).isEqualTo(UPDATED_COMPETITION_NAME);
        assertThat(testCompetition.getStart()).isEqualTo(UPDATED_START);
        assertThat(testCompetition.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testCompetition.getDateLimit()).isEqualTo(UPDATED_DATE_LIMIT);
        assertThat(testCompetition.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testCompetition.getRule()).isEqualTo(UPDATED_RULE);
        assertThat(testCompetition.getRuleContentType()).isEqualTo(UPDATED_RULE_CONTENT_TYPE);

        // Validate the Competition in Elasticsearch
        verify(mockCompetitionSearchRepository, times(1)).save(testCompetition);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetition() throws Exception {
        int databaseSizeBeforeUpdate = competitionRepository.findAll().size();

        // Create the Competition
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitionMockMvc.perform(put("/api/competitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Competition in Elasticsearch
        verify(mockCompetitionSearchRepository, times(0)).save(competition);
    }

    @Test
    @Transactional
    public void deleteCompetition() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        int databaseSizeBeforeDelete = competitionRepository.findAll().size();

        // Delete the competition
        restCompetitionMockMvc.perform(delete("/api/competitions/{id}", competition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Competition in Elasticsearch
        verify(mockCompetitionSearchRepository, times(1)).deleteById(competition.getId());
    }

    @Test
    @Transactional
    public void searchCompetition() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);
        when(mockCompetitionSearchRepository.search(queryStringQuery("id:" + competition.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(competition), PageRequest.of(0, 1), 1));
        // Search the competition
        restCompetitionMockMvc.perform(get("/api/_search/competitions?query=id:" + competition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competition.getId().intValue())))
            .andExpect(jsonPath("$.[*].competitionName").value(hasItem(DEFAULT_COMPETITION_NAME)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].dateLimit").value(hasItem(DEFAULT_DATE_LIMIT.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].ruleContentType").value(hasItem(DEFAULT_RULE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].rule").value(hasItem(Base64Utils.encodeToString(DEFAULT_RULE))));
    }
}
