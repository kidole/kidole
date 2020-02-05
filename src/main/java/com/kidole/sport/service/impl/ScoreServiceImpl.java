package com.kidole.sport.service.impl;

import com.kidole.sport.service.ScoreService;
import com.kidole.sport.domain.Score;
import com.kidole.sport.repository.ScoreRepository;
import com.kidole.sport.repository.search.ScoreSearchRepository;
import com.kidole.sport.service.dto.ScoreDTO;
import com.kidole.sport.service.mapper.ScoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Score}.
 */
@Service
@Transactional
public class ScoreServiceImpl implements ScoreService {

    private final Logger log = LoggerFactory.getLogger(ScoreServiceImpl.class);

    private final ScoreRepository scoreRepository;

    private final ScoreMapper scoreMapper;

    private final ScoreSearchRepository scoreSearchRepository;

    public ScoreServiceImpl(ScoreRepository scoreRepository, ScoreMapper scoreMapper, ScoreSearchRepository scoreSearchRepository) {
        this.scoreRepository = scoreRepository;
        this.scoreMapper = scoreMapper;
        this.scoreSearchRepository = scoreSearchRepository;
    }

    /**
     * Save a score.
     *
     * @param scoreDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ScoreDTO save(ScoreDTO scoreDTO) {
        log.debug("Request to save Score : {}", scoreDTO);
        Score score = scoreMapper.toEntity(scoreDTO);
        score = scoreRepository.save(score);
        ScoreDTO result = scoreMapper.toDto(score);
        scoreSearchRepository.save(score);
        return result;
    }

    /**
     * Get all the scores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ScoreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Scores");
        return scoreRepository.findAll(pageable)
            .map(scoreMapper::toDto);
    }


    /**
     * Get one score by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ScoreDTO> findOne(Long id) {
        log.debug("Request to get Score : {}", id);
        return scoreRepository.findById(id)
            .map(scoreMapper::toDto);
    }

    /**
     * Delete the score by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Score : {}", id);
        scoreRepository.deleteById(id);
        scoreSearchRepository.deleteById(id);
    }

    /**
     * Search for the score corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ScoreDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Scores for query {}", query);
        return scoreSearchRepository.search(queryStringQuery(query), pageable)
            .map(scoreMapper::toDto);
    }
}
