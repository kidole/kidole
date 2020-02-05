package com.kidole.sport.service.impl;

import com.kidole.sport.service.PoulesService;
import com.kidole.sport.domain.Poules;
import com.kidole.sport.repository.PoulesRepository;
import com.kidole.sport.repository.search.PoulesSearchRepository;
import com.kidole.sport.service.dto.PoulesDTO;
import com.kidole.sport.service.mapper.PoulesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Poules}.
 */
@Service
@Transactional
public class PoulesServiceImpl implements PoulesService {

    private final Logger log = LoggerFactory.getLogger(PoulesServiceImpl.class);

    private final PoulesRepository poulesRepository;

    private final PoulesMapper poulesMapper;

    private final PoulesSearchRepository poulesSearchRepository;

    public PoulesServiceImpl(PoulesRepository poulesRepository, PoulesMapper poulesMapper, PoulesSearchRepository poulesSearchRepository) {
        this.poulesRepository = poulesRepository;
        this.poulesMapper = poulesMapper;
        this.poulesSearchRepository = poulesSearchRepository;
    }

    /**
     * Save a poules.
     *
     * @param poulesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PoulesDTO save(PoulesDTO poulesDTO) {
        log.debug("Request to save Poules : {}", poulesDTO);
        Poules poules = poulesMapper.toEntity(poulesDTO);
        poules = poulesRepository.save(poules);
        PoulesDTO result = poulesMapper.toDto(poules);
        poulesSearchRepository.save(poules);
        return result;
    }

    /**
     * Get all the poules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PoulesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Poules");
        return poulesRepository.findAll(pageable)
            .map(poulesMapper::toDto);
    }


    /**
     * Get one poules by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PoulesDTO> findOne(Long id) {
        log.debug("Request to get Poules : {}", id);
        return poulesRepository.findById(id)
            .map(poulesMapper::toDto);
    }

    /**
     * Delete the poules by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Poules : {}", id);
        poulesRepository.deleteById(id);
        poulesSearchRepository.deleteById(id);
    }

    /**
     * Search for the poules corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PoulesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Poules for query {}", query);
        return poulesSearchRepository.search(queryStringQuery(query), pageable)
            .map(poulesMapper::toDto);
    }
}
