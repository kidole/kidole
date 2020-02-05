package com.kidole.sport.service.impl;

import com.kidole.sport.service.CompetitionService;
import com.kidole.sport.domain.Competition;
import com.kidole.sport.repository.CompetitionRepository;
import com.kidole.sport.repository.search.CompetitionSearchRepository;
import com.kidole.sport.service.dto.CompetitionDTO;
import com.kidole.sport.service.mapper.CompetitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Competition}.
 */
@Service
@Transactional
public class CompetitionServiceImpl implements CompetitionService {

    private final Logger log = LoggerFactory.getLogger(CompetitionServiceImpl.class);

    private final CompetitionRepository competitionRepository;

    private final CompetitionMapper competitionMapper;

    private final CompetitionSearchRepository competitionSearchRepository;

    public CompetitionServiceImpl(CompetitionRepository competitionRepository, CompetitionMapper competitionMapper, CompetitionSearchRepository competitionSearchRepository) {
        this.competitionRepository = competitionRepository;
        this.competitionMapper = competitionMapper;
        this.competitionSearchRepository = competitionSearchRepository;
    }

    /**
     * Save a competition.
     *
     * @param competitionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CompetitionDTO save(CompetitionDTO competitionDTO) {
        log.debug("Request to save Competition : {}", competitionDTO);
        Competition competition = competitionMapper.toEntity(competitionDTO);
        competition = competitionRepository.save(competition);
        CompetitionDTO result = competitionMapper.toDto(competition);
        competitionSearchRepository.save(competition);
        return result;
    }

    /**
     * Get all the competitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompetitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Competitions");
        return competitionRepository.findAll(pageable)
            .map(competitionMapper::toDto);
    }


    /**
     * Get one competition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompetitionDTO> findOne(Long id) {
        log.debug("Request to get Competition : {}", id);
        return competitionRepository.findById(id)
            .map(competitionMapper::toDto);
    }

    /**
     * Delete the competition by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Competition : {}", id);
        competitionRepository.deleteById(id);
        competitionSearchRepository.deleteById(id);
    }

    /**
     * Search for the competition corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompetitionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Competitions for query {}", query);
        return competitionSearchRepository.search(queryStringQuery(query), pageable)
            .map(competitionMapper::toDto);
    }
}
