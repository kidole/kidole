package com.kidole.sport.service.impl;

import com.kidole.sport.service.ConfrontationService;
import com.kidole.sport.domain.Confrontation;
import com.kidole.sport.repository.ConfrontationRepository;
import com.kidole.sport.repository.search.ConfrontationSearchRepository;
import com.kidole.sport.service.dto.ConfrontationDTO;
import com.kidole.sport.service.mapper.ConfrontationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Confrontation}.
 */
@Service
@Transactional
public class ConfrontationServiceImpl implements ConfrontationService {

    private final Logger log = LoggerFactory.getLogger(ConfrontationServiceImpl.class);

    private final ConfrontationRepository confrontationRepository;

    private final ConfrontationMapper confrontationMapper;

    private final ConfrontationSearchRepository confrontationSearchRepository;

    public ConfrontationServiceImpl(ConfrontationRepository confrontationRepository, ConfrontationMapper confrontationMapper, ConfrontationSearchRepository confrontationSearchRepository) {
        this.confrontationRepository = confrontationRepository;
        this.confrontationMapper = confrontationMapper;
        this.confrontationSearchRepository = confrontationSearchRepository;
    }

    /**
     * Save a confrontation.
     *
     * @param confrontationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ConfrontationDTO save(ConfrontationDTO confrontationDTO) {
        log.debug("Request to save Confrontation : {}", confrontationDTO);
        Confrontation confrontation = confrontationMapper.toEntity(confrontationDTO);
        confrontation = confrontationRepository.save(confrontation);
        ConfrontationDTO result = confrontationMapper.toDto(confrontation);
        confrontationSearchRepository.save(confrontation);
        return result;
    }

    /**
     * Get all the confrontations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConfrontationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Confrontations");
        return confrontationRepository.findAll(pageable)
            .map(confrontationMapper::toDto);
    }


    /**
     * Get one confrontation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConfrontationDTO> findOne(Long id) {
        log.debug("Request to get Confrontation : {}", id);
        return confrontationRepository.findById(id)
            .map(confrontationMapper::toDto);
    }

    /**
     * Delete the confrontation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Confrontation : {}", id);
        confrontationRepository.deleteById(id);
        confrontationSearchRepository.deleteById(id);
    }

    /**
     * Search for the confrontation corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConfrontationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Confrontations for query {}", query);
        return confrontationSearchRepository.search(queryStringQuery(query), pageable)
            .map(confrontationMapper::toDto);
    }
}
