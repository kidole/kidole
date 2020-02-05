package com.kidole.sport.service;

import com.kidole.sport.service.dto.MatchSheetDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.MatchSheet}.
 */
public interface MatchSheetService {

    /**
     * Save a matchSheet.
     *
     * @param matchSheetDTO the entity to save.
     * @return the persisted entity.
     */
    MatchSheetDTO save(MatchSheetDTO matchSheetDTO);

    /**
     * Get all the matchSheets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MatchSheetDTO> findAll(Pageable pageable);


    /**
     * Get the "id" matchSheet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatchSheetDTO> findOne(Long id);

    /**
     * Delete the "id" matchSheet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the matchSheet corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MatchSheetDTO> search(String query, Pageable pageable);
}
