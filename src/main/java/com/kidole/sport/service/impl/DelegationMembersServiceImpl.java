package com.kidole.sport.service.impl;

import com.kidole.sport.service.DelegationMembersService;
import com.kidole.sport.domain.DelegationMembers;
import com.kidole.sport.repository.DelegationMembersRepository;
import com.kidole.sport.repository.search.DelegationMembersSearchRepository;
import com.kidole.sport.service.dto.DelegationMembersDTO;
import com.kidole.sport.service.mapper.DelegationMembersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link DelegationMembers}.
 */
@Service
@Transactional
public class DelegationMembersServiceImpl implements DelegationMembersService {

    private final Logger log = LoggerFactory.getLogger(DelegationMembersServiceImpl.class);

    private final DelegationMembersRepository delegationMembersRepository;

    private final DelegationMembersMapper delegationMembersMapper;

    private final DelegationMembersSearchRepository delegationMembersSearchRepository;

    public DelegationMembersServiceImpl(DelegationMembersRepository delegationMembersRepository, DelegationMembersMapper delegationMembersMapper, DelegationMembersSearchRepository delegationMembersSearchRepository) {
        this.delegationMembersRepository = delegationMembersRepository;
        this.delegationMembersMapper = delegationMembersMapper;
        this.delegationMembersSearchRepository = delegationMembersSearchRepository;
    }

    /**
     * Save a delegationMembers.
     *
     * @param delegationMembersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DelegationMembersDTO save(DelegationMembersDTO delegationMembersDTO) {
        log.debug("Request to save DelegationMembers : {}", delegationMembersDTO);
        DelegationMembers delegationMembers = delegationMembersMapper.toEntity(delegationMembersDTO);
        delegationMembers = delegationMembersRepository.save(delegationMembers);
        DelegationMembersDTO result = delegationMembersMapper.toDto(delegationMembers);
        delegationMembersSearchRepository.save(delegationMembers);
        return result;
    }

    /**
     * Get all the delegationMembers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DelegationMembersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DelegationMembers");
        return delegationMembersRepository.findAll(pageable)
            .map(delegationMembersMapper::toDto);
    }


    /**
     * Get one delegationMembers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DelegationMembersDTO> findOne(Long id) {
        log.debug("Request to get DelegationMembers : {}", id);
        return delegationMembersRepository.findById(id)
            .map(delegationMembersMapper::toDto);
    }

    /**
     * Delete the delegationMembers by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DelegationMembers : {}", id);
        delegationMembersRepository.deleteById(id);
        delegationMembersSearchRepository.deleteById(id);
    }

    /**
     * Search for the delegationMembers corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DelegationMembersDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DelegationMembers for query {}", query);
        return delegationMembersSearchRepository.search(queryStringQuery(query), pageable)
            .map(delegationMembersMapper::toDto);
    }
}
