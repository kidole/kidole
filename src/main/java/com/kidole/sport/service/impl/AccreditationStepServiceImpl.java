package com.kidole.sport.service.impl;

import com.kidole.sport.service.AccreditationStepService;
import com.kidole.sport.domain.AccreditationStep;
import com.kidole.sport.repository.AccreditationStepRepository;
import com.kidole.sport.repository.search.AccreditationStepSearchRepository;
import com.kidole.sport.service.dto.AccreditationStepDTO;
import com.kidole.sport.service.mapper.AccreditationStepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AccreditationStep}.
 */
@Service
@Transactional
public class AccreditationStepServiceImpl implements AccreditationStepService {

    private final Logger log = LoggerFactory.getLogger(AccreditationStepServiceImpl.class);

    private final AccreditationStepRepository accreditationStepRepository;

    private final AccreditationStepMapper accreditationStepMapper;

    private final AccreditationStepSearchRepository accreditationStepSearchRepository;

    public AccreditationStepServiceImpl(AccreditationStepRepository accreditationStepRepository, AccreditationStepMapper accreditationStepMapper, AccreditationStepSearchRepository accreditationStepSearchRepository) {
        this.accreditationStepRepository = accreditationStepRepository;
        this.accreditationStepMapper = accreditationStepMapper;
        this.accreditationStepSearchRepository = accreditationStepSearchRepository;
    }

    /**
     * Save a accreditationStep.
     *
     * @param accreditationStepDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccreditationStepDTO save(AccreditationStepDTO accreditationStepDTO) {
        log.debug("Request to save AccreditationStep : {}", accreditationStepDTO);
        AccreditationStep accreditationStep = accreditationStepMapper.toEntity(accreditationStepDTO);
        accreditationStep = accreditationStepRepository.save(accreditationStep);
        AccreditationStepDTO result = accreditationStepMapper.toDto(accreditationStep);
        accreditationStepSearchRepository.save(accreditationStep);
        return result;
    }

    /**
     * Get all the accreditationSteps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccreditationStepDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccreditationSteps");
        return accreditationStepRepository.findAll(pageable)
            .map(accreditationStepMapper::toDto);
    }


    /**
     * Get one accreditationStep by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AccreditationStepDTO> findOne(Long id) {
        log.debug("Request to get AccreditationStep : {}", id);
        return accreditationStepRepository.findById(id)
            .map(accreditationStepMapper::toDto);
    }

    /**
     * Delete the accreditationStep by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccreditationStep : {}", id);
        accreditationStepRepository.deleteById(id);
        accreditationStepSearchRepository.deleteById(id);
    }

    /**
     * Search for the accreditationStep corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccreditationStepDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AccreditationSteps for query {}", query);
        return accreditationStepSearchRepository.search(queryStringQuery(query), pageable)
            .map(accreditationStepMapper::toDto);
    }
}
