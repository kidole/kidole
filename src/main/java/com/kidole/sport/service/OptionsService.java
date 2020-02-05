package com.kidole.sport.service;

import com.kidole.sport.service.dto.OptionsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.Options}.
 */
public interface OptionsService {

    /**
     * Save a options.
     *
     * @param optionsDTO the entity to save.
     * @return the persisted entity.
     */
    OptionsDTO save(OptionsDTO optionsDTO);

    /**
     * Get all the options.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OptionsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" options.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OptionsDTO> findOne(Long id);

    /**
     * Delete the "id" options.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the options corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OptionsDTO> search(String query, Pageable pageable);
}
