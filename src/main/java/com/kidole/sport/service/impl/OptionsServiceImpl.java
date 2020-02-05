package com.kidole.sport.service.impl;

import com.kidole.sport.service.OptionsService;
import com.kidole.sport.domain.Options;
import com.kidole.sport.repository.OptionsRepository;
import com.kidole.sport.repository.search.OptionsSearchRepository;
import com.kidole.sport.service.dto.OptionsDTO;
import com.kidole.sport.service.mapper.OptionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Options}.
 */
@Service
@Transactional
public class OptionsServiceImpl implements OptionsService {

    private final Logger log = LoggerFactory.getLogger(OptionsServiceImpl.class);

    private final OptionsRepository optionsRepository;

    private final OptionsMapper optionsMapper;

    private final OptionsSearchRepository optionsSearchRepository;

    public OptionsServiceImpl(OptionsRepository optionsRepository, OptionsMapper optionsMapper, OptionsSearchRepository optionsSearchRepository) {
        this.optionsRepository = optionsRepository;
        this.optionsMapper = optionsMapper;
        this.optionsSearchRepository = optionsSearchRepository;
    }

    /**
     * Save a options.
     *
     * @param optionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OptionsDTO save(OptionsDTO optionsDTO) {
        log.debug("Request to save Options : {}", optionsDTO);
        Options options = optionsMapper.toEntity(optionsDTO);
        options = optionsRepository.save(options);
        OptionsDTO result = optionsMapper.toDto(options);
        optionsSearchRepository.save(options);
        return result;
    }

    /**
     * Get all the options.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OptionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Options");
        return optionsRepository.findAll(pageable)
            .map(optionsMapper::toDto);
    }


    /**
     * Get one options by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OptionsDTO> findOne(Long id) {
        log.debug("Request to get Options : {}", id);
        return optionsRepository.findById(id)
            .map(optionsMapper::toDto);
    }

    /**
     * Delete the options by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Options : {}", id);
        optionsRepository.deleteById(id);
        optionsSearchRepository.deleteById(id);
    }

    /**
     * Search for the options corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OptionsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Options for query {}", query);
        return optionsSearchRepository.search(queryStringQuery(query), pageable)
            .map(optionsMapper::toDto);
    }
}
