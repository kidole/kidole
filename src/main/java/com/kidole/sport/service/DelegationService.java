package com.kidole.sport.service;

import com.kidole.sport.service.dto.DelegationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.Delegation}.
 */
public interface DelegationService {

    /**
     * Save a delegation.
     *
     * @param delegationDTO the entity to save.
     * @return the persisted entity.
     */
    DelegationDTO save(DelegationDTO delegationDTO);

    /**
     * Get all the delegations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DelegationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" delegation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DelegationDTO> findOne(Long id);

    /**
     * Delete the "id" delegation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the delegation corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DelegationDTO> search(String query, Pageable pageable);
}
