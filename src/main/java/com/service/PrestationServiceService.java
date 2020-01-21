package com.service;

import com.domain.PrestationService;
import com.repository.PrestationServiceRepository;
import com.service.dto.PrestationServiceDTO;
import com.service.mapper.PrestationServiceMapper;
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
 * Service Implementation for managing {@link PrestationService}.
 */
@Service
@Transactional
public class PrestationServiceService {

    private final Logger log = LoggerFactory.getLogger(PrestationServiceService.class);

    private final PrestationServiceRepository prestationServiceRepository;

    private final PrestationServiceMapper prestationServiceMapper;

    public PrestationServiceService(PrestationServiceRepository prestationServiceRepository, PrestationServiceMapper prestationServiceMapper) {
        this.prestationServiceRepository = prestationServiceRepository;
        this.prestationServiceMapper = prestationServiceMapper;
    }

    /**
     * Save a prestationService.
     *
     * @param prestationServiceDTO the entity to save.
     * @return the persisted entity.
     */
    public PrestationServiceDTO save(PrestationServiceDTO prestationServiceDTO) {
        log.debug("Request to save PrestationService : {}", prestationServiceDTO);
        PrestationService prestationService = prestationServiceMapper.toEntity(prestationServiceDTO);
        prestationService = prestationServiceRepository.save(prestationService);
        return prestationServiceMapper.toDto(prestationService);
    }

    /**
     * Get all the prestationServices.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PrestationServiceDTO> findAll() {
        log.debug("Request to get all PrestationServices");
        return prestationServiceRepository.findAll().stream()
            .map(prestationServiceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the prestationServices where Competitionservicejoined is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<PrestationServiceDTO> findAllWhereCompetitionservicejoinedIsNull() {
        log.debug("Request to get all prestationServices where Competitionservicejoined is null");
        return StreamSupport
            .stream(prestationServiceRepository.findAll().spliterator(), false)
            .filter(prestationService -> prestationService.getCompetitionservicejoined() == null)
            .map(prestationServiceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one prestationService by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrestationServiceDTO> findOne(Long id) {
        log.debug("Request to get PrestationService : {}", id);
        return prestationServiceRepository.findById(id)
            .map(prestationServiceMapper::toDto);
    }

    /**
     * Delete the prestationService by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PrestationService : {}", id);
        prestationServiceRepository.deleteById(id);
    }
}
