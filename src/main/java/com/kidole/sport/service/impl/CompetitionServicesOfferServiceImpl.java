package com.kidole.sport.service.impl;

import com.kidole.sport.service.CompetitionServicesOfferService;
import com.kidole.sport.domain.CompetitionServicesOffer;
import com.kidole.sport.repository.CompetitionServicesOfferRepository;
import com.kidole.sport.repository.search.CompetitionServicesOfferSearchRepository;
import com.kidole.sport.service.dto.CompetitionServicesOfferDTO;
import com.kidole.sport.service.mapper.CompetitionServicesOfferMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CompetitionServicesOffer}.
 */
@Service
@Transactional
public class CompetitionServicesOfferServiceImpl implements CompetitionServicesOfferService {

    private final Logger log = LoggerFactory.getLogger(CompetitionServicesOfferServiceImpl.class);

    private final CompetitionServicesOfferRepository competitionServicesOfferRepository;

    private final CompetitionServicesOfferMapper competitionServicesOfferMapper;

    private final CompetitionServicesOfferSearchRepository competitionServicesOfferSearchRepository;

    public CompetitionServicesOfferServiceImpl(CompetitionServicesOfferRepository competitionServicesOfferRepository, CompetitionServicesOfferMapper competitionServicesOfferMapper, CompetitionServicesOfferSearchRepository competitionServicesOfferSearchRepository) {
        this.competitionServicesOfferRepository = competitionServicesOfferRepository;
        this.competitionServicesOfferMapper = competitionServicesOfferMapper;
        this.competitionServicesOfferSearchRepository = competitionServicesOfferSearchRepository;
    }

    /**
     * Save a competitionServicesOffer.
     *
     * @param competitionServicesOfferDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CompetitionServicesOfferDTO save(CompetitionServicesOfferDTO competitionServicesOfferDTO) {
        log.debug("Request to save CompetitionServicesOffer : {}", competitionServicesOfferDTO);
        CompetitionServicesOffer competitionServicesOffer = competitionServicesOfferMapper.toEntity(competitionServicesOfferDTO);
        competitionServicesOffer = competitionServicesOfferRepository.save(competitionServicesOffer);
        CompetitionServicesOfferDTO result = competitionServicesOfferMapper.toDto(competitionServicesOffer);
        competitionServicesOfferSearchRepository.save(competitionServicesOffer);
        return result;
    }

    /**
     * Get all the competitionServicesOffers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompetitionServicesOfferDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompetitionServicesOffers");
        return competitionServicesOfferRepository.findAll(pageable)
            .map(competitionServicesOfferMapper::toDto);
    }


    /**
     * Get one competitionServicesOffer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompetitionServicesOfferDTO> findOne(Long id) {
        log.debug("Request to get CompetitionServicesOffer : {}", id);
        return competitionServicesOfferRepository.findById(id)
            .map(competitionServicesOfferMapper::toDto);
    }

    /**
     * Delete the competitionServicesOffer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompetitionServicesOffer : {}", id);
        competitionServicesOfferRepository.deleteById(id);
        competitionServicesOfferSearchRepository.deleteById(id);
    }

    /**
     * Search for the competitionServicesOffer corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompetitionServicesOfferDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CompetitionServicesOffers for query {}", query);
        return competitionServicesOfferSearchRepository.search(queryStringQuery(query), pageable)
            .map(competitionServicesOfferMapper::toDto);
    }
}
