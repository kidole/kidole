package com.kidole.sport.service.impl;

import com.kidole.sport.service.JourneeService;
import com.kidole.sport.domain.Journee;
import com.kidole.sport.repository.JourneeRepository;
import com.kidole.sport.repository.search.JourneeSearchRepository;
import com.kidole.sport.service.dto.JourneeDTO;
import com.kidole.sport.service.mapper.JourneeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Journee}.
 */
@Service
@Transactional
public class JourneeServiceImpl implements JourneeService {

    private final Logger log = LoggerFactory.getLogger(JourneeServiceImpl.class);

    private final JourneeRepository journeeRepository;

    private final JourneeMapper journeeMapper;

    private final JourneeSearchRepository journeeSearchRepository;

    public JourneeServiceImpl(JourneeRepository journeeRepository, JourneeMapper journeeMapper, JourneeSearchRepository journeeSearchRepository) {
        this.journeeRepository = journeeRepository;
        this.journeeMapper = journeeMapper;
        this.journeeSearchRepository = journeeSearchRepository;
    }

    /**
     * Save a journee.
     *
     * @param journeeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public JourneeDTO save(JourneeDTO journeeDTO) {
        log.debug("Request to save Journee : {}", journeeDTO);
        Journee journee = journeeMapper.toEntity(journeeDTO);
        journee = journeeRepository.save(journee);
        JourneeDTO result = journeeMapper.toDto(journee);
        journeeSearchRepository.save(journee);
        return result;
    }

    /**
     * Get all the journees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JourneeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Journees");
        return journeeRepository.findAll(pageable)
            .map(journeeMapper::toDto);
    }


    /**
     * Get one journee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<JourneeDTO> findOne(Long id) {
        log.debug("Request to get Journee : {}", id);
        return journeeRepository.findById(id)
            .map(journeeMapper::toDto);
    }

    /**
     * Delete the journee by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Journee : {}", id);
        journeeRepository.deleteById(id);
        journeeSearchRepository.deleteById(id);
    }

    /**
     * Search for the journee corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JourneeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Journees for query {}", query);
        return journeeSearchRepository.search(queryStringQuery(query), pageable)
            .map(journeeMapper::toDto);
    }
}
