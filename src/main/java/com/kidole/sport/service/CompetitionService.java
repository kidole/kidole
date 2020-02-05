package com.kidole.sport.service;

import com.kidole.sport.service.dto.CompetitionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.Competition}.
 */
public interface CompetitionService {

    /**
     * Save a competition.
     *
     * @param competitionDTO the entity to save.
     * @return the persisted entity.
     */
    CompetitionDTO save(CompetitionDTO competitionDTO);

    /**
     * Get all the competitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompetitionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" competition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompetitionDTO> findOne(Long id);

    /**
     * Delete the "id" competition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the competition corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompetitionDTO> search(String query, Pageable pageable);
}
