package com.kidole.sport.service;

import com.kidole.sport.service.dto.AccreditationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.Accreditation}.
 */
public interface AccreditationService {

    /**
     * Save a accreditation.
     *
     * @param accreditationDTO the entity to save.
     * @return the persisted entity.
     */
    AccreditationDTO save(AccreditationDTO accreditationDTO);

    /**
     * Get all the accreditations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccreditationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" accreditation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccreditationDTO> findOne(Long id);

    /**
     * Delete the "id" accreditation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the accreditation corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccreditationDTO> search(String query, Pageable pageable);
}
