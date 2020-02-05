package com.kidole.sport.service;

import com.kidole.sport.service.dto.CompetitionServicesOfferDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.CompetitionServicesOffer}.
 */
public interface CompetitionServicesOfferService {

    /**
     * Save a competitionServicesOffer.
     *
     * @param competitionServicesOfferDTO the entity to save.
     * @return the persisted entity.
     */
    CompetitionServicesOfferDTO save(CompetitionServicesOfferDTO competitionServicesOfferDTO);

    /**
     * Get all the competitionServicesOffers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompetitionServicesOfferDTO> findAll(Pageable pageable);


    /**
     * Get the "id" competitionServicesOffer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompetitionServicesOfferDTO> findOne(Long id);

    /**
     * Delete the "id" competitionServicesOffer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the competitionServicesOffer corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompetitionServicesOfferDTO> search(String query, Pageable pageable);
}
