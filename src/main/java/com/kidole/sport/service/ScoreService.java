package com.kidole.sport.service;

import com.kidole.sport.service.dto.ScoreDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.Score}.
 */
public interface ScoreService {

    /**
     * Save a score.
     *
     * @param scoreDTO the entity to save.
     * @return the persisted entity.
     */
    ScoreDTO save(ScoreDTO scoreDTO);

    /**
     * Get all the scores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ScoreDTO> findAll(Pageable pageable);


    /**
     * Get the "id" score.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ScoreDTO> findOne(Long id);

    /**
     * Delete the "id" score.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the score corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ScoreDTO> search(String query, Pageable pageable);
}
