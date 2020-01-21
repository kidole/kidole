package com.service;

import com.domain.AccreditationStep;
import com.repository.AccreditationStepRepository;
import com.service.dto.AccreditationStepDTO;
import com.service.mapper.AccreditationStepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AccreditationStep}.
 */
@Service
@Transactional
public class AccreditationStepService {

    private final Logger log = LoggerFactory.getLogger(AccreditationStepService.class);

    private final AccreditationStepRepository accreditationStepRepository;

    private final AccreditationStepMapper accreditationStepMapper;

    public AccreditationStepService(AccreditationStepRepository accreditationStepRepository, AccreditationStepMapper accreditationStepMapper) {
        this.accreditationStepRepository = accreditationStepRepository;
        this.accreditationStepMapper = accreditationStepMapper;
    }

    /**
     * Save a accreditationStep.
     *
     * @param accreditationStepDTO the entity to save.
     * @return the persisted entity.
     */
    public AccreditationStepDTO save(AccreditationStepDTO accreditationStepDTO) {
        log.debug("Request to save AccreditationStep : {}", accreditationStepDTO);
        AccreditationStep accreditationStep = accreditationStepMapper.toEntity(accreditationStepDTO);
        accreditationStep = accreditationStepRepository.save(accreditationStep);
        return accreditationStepMapper.toDto(accreditationStep);
    }

    /**
     * Get all the accreditationSteps.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AccreditationStepDTO> findAll() {
        log.debug("Request to get all AccreditationSteps");
        return accreditationStepRepository.findAll().stream()
            .map(accreditationStepMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one accreditationStep by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
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
    public void delete(Long id) {
        log.debug("Request to delete AccreditationStep : {}", id);
        accreditationStepRepository.deleteById(id);
    }
}
