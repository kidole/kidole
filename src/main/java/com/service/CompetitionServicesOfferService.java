package com.service;

import com.domain.CompetitionServicesOffer;
import com.repository.CompetitionServicesOfferRepository;
import com.service.dto.CompetitionServicesOfferDTO;
import com.service.mapper.CompetitionServicesOfferMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CompetitionServicesOffer}.
 */
@Service
@Transactional
public class CompetitionServicesOfferService {

    private final Logger log = LoggerFactory.getLogger(CompetitionServicesOfferService.class);

    private final CompetitionServicesOfferRepository competitionServicesOfferRepository;

    private final CompetitionServicesOfferMapper competitionServicesOfferMapper;

    public CompetitionServicesOfferService(CompetitionServicesOfferRepository competitionServicesOfferRepository, CompetitionServicesOfferMapper competitionServicesOfferMapper) {
        this.competitionServicesOfferRepository = competitionServicesOfferRepository;
        this.competitionServicesOfferMapper = competitionServicesOfferMapper;
    }

    /**
     * Save a competitionServicesOffer.
     *
     * @param competitionServicesOfferDTO the entity to save.
     * @return the persisted entity.
     */
    public CompetitionServicesOfferDTO save(CompetitionServicesOfferDTO competitionServicesOfferDTO) {
        log.debug("Request to save CompetitionServicesOffer : {}", competitionServicesOfferDTO);
        CompetitionServicesOffer competitionServicesOffer = competitionServicesOfferMapper.toEntity(competitionServicesOfferDTO);
        competitionServicesOffer = competitionServicesOfferRepository.save(competitionServicesOffer);
        return competitionServicesOfferMapper.toDto(competitionServicesOffer);
    }

    /**
     * Get all the competitionServicesOffers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CompetitionServicesOfferDTO> findAll() {
        log.debug("Request to get all CompetitionServicesOffers");
        return competitionServicesOfferRepository.findAll().stream()
            .map(competitionServicesOfferMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one competitionServicesOffer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
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
    public void delete(Long id) {
        log.debug("Request to delete CompetitionServicesOffer : {}", id);
        competitionServicesOfferRepository.deleteById(id);
    }
}
