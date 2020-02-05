package com.kidole.sport.service;

import com.kidole.sport.service.dto.JourneeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.Journee}.
 */
public interface JourneeService {

    /**
     * Save a journee.
     *
     * @param journeeDTO the entity to save.
     * @return the persisted entity.
     */
    JourneeDTO save(JourneeDTO journeeDTO);

    /**
     * Get all the journees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JourneeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" journee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JourneeDTO> findOne(Long id);

    /**
     * Delete the "id" journee.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the journee corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JourneeDTO> search(String query, Pageable pageable);
}
