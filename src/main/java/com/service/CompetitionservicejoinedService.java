package com.service;

import com.domain.Competitionservicejoined;
import com.repository.CompetitionservicejoinedRepository;
import com.service.dto.CompetitionservicejoinedDTO;
import com.service.mapper.CompetitionservicejoinedMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Competitionservicejoined}.
 */
@Service
@Transactional
public class CompetitionservicejoinedService {

    private final Logger log = LoggerFactory.getLogger(CompetitionservicejoinedService.class);

    private final CompetitionservicejoinedRepository competitionservicejoinedRepository;

    private final CompetitionservicejoinedMapper competitionservicejoinedMapper;

    public CompetitionservicejoinedService(CompetitionservicejoinedRepository competitionservicejoinedRepository, CompetitionservicejoinedMapper competitionservicejoinedMapper) {
        this.competitionservicejoinedRepository = competitionservicejoinedRepository;
        this.competitionservicejoinedMapper = competitionservicejoinedMapper;
    }

    /**
     * Save a competitionservicejoined.
     *
     * @param competitionservicejoinedDTO the entity to save.
     * @return the persisted entity.
     */
    public CompetitionservicejoinedDTO save(CompetitionservicejoinedDTO competitionservicejoinedDTO) {
        log.debug("Request to save Competitionservicejoined : {}", competitionservicejoinedDTO);
        Competitionservicejoined competitionservicejoined = competitionservicejoinedMapper.toEntity(competitionservicejoinedDTO);
        competitionservicejoined = competitionservicejoinedRepository.save(competitionservicejoined);
        return competitionservicejoinedMapper.toDto(competitionservicejoined);
    }

    /**
     * Get all the competitionservicejoineds.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CompetitionservicejoinedDTO> findAll() {
        log.debug("Request to get all Competitionservicejoineds");
        return competitionservicejoinedRepository.findAll().stream()
            .map(competitionservicejoinedMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one competitionservicejoined by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompetitionservicejoinedDTO> findOne(Long id) {
        log.debug("Request to get Competitionservicejoined : {}", id);
        return competitionservicejoinedRepository.findById(id)
            .map(competitionservicejoinedMapper::toDto);
    }

    /**
     * Delete the competitionservicejoined by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Competitionservicejoined : {}", id);
        competitionservicejoinedRepository.deleteById(id);
    }
}
