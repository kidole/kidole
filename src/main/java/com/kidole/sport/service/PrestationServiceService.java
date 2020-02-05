package com.kidole.sport.service;

import com.kidole.sport.service.dto.PrestationServiceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.PrestationService}.
 */
public interface PrestationServiceService {

    /**
     * Save a prestationService.
     *
     * @param prestationServiceDTO the entity to save.
     * @return the persisted entity.
     */
    PrestationServiceDTO save(PrestationServiceDTO prestationServiceDTO);

    /**
     * Get all the prestationServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrestationServiceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" prestationService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrestationServiceDTO> findOne(Long id);

    /**
     * Delete the "id" prestationService.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the prestationService corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PrestationServiceDTO> search(String query, Pageable pageable);
}
