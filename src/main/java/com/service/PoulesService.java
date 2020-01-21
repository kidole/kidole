package com.service;

import com.domain.Poules;
import com.repository.PoulesRepository;
import com.service.dto.PoulesDTO;
import com.service.mapper.PoulesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Poules}.
 */
@Service
@Transactional
public class PoulesService {

    private final Logger log = LoggerFactory.getLogger(PoulesService.class);

    private final PoulesRepository poulesRepository;

    private final PoulesMapper poulesMapper;

    public PoulesService(PoulesRepository poulesRepository, PoulesMapper poulesMapper) {
        this.poulesRepository = poulesRepository;
        this.poulesMapper = poulesMapper;
    }

    /**
     * Save a poules.
     *
     * @param poulesDTO the entity to save.
     * @return the persisted entity.
     */
    public PoulesDTO save(PoulesDTO poulesDTO) {
        log.debug("Request to save Poules : {}", poulesDTO);
        Poules poules = poulesMapper.toEntity(poulesDTO);
        poules = poulesRepository.save(poules);
        return poulesMapper.toDto(poules);
    }

    /**
     * Get all the poules.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PoulesDTO> findAll() {
        log.debug("Request to get all Poules");
        return poulesRepository.findAll().stream()
            .map(poulesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one poules by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PoulesDTO> findOne(Long id) {
        log.debug("Request to get Poules : {}", id);
        return poulesRepository.findById(id)
            .map(poulesMapper::toDto);
    }

    /**
     * Delete the poules by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Poules : {}", id);
        poulesRepository.deleteById(id);
    }
}
