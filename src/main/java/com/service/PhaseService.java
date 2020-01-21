package com.service;

import com.domain.Phase;
import com.repository.PhaseRepository;
import com.service.dto.PhaseDTO;
import com.service.mapper.PhaseMapper;
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
 * Service Implementation for managing {@link Phase}.
 */
@Service
@Transactional
public class PhaseService {

    private final Logger log = LoggerFactory.getLogger(PhaseService.class);

    private final PhaseRepository phaseRepository;

    private final PhaseMapper phaseMapper;

    public PhaseService(PhaseRepository phaseRepository, PhaseMapper phaseMapper) {
        this.phaseRepository = phaseRepository;
        this.phaseMapper = phaseMapper;
    }

    /**
     * Save a phase.
     *
     * @param phaseDTO the entity to save.
     * @return the persisted entity.
     */
    public PhaseDTO save(PhaseDTO phaseDTO) {
        log.debug("Request to save Phase : {}", phaseDTO);
        Phase phase = phaseMapper.toEntity(phaseDTO);
        phase = phaseRepository.save(phase);
        return phaseMapper.toDto(phase);
    }

    /**
     * Get all the phases.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PhaseDTO> findAll() {
        log.debug("Request to get all Phases");
        return phaseRepository.findAll().stream()
            .map(phaseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the phases where Format is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<PhaseDTO> findAllWhereFormatIsNull() {
        log.debug("Request to get all phases where Format is null");
        return StreamSupport
            .stream(phaseRepository.findAll().spliterator(), false)
            .filter(phase -> phase.getFormat() == null)
            .map(phaseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one phase by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PhaseDTO> findOne(Long id) {
        log.debug("Request to get Phase : {}", id);
        return phaseRepository.findById(id)
            .map(phaseMapper::toDto);
    }

    /**
     * Delete the phase by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Phase : {}", id);
        phaseRepository.deleteById(id);
    }
}
