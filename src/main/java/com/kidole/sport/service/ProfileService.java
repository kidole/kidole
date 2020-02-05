package com.kidole.sport.service;

import com.kidole.sport.service.dto.ProfileDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.Profile}.
 */
public interface ProfileService {

    /**
     * Save a profile.
     *
     * @param profileDTO the entity to save.
     * @return the persisted entity.
     */
    ProfileDTO save(ProfileDTO profileDTO);

    /**
     * Get all the profiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProfileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" profile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProfileDTO> findOne(Long id);

    /**
     * Delete the "id" profile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the profile corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProfileDTO> search(String query, Pageable pageable);
}
