package com.kidole.sport.service;

import com.kidole.sport.service.dto.ConfrontationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.Confrontation}.
 */
public interface ConfrontationService {

    /**
     * Save a confrontation.
     *
     * @param confrontationDTO the entity to save.
     * @return the persisted entity.
     */
    ConfrontationDTO save(ConfrontationDTO confrontationDTO);

    /**
     * Get all the confrontations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConfrontationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" confrontation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConfrontationDTO> findOne(Long id);

    /**
     * Delete the "id" confrontation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the confrontation corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConfrontationDTO> search(String query, Pageable pageable);
}
