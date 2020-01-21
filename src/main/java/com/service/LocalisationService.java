package com.service;

import com.domain.Localisation;
import com.repository.LocalisationRepository;
import com.service.dto.LocalisationDTO;
import com.service.mapper.LocalisationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Localisation}.
 */
@Service
@Transactional
public class LocalisationService {

    private final Logger log = LoggerFactory.getLogger(LocalisationService.class);

    private final LocalisationRepository localisationRepository;

    private final LocalisationMapper localisationMapper;

    public LocalisationService(LocalisationRepository localisationRepository, LocalisationMapper localisationMapper) {
        this.localisationRepository = localisationRepository;
        this.localisationMapper = localisationMapper;
    }

    /**
     * Save a localisation.
     *
     * @param localisationDTO the entity to save.
     * @return the persisted entity.
     */
    public LocalisationDTO save(LocalisationDTO localisationDTO) {
        log.debug("Request to save Localisation : {}", localisationDTO);
        Localisation localisation = localisationMapper.toEntity(localisationDTO);
        localisation = localisationRepository.save(localisation);
        return localisationMapper.toDto(localisation);
    }

    /**
     * Get all the localisations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LocalisationDTO> findAll() {
        log.debug("Request to get all Localisations");
        return localisationRepository.findAll().stream()
            .map(localisationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the localisations where Confrontation is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<LocalisationDTO> findAllWhereConfrontationIsNull() {
        log.debug("Request to get all localisations where Confrontation is null");
        return StreamSupport
            .stream(localisationRepository.findAll().spliterator(), false)
            .filter(localisation -> localisation.getConfrontation() == null)
            .map(localisationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one localisation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocalisationDTO> findOne(Long id) {
        log.debug("Request to get Localisation : {}", id);
        return localisationRepository.findById(id)
            .map(localisationMapper::toDto);
    }

    /**
     * Delete the localisation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Localisation : {}", id);
        localisationRepository.deleteById(id);
    }
}
