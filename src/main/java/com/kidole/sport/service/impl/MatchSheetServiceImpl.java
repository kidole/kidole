package com.kidole.sport.service.impl;

import com.kidole.sport.service.MatchSheetService;
import com.kidole.sport.domain.MatchSheet;
import com.kidole.sport.repository.MatchSheetRepository;
import com.kidole.sport.repository.search.MatchSheetSearchRepository;
import com.kidole.sport.service.dto.MatchSheetDTO;
import com.kidole.sport.service.mapper.MatchSheetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MatchSheet}.
 */
@Service
@Transactional
public class MatchSheetServiceImpl implements MatchSheetService {

    private final Logger log = LoggerFactory.getLogger(MatchSheetServiceImpl.class);

    private final MatchSheetRepository matchSheetRepository;

    private final MatchSheetMapper matchSheetMapper;

    private final MatchSheetSearchRepository matchSheetSearchRepository;

    public MatchSheetServiceImpl(MatchSheetRepository matchSheetRepository, MatchSheetMapper matchSheetMapper, MatchSheetSearchRepository matchSheetSearchRepository) {
        this.matchSheetRepository = matchSheetRepository;
        this.matchSheetMapper = matchSheetMapper;
        this.matchSheetSearchRepository = matchSheetSearchRepository;
    }

    /**
     * Save a matchSheet.
     *
     * @param matchSheetDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MatchSheetDTO save(MatchSheetDTO matchSheetDTO) {
        log.debug("Request to save MatchSheet : {}", matchSheetDTO);
        MatchSheet matchSheet = matchSheetMapper.toEntity(matchSheetDTO);
        matchSheet = matchSheetRepository.save(matchSheet);
        MatchSheetDTO result = matchSheetMapper.toDto(matchSheet);
        matchSheetSearchRepository.save(matchSheet);
        return result;
    }

    /**
     * Get all the matchSheets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MatchSheetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MatchSheets");
        return matchSheetRepository.findAll(pageable)
            .map(matchSheetMapper::toDto);
    }


    /**
     * Get one matchSheet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MatchSheetDTO> findOne(Long id) {
        log.debug("Request to get MatchSheet : {}", id);
        return matchSheetRepository.findById(id)
            .map(matchSheetMapper::toDto);
    }

    /**
     * Delete the matchSheet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MatchSheet : {}", id);
        matchSheetRepository.deleteById(id);
        matchSheetSearchRepository.deleteById(id);
    }

    /**
     * Search for the matchSheet corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MatchSheetDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MatchSheets for query {}", query);
        return matchSheetSearchRepository.search(queryStringQuery(query), pageable)
            .map(matchSheetMapper::toDto);
    }
}
