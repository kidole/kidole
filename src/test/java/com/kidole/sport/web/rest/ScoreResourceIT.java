package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.Score;
import com.kidole.sport.repository.ScoreRepository;
import com.kidole.sport.repository.search.ScoreSearchRepository;
import com.kidole.sport.service.ScoreService;
import com.kidole.sport.service.dto.ScoreDTO;
import com.kidole.sport.service.mapper.ScoreMapper;
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
 * Integration tests for the {@link ScoreResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class ScoreResourceIT {

    private static final String DEFAULT_SCORE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCORE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE_INDEX = 0;
    private static final Integer UPDATED_SCORE_INDEX = 1;

    private static final Double DEFAULT_SCORE_VALUE = 1D;
    private static final Double UPDATED_SCORE_VALUE = 2D;

    private static final String DEFAULT_SCORE_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_SCORE_UNIT = "BBBBBBBBBB";

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private ScoreService scoreService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.ScoreSearchRepositoryMockConfiguration
     */
    @Autowired
    private ScoreSearchRepository mockScoreSearchRepository;

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

    private MockMvc restScoreMockMvc;

    private Score score;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScoreResource scoreResource = new ScoreResource(scoreService);
        this.restScoreMockMvc = MockMvcBuilders.standaloneSetup(scoreResource)
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
    public static Score createEntity(EntityManager em) {
        Score score = new Score()
            .scoreName(DEFAULT_SCORE_NAME)
            .scoreIndex(DEFAULT_SCORE_INDEX)
            .scoreValue(DEFAULT_SCORE_VALUE)
            .scoreUnit(DEFAULT_SCORE_UNIT);
        return score;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Score createUpdatedEntity(EntityManager em) {
        Score score = new Score()
            .scoreName(UPDATED_SCORE_NAME)
            .scoreIndex(UPDATED_SCORE_INDEX)
            .scoreValue(UPDATED_SCORE_VALUE)
            .scoreUnit(UPDATED_SCORE_UNIT);
        return score;
    }

    @BeforeEach
    public void initTest() {
        score = createEntity(em);
    }

    @Test
    @Transactional
    public void createScore() throws Exception {
        int databaseSizeBeforeCreate = scoreRepository.findAll().size();

        // Create the Score
        ScoreDTO scoreDTO = scoreMapper.toDto(score);
        restScoreMockMvc.perform(post("/api/scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isCreated());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeCreate + 1);
        Score testScore = scoreList.get(scoreList.size() - 1);
        assertThat(testScore.getScoreName()).isEqualTo(DEFAULT_SCORE_NAME);
        assertThat(testScore.getScoreIndex()).isEqualTo(DEFAULT_SCORE_INDEX);
        assertThat(testScore.getScoreValue()).isEqualTo(DEFAULT_SCORE_VALUE);
        assertThat(testScore.getScoreUnit()).isEqualTo(DEFAULT_SCORE_UNIT);

        // Validate the Score in Elasticsearch
        verify(mockScoreSearchRepository, times(1)).save(testScore);
    }

    @Test
    @Transactional
    public void createScoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scoreRepository.findAll().size();

        // Create the Score with an existing ID
        score.setId(1L);
        ScoreDTO scoreDTO = scoreMapper.toDto(score);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScoreMockMvc.perform(post("/api/scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeCreate);

        // Validate the Score in Elasticsearch
        verify(mockScoreSearchRepository, times(0)).save(score);
    }


    @Test
    @Transactional
    public void checkScoreNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = scoreRepository.findAll().size();
        // set the field null
        score.setScoreName(null);

        // Create the Score, which fails.
        ScoreDTO scoreDTO = scoreMapper.toDto(score);

        restScoreMockMvc.perform(post("/api/scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isBadRequest());

        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScoreIndexIsRequired() throws Exception {
        int databaseSizeBeforeTest = scoreRepository.findAll().size();
        // set the field null
        score.setScoreIndex(null);

        // Create the Score, which fails.
        ScoreDTO scoreDTO = scoreMapper.toDto(score);

        restScoreMockMvc.perform(post("/api/scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isBadRequest());

        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScoreValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = scoreRepository.findAll().size();
        // set the field null
        score.setScoreValue(null);

        // Create the Score, which fails.
        ScoreDTO scoreDTO = scoreMapper.toDto(score);

        restScoreMockMvc.perform(post("/api/scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isBadRequest());

        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScoreUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = scoreRepository.findAll().size();
        // set the field null
        score.setScoreUnit(null);

        // Create the Score, which fails.
        ScoreDTO scoreDTO = scoreMapper.toDto(score);

        restScoreMockMvc.perform(post("/api/scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isBadRequest());

        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScores() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scoreList
        restScoreMockMvc.perform(get("/api/scores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(score.getId().intValue())))
            .andExpect(jsonPath("$.[*].scoreName").value(hasItem(DEFAULT_SCORE_NAME)))
            .andExpect(jsonPath("$.[*].scoreIndex").value(hasItem(DEFAULT_SCORE_INDEX)))
            .andExpect(jsonPath("$.[*].scoreValue").value(hasItem(DEFAULT_SCORE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].scoreUnit").value(hasItem(DEFAULT_SCORE_UNIT)));
    }
    
    @Test
    @Transactional
    public void getScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get the score
        restScoreMockMvc.perform(get("/api/scores/{id}", score.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(score.getId().intValue()))
            .andExpect(jsonPath("$.scoreName").value(DEFAULT_SCORE_NAME))
            .andExpect(jsonPath("$.scoreIndex").value(DEFAULT_SCORE_INDEX))
            .andExpect(jsonPath("$.scoreValue").value(DEFAULT_SCORE_VALUE.doubleValue()))
            .andExpect(jsonPath("$.scoreUnit").value(DEFAULT_SCORE_UNIT));
    }

    @Test
    @Transactional
    public void getNonExistingScore() throws Exception {
        // Get the score
        restScoreMockMvc.perform(get("/api/scores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        int databaseSizeBeforeUpdate = scoreRepository.findAll().size();

        // Update the score
        Score updatedScore = scoreRepository.findById(score.getId()).get();
        // Disconnect from session so that the updates on updatedScore are not directly saved in db
        em.detach(updatedScore);
        updatedScore
            .scoreName(UPDATED_SCORE_NAME)
            .scoreIndex(UPDATED_SCORE_INDEX)
            .scoreValue(UPDATED_SCORE_VALUE)
            .scoreUnit(UPDATED_SCORE_UNIT);
        ScoreDTO scoreDTO = scoreMapper.toDto(updatedScore);

        restScoreMockMvc.perform(put("/api/scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isOk());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeUpdate);
        Score testScore = scoreList.get(scoreList.size() - 1);
        assertThat(testScore.getScoreName()).isEqualTo(UPDATED_SCORE_NAME);
        assertThat(testScore.getScoreIndex()).isEqualTo(UPDATED_SCORE_INDEX);
        assertThat(testScore.getScoreValue()).isEqualTo(UPDATED_SCORE_VALUE);
        assertThat(testScore.getScoreUnit()).isEqualTo(UPDATED_SCORE_UNIT);

        // Validate the Score in Elasticsearch
        verify(mockScoreSearchRepository, times(1)).save(testScore);
    }

    @Test
    @Transactional
    public void updateNonExistingScore() throws Exception {
        int databaseSizeBeforeUpdate = scoreRepository.findAll().size();

        // Create the Score
        ScoreDTO scoreDTO = scoreMapper.toDto(score);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScoreMockMvc.perform(put("/api/scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Score in the database
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Score in Elasticsearch
        verify(mockScoreSearchRepository, times(0)).save(score);
    }

    @Test
    @Transactional
    public void deleteScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        int databaseSizeBeforeDelete = scoreRepository.findAll().size();

        // Delete the score
        restScoreMockMvc.perform(delete("/api/scores/{id}", score.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Score> scoreList = scoreRepository.findAll();
        assertThat(scoreList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Score in Elasticsearch
        verify(mockScoreSearchRepository, times(1)).deleteById(score.getId());
    }

    @Test
    @Transactional
    public void searchScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);
        when(mockScoreSearchRepository.search(queryStringQuery("id:" + score.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(score), PageRequest.of(0, 1), 1));
        // Search the score
        restScoreMockMvc.perform(get("/api/_search/scores?query=id:" + score.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(score.getId().intValue())))
            .andExpect(jsonPath("$.[*].scoreName").value(hasItem(DEFAULT_SCORE_NAME)))
            .andExpect(jsonPath("$.[*].scoreIndex").value(hasItem(DEFAULT_SCORE_INDEX)))
            .andExpect(jsonPath("$.[*].scoreValue").value(hasItem(DEFAULT_SCORE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].scoreUnit").value(hasItem(DEFAULT_SCORE_UNIT)));
    }
}
