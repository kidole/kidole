package com.service;

import com.domain.MatchSheet;
import com.repository.MatchSheetRepository;
import com.service.dto.MatchSheetDTO;
import com.service.mapper.MatchSheetMapper;
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
 * Service Implementation for managing {@link MatchSheet}.
 */
@Service
@Transactional
public class MatchSheetService {

    private final Logger log = LoggerFactory.getLogger(MatchSheetService.class);

    private final MatchSheetRepository matchSheetRepository;

    private final MatchSheetMapper matchSheetMapper;

    public MatchSheetService(MatchSheetRepository matchSheetRepository, MatchSheetMapper matchSheetMapper) {
        this.matchSheetRepository = matchSheetRepository;
        this.matchSheetMapper = matchSheetMapper;
    }

    /**
     * Save a matchSheet.
     *
     * @param matchSheetDTO the entity to save.
     * @return the persisted entity.
     */
    public MatchSheetDTO save(MatchSheetDTO matchSheetDTO) {
        log.debug("Request to save MatchSheet : {}", matchSheetDTO);
        MatchSheet matchSheet = matchSheetMapper.toEntity(matchSheetDTO);
        matchSheet = matchSheetRepository.save(matchSheet);
        return matchSheetMapper.toDto(matchSheet);
    }

    /**
     * Get all the matchSheets.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MatchSheetDTO> findAll() {
        log.debug("Request to get all MatchSheets");
        return matchSheetRepository.findAll().stream()
            .map(matchSheetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the matchSheets where Confrontation is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<MatchSheetDTO> findAllWhereConfrontationIsNull() {
        log.debug("Request to get all matchSheets where Confrontation is null");
        return StreamSupport
            .stream(matchSheetRepository.findAll().spliterator(), false)
            .filter(matchSheet -> matchSheet.getConfrontation() == null)
            .map(matchSheetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one matchSheet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
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
    public void delete(Long id) {
        log.debug("Request to delete MatchSheet : {}", id);
        matchSheetRepository.deleteById(id);
    }
}
