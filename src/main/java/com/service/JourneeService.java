package com.service;

import com.domain.Journee;
import com.repository.JourneeRepository;
import com.service.dto.JourneeDTO;
import com.service.mapper.JourneeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Journee}.
 */
@Service
@Transactional
public class JourneeService {

    private final Logger log = LoggerFactory.getLogger(JourneeService.class);

    private final JourneeRepository journeeRepository;

    private final JourneeMapper journeeMapper;

    public JourneeService(JourneeRepository journeeRepository, JourneeMapper journeeMapper) {
        this.journeeRepository = journeeRepository;
        this.journeeMapper = journeeMapper;
    }

    /**
     * Save a journee.
     *
     * @param journeeDTO the entity to save.
     * @return the persisted entity.
     */
    public JourneeDTO save(JourneeDTO journeeDTO) {
        log.debug("Request to save Journee : {}", journeeDTO);
        Journee journee = journeeMapper.toEntity(journeeDTO);
        journee = journeeRepository.save(journee);
        return journeeMapper.toDto(journee);
    }

    /**
     * Get all the journees.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<JourneeDTO> findAll() {
        log.debug("Request to get all Journees");
        return journeeRepository.findAll().stream()
            .map(journeeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one journee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
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
    public void delete(Long id) {
        log.debug("Request to delete Journee : {}", id);
        journeeRepository.deleteById(id);
    }
}
