package com.kidole.sport.service.impl;

import com.kidole.sport.service.DisciplineService;
import com.kidole.sport.domain.Discipline;
import com.kidole.sport.repository.DisciplineRepository;
import com.kidole.sport.repository.search.DisciplineSearchRepository;
import com.kidole.sport.service.dto.DisciplineDTO;
import com.kidole.sport.service.mapper.DisciplineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Discipline}.
 */
@Service
@Transactional
public class DisciplineServiceImpl implements DisciplineService {

    private final Logger log = LoggerFactory.getLogger(DisciplineServiceImpl.class);

    private final DisciplineRepository disciplineRepository;

    private final DisciplineMapper disciplineMapper;

    private final DisciplineSearchRepository disciplineSearchRepository;

    public DisciplineServiceImpl(DisciplineRepository disciplineRepository, DisciplineMapper disciplineMapper, DisciplineSearchRepository disciplineSearchRepository) {
        this.disciplineRepository = disciplineRepository;
        this.disciplineMapper = disciplineMapper;
        this.disciplineSearchRepository = disciplineSearchRepository;
    }

    /**
     * Save a discipline.
     *
     * @param disciplineDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DisciplineDTO save(DisciplineDTO disciplineDTO) {
        log.debug("Request to save Discipline : {}", disciplineDTO);
        Discipline discipline = disciplineMapper.toEntity(disciplineDTO);
        discipline = disciplineRepository.save(discipline);
        DisciplineDTO result = disciplineMapper.toDto(discipline);
        disciplineSearchRepository.save(discipline);
        return result;
    }

    /**
     * Get all the disciplines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DisciplineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Disciplines");
        return disciplineRepository.findAll(pageable)
            .map(disciplineMapper::toDto);
    }


    /**
     * Get one discipline by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
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
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Discipline : {}", id);
        disciplineRepository.deleteById(id);
        disciplineSearchRepository.deleteById(id);
    }

    /**
     * Search for the discipline corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DisciplineDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Disciplines for query {}", query);
        return disciplineSearchRepository.search(queryStringQuery(query), pageable)
            .map(disciplineMapper::toDto);
    }
}
