package com.kidole.sport.service;

import com.kidole.sport.service.dto.DelegationMembersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.DelegationMembers}.
 */
public interface DelegationMembersService {

    /**
     * Save a delegationMembers.
     *
     * @param delegationMembersDTO the entity to save.
     * @return the persisted entity.
     */
    DelegationMembersDTO save(DelegationMembersDTO delegationMembersDTO);

    /**
     * Get all the delegationMembers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DelegationMembersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" delegationMembers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DelegationMembersDTO> findOne(Long id);

    /**
     * Delete the "id" delegationMembers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the delegationMembers corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DelegationMembersDTO> search(String query, Pageable pageable);
}
