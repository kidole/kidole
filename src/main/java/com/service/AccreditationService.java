package com.service;

import com.domain.Accreditation;
import com.repository.AccreditationRepository;
import com.service.dto.AccreditationDTO;
import com.service.mapper.AccreditationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Accreditation}.
 */
@Service
@Transactional
public class AccreditationService {

    private final Logger log = LoggerFactory.getLogger(AccreditationService.class);

    private final AccreditationRepository accreditationRepository;

    private final AccreditationMapper accreditationMapper;

    public AccreditationService(AccreditationRepository accreditationRepository, AccreditationMapper accreditationMapper) {
        this.accreditationRepository = accreditationRepository;
        this.accreditationMapper = accreditationMapper;
    }

    /**
     * Save a accreditation.
     *
     * @param accreditationDTO the entity to save.
     * @return the persisted entity.
     */
    public AccreditationDTO save(AccreditationDTO accreditationDTO) {
        log.debug("Request to save Accreditation : {}", accreditationDTO);
        Accreditation accreditation = accreditationMapper.toEntity(accreditationDTO);
        accreditation = accreditationRepository.save(accreditation);
        return accreditationMapper.toDto(accreditation);
    }

    /**
     * Get all the accreditations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AccreditationDTO> findAll() {
        log.debug("Request to get all Accreditations");
        return accreditationRepository.findAll().stream()
            .map(accreditationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one accreditation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccreditationDTO> findOne(Long id) {
        log.debug("Request to get Accreditation : {}", id);
        return accreditationRepository.findById(id)
            .map(accreditationMapper::toDto);
    }

    /**
     * Delete the accreditation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Accreditation : {}", id);
        accreditationRepository.deleteById(id);
    }
}
