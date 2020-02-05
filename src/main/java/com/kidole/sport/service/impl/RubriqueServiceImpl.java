package com.kidole.sport.service.impl;

import com.kidole.sport.service.RubriqueService;
import com.kidole.sport.domain.Rubrique;
import com.kidole.sport.repository.RubriqueRepository;
import com.kidole.sport.repository.search.RubriqueSearchRepository;
import com.kidole.sport.service.dto.RubriqueDTO;
import com.kidole.sport.service.mapper.RubriqueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Rubrique}.
 */
@Service
@Transactional
public class RubriqueServiceImpl implements RubriqueService {

    private final Logger log = LoggerFactory.getLogger(RubriqueServiceImpl.class);

    private final RubriqueRepository rubriqueRepository;

    private final RubriqueMapper rubriqueMapper;

    private final RubriqueSearchRepository rubriqueSearchRepository;

    public RubriqueServiceImpl(RubriqueRepository rubriqueRepository, RubriqueMapper rubriqueMapper, RubriqueSearchRepository rubriqueSearchRepository) {
        this.rubriqueRepository = rubriqueRepository;
        this.rubriqueMapper = rubriqueMapper;
        this.rubriqueSearchRepository = rubriqueSearchRepository;
    }

    /**
     * Save a rubrique.
     *
     * @param rubriqueDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RubriqueDTO save(RubriqueDTO rubriqueDTO) {
        log.debug("Request to save Rubrique : {}", rubriqueDTO);
        Rubrique rubrique = rubriqueMapper.toEntity(rubriqueDTO);
        rubrique = rubriqueRepository.save(rubrique);
        RubriqueDTO result = rubriqueMapper.toDto(rubrique);
        rubriqueSearchRepository.save(rubrique);
        return result;
    }

    /**
     * Get all the rubriques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RubriqueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rubriques");
        return rubriqueRepository.findAll(pageable)
            .map(rubriqueMapper::toDto);
    }


    /**
     * Get one rubrique by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RubriqueDTO> findOne(Long id) {
        log.debug("Request to get Rubrique : {}", id);
        return rubriqueRepository.findById(id)
            .map(rubriqueMapper::toDto);
    }

    /**
     * Delete the rubrique by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rubrique : {}", id);
        rubriqueRepository.deleteById(id);
        rubriqueSearchRepository.deleteById(id);
    }

    /**
     * Search for the rubrique corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RubriqueDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Rubriques for query {}", query);
        return rubriqueSearchRepository.search(queryStringQuery(query), pageable)
            .map(rubriqueMapper::toDto);
    }
}
