package com.kidole.sport.service.impl;

import com.kidole.sport.service.AccreditationService;
import com.kidole.sport.domain.Accreditation;
import com.kidole.sport.repository.AccreditationRepository;
import com.kidole.sport.repository.search.AccreditationSearchRepository;
import com.kidole.sport.service.dto.AccreditationDTO;
import com.kidole.sport.service.mapper.AccreditationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Accreditation}.
 */
@Service
@Transactional
public class AccreditationServiceImpl implements AccreditationService {

    private final Logger log = LoggerFactory.getLogger(AccreditationServiceImpl.class);

    private final AccreditationRepository accreditationRepository;

    private final AccreditationMapper accreditationMapper;

    private final AccreditationSearchRepository accreditationSearchRepository;

    public AccreditationServiceImpl(AccreditationRepository accreditationRepository, AccreditationMapper accreditationMapper, AccreditationSearchRepository accreditationSearchRepository) {
        this.accreditationRepository = accreditationRepository;
        this.accreditationMapper = accreditationMapper;
        this.accreditationSearchRepository = accreditationSearchRepository;
    }

    /**
     * Save a accreditation.
     *
     * @param accreditationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AccreditationDTO save(AccreditationDTO accreditationDTO) {
        log.debug("Request to save Accreditation : {}", accreditationDTO);
        Accreditation accreditation = accreditationMapper.toEntity(accreditationDTO);
        accreditation = accreditationRepository.save(accreditation);
        AccreditationDTO result = accreditationMapper.toDto(accreditation);
        accreditationSearchRepository.save(accreditation);
        return result;
    }

    /**
     * Get all the accreditations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccreditationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Accreditations");
        return accreditationRepository.findAll(pageable)
            .map(accreditationMapper::toDto);
    }


    /**
     * Get one accreditation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
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
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Accreditation : {}", id);
        accreditationRepository.deleteById(id);
        accreditationSearchRepository.deleteById(id);
    }

    /**
     * Search for the accreditation corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AccreditationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Accreditations for query {}", query);
        return accreditationSearchRepository.search(queryStringQuery(query), pageable)
            .map(accreditationMapper::toDto);
    }
}
