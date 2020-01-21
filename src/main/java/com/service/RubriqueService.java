package com.service;

import com.domain.Rubrique;
import com.repository.RubriqueRepository;
import com.service.dto.RubriqueDTO;
import com.service.mapper.RubriqueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Rubrique}.
 */
@Service
@Transactional
public class RubriqueService {

    private final Logger log = LoggerFactory.getLogger(RubriqueService.class);

    private final RubriqueRepository rubriqueRepository;

    private final RubriqueMapper rubriqueMapper;

    public RubriqueService(RubriqueRepository rubriqueRepository, RubriqueMapper rubriqueMapper) {
        this.rubriqueRepository = rubriqueRepository;
        this.rubriqueMapper = rubriqueMapper;
    }

    /**
     * Save a rubrique.
     *
     * @param rubriqueDTO the entity to save.
     * @return the persisted entity.
     */
    public RubriqueDTO save(RubriqueDTO rubriqueDTO) {
        log.debug("Request to save Rubrique : {}", rubriqueDTO);
        Rubrique rubrique = rubriqueMapper.toEntity(rubriqueDTO);
        rubrique = rubriqueRepository.save(rubrique);
        return rubriqueMapper.toDto(rubrique);
    }

    /**
     * Get all the rubriques.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RubriqueDTO> findAll() {
        log.debug("Request to get all Rubriques");
        return rubriqueRepository.findAll().stream()
            .map(rubriqueMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the rubriques where PrestationService is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<RubriqueDTO> findAllWherePrestationServiceIsNull() {
        log.debug("Request to get all rubriques where PrestationService is null");
        return StreamSupport
            .stream(rubriqueRepository.findAll().spliterator(), false)
            .filter(rubrique -> rubrique.getPrestationService() == null)
            .map(rubriqueMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
    *  Get all the rubriques where CompetitionServicesOffer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<RubriqueDTO> findAllWhereCompetitionServicesOfferIsNull() {
        log.debug("Request to get all rubriques where CompetitionServicesOffer is null");
        return StreamSupport
            .stream(rubriqueRepository.findAll().spliterator(), false)
            .filter(rubrique -> rubrique.getCompetitionServicesOffer() == null)
            .map(rubriqueMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one rubrique by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RubriqueDTO> findOne(Long id) {
        log.debug("Request to get Rubrique : {}", id);
        return rubriqueRepository.findById(id)
            .map(rubriqueMapper::toDto);
    }

    /**
     * Delete the rubrique by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Rubrique : {}", id);
        rubriqueRepository.deleteById(id);
    }
}
