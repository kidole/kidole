package com.kidole.sport.service;

import com.kidole.sport.service.dto.AccreditationStepDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.AccreditationStep}.
 */
public interface AccreditationStepService {

    /**
     * Save a accreditationStep.
     *
     * @param accreditationStepDTO the entity to save.
     * @return the persisted entity.
     */
    AccreditationStepDTO save(AccreditationStepDTO accreditationStepDTO);

    /**
     * Get all the accreditationSteps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccreditationStepDTO> findAll(Pageable pageable);


    /**
     * Get the "id" accreditationStep.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccreditationStepDTO> findOne(Long id);

    /**
     * Delete the "id" accreditationStep.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the accreditationStep corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccreditationStepDTO> search(String query, Pageable pageable);
}
