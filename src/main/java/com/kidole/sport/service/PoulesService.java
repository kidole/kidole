package com.kidole.sport.service;

import com.kidole.sport.service.dto.PoulesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.Poules}.
 */
public interface PoulesService {

    /**
     * Save a poules.
     *
     * @param poulesDTO the entity to save.
     * @return the persisted entity.
     */
    PoulesDTO save(PoulesDTO poulesDTO);

    /**
     * Get all the poules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PoulesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" poules.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PoulesDTO> findOne(Long id);

    /**
     * Delete the "id" poules.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the poules corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PoulesDTO> search(String query, Pageable pageable);
}
