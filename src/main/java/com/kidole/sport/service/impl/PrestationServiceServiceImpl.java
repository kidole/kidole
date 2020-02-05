package com.kidole.sport.service.impl;

import com.kidole.sport.service.PrestationServiceService;
import com.kidole.sport.domain.PrestationService;
import com.kidole.sport.repository.PrestationServiceRepository;
import com.kidole.sport.repository.search.PrestationServiceSearchRepository;
import com.kidole.sport.service.dto.PrestationServiceDTO;
import com.kidole.sport.service.mapper.PrestationServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PrestationService}.
 */
@Service
@Transactional
public class PrestationServiceServiceImpl implements PrestationServiceService {

    private final Logger log = LoggerFactory.getLogger(PrestationServiceServiceImpl.class);

    private final PrestationServiceRepository prestationServiceRepository;

    private final PrestationServiceMapper prestationServiceMapper;

    private final PrestationServiceSearchRepository prestationServiceSearchRepository;

    public PrestationServiceServiceImpl(PrestationServiceRepository prestationServiceRepository, PrestationServiceMapper prestationServiceMapper, PrestationServiceSearchRepository prestationServiceSearchRepository) {
        this.prestationServiceRepository = prestationServiceRepository;
        this.prestationServiceMapper = prestationServiceMapper;
        this.prestationServiceSearchRepository = prestationServiceSearchRepository;
    }

    /**
     * Save a prestationService.
     *
     * @param prestationServiceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PrestationServiceDTO save(PrestationServiceDTO prestationServiceDTO) {
        log.debug("Request to save PrestationService : {}", prestationServiceDTO);
        PrestationService prestationService = prestationServiceMapper.toEntity(prestationServiceDTO);
        prestationService = prestationServiceRepository.save(prestationService);
        PrestationServiceDTO result = prestationServiceMapper.toDto(prestationService);
        prestationServiceSearchRepository.save(prestationService);
        return result;
    }

    /**
     * Get all the prestationServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrestationServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrestationServices");
        return prestationServiceRepository.findAll(pageable)
            .map(prestationServiceMapper::toDto);
    }


    /**
     * Get one prestationService by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
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
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PrestationService : {}", id);
        prestationServiceRepository.deleteById(id);
        prestationServiceSearchRepository.deleteById(id);
    }

    /**
     * Search for the prestationService corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PrestationServiceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PrestationServices for query {}", query);
        return prestationServiceSearchRepository.search(queryStringQuery(query), pageable)
            .map(prestationServiceMapper::toDto);
    }
}
