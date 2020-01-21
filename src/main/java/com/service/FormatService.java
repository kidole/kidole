package com.service;

import com.domain.Format;
import com.repository.FormatRepository;
import com.service.dto.FormatDTO;
import com.service.mapper.FormatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Format}.
 */
@Service
@Transactional
public class FormatService {

    private final Logger log = LoggerFactory.getLogger(FormatService.class);

    private final FormatRepository formatRepository;

    private final FormatMapper formatMapper;

    public FormatService(FormatRepository formatRepository, FormatMapper formatMapper) {
        this.formatRepository = formatRepository;
        this.formatMapper = formatMapper;
    }

    /**
     * Save a format.
     *
     * @param formatDTO the entity to save.
     * @return the persisted entity.
     */
    public FormatDTO save(FormatDTO formatDTO) {
        log.debug("Request to save Format : {}", formatDTO);
        Format format = formatMapper.toEntity(formatDTO);
        format = formatRepository.save(format);
        return formatMapper.toDto(format);
    }

    /**
     * Get all the formats.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FormatDTO> findAll() {
        log.debug("Request to get all Formats");
        return formatRepository.findAll().stream()
            .map(formatMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one format by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
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
    public void delete(Long id) {
        log.debug("Request to delete Format : {}", id);
        formatRepository.deleteById(id);
    }
}
