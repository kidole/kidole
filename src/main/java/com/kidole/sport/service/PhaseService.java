package com.kidole.sport.service;

import com.kidole.sport.service.dto.PhaseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.Phase}.
 */
public interface PhaseService {

    /**
     * Save a phase.
     *
     * @param phaseDTO the entity to save.
     * @return the persisted entity.
     */
    PhaseDTO save(PhaseDTO phaseDTO);

    /**
     * Get all the phases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PhaseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" phase.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PhaseDTO> findOne(Long id);

    /**
     * Delete the "id" phase.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the phase corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PhaseDTO> search(String query, Pageable pageable);
}
