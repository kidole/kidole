package com.kidole.sport.service;

import com.kidole.sport.service.dto.RubriqueDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.Rubrique}.
 */
public interface RubriqueService {

    /**
     * Save a rubrique.
     *
     * @param rubriqueDTO the entity to save.
     * @return the persisted entity.
     */
    RubriqueDTO save(RubriqueDTO rubriqueDTO);

    /**
     * Get all the rubriques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RubriqueDTO> findAll(Pageable pageable);


    /**
     * Get the "id" rubrique.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RubriqueDTO> findOne(Long id);

    /**
     * Delete the "id" rubrique.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the rubrique corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RubriqueDTO> search(String query, Pageable pageable);
}
