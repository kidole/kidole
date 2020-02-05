package com.kidole.sport.service.impl;

import com.kidole.sport.service.FormatService;
import com.kidole.sport.domain.Format;
import com.kidole.sport.repository.FormatRepository;
import com.kidole.sport.repository.search.FormatSearchRepository;
import com.kidole.sport.service.dto.FormatDTO;
import com.kidole.sport.service.mapper.FormatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Format}.
 */
@Service
@Transactional
public class FormatServiceImpl implements FormatService {

    private final Logger log = LoggerFactory.getLogger(FormatServiceImpl.class);

    private final FormatRepository formatRepository;

    private final FormatMapper formatMapper;

    private final FormatSearchRepository formatSearchRepository;

    public FormatServiceImpl(FormatRepository formatRepository, FormatMapper formatMapper, FormatSearchRepository formatSearchRepository) {
        this.formatRepository = formatRepository;
        this.formatMapper = formatMapper;
        this.formatSearchRepository = formatSearchRepository;
    }

    /**
     * Save a format.
     *
     * @param formatDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FormatDTO save(FormatDTO formatDTO) {
        log.debug("Request to save Format : {}", formatDTO);
        Format format = formatMapper.toEntity(formatDTO);
        format = formatRepository.save(format);
        FormatDTO result = formatMapper.toDto(format);
        formatSearchRepository.save(format);
        return result;
    }

    /**
     * Get all the formats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Formats");
        return formatRepository.findAll(pageable)
            .map(formatMapper::toDto);
    }


    /**
     * Get one format by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FormatDTO> findOne(Long id) {
        log.debug("Request to get Format : {}", id);
        return formatRepository.findById(id)
            .map(formatMapper::toDto);
    }

    /**
     * Delete the format by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Format : {}", id);
        formatRepository.deleteById(id);
        formatSearchRepository.deleteById(id);
    }

    /**
     * Search for the format corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormatDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Formats for query {}", query);
        return formatSearchRepository.search(queryStringQuery(query), pageable)
            .map(formatMapper::toDto);
    }
}
