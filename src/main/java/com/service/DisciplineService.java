package com.service;

import com.domain.Discipline;
import com.repository.DisciplineRepository;
import com.service.dto.DisciplineDTO;
import com.service.mapper.DisciplineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Discipline}.
 */
@Service
@Transactional
public class DisciplineService {

    private final Logger log = LoggerFactory.getLogger(DisciplineService.class);

    private final DisciplineRepository disciplineRepository;

    private final DisciplineMapper disciplineMapper;

    public DisciplineService(DisciplineRepository disciplineRepository, DisciplineMapper disciplineMapper) {
        this.disciplineRepository = disciplineRepository;
        this.disciplineMapper = disciplineMapper;
    }

    /**
     * Save a discipline.
     *
     * @param disciplineDTO the entity to save.
     * @return the persisted entity.
     */
    public DisciplineDTO save(DisciplineDTO disciplineDTO) {
        log.debug("Request to save Discipline : {}", disciplineDTO);
        Discipline discipline = disciplineMapper.toEntity(disciplineDTO);
        discipline = disciplineRepository.save(discipline);
        return disciplineMapper.toDto(discipline);
    }

    /**
     * Get all the disciplines.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DisciplineDTO> findAll() {
        log.debug("Request to get all Disciplines");
        return disciplineRepository.findAll().stream()
            .map(disciplineMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one discipline by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DisciplineDTO> findOne(Long id) {
        log.debug("Request to get Discipline : {}", id);
        return disciplineRepository.findById(id)
            .map(disciplineMapper::toDto);
    }

    /**
     * Delete the discipline by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Discipline : {}", id);
        disciplineRepository.deleteById(id);
    }
}
