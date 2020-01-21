package com.service;

import com.domain.DelegationMembers;
import com.repository.DelegationMembersRepository;
import com.service.dto.DelegationMembersDTO;
import com.service.mapper.DelegationMembersMapper;
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
 * Service Implementation for managing {@link DelegationMembers}.
 */
@Service
@Transactional
public class DelegationMembersService {

    private final Logger log = LoggerFactory.getLogger(DelegationMembersService.class);

    private final DelegationMembersRepository delegationMembersRepository;

    private final DelegationMembersMapper delegationMembersMapper;

    public DelegationMembersService(DelegationMembersRepository delegationMembersRepository, DelegationMembersMapper delegationMembersMapper) {
        this.delegationMembersRepository = delegationMembersRepository;
        this.delegationMembersMapper = delegationMembersMapper;
    }

    /**
     * Save a delegationMembers.
     *
     * @param delegationMembersDTO the entity to save.
     * @return the persisted entity.
     */
    public DelegationMembersDTO save(DelegationMembersDTO delegationMembersDTO) {
        log.debug("Request to save DelegationMembers : {}", delegationMembersDTO);
        DelegationMembers delegationMembers = delegationMembersMapper.toEntity(delegationMembersDTO);
        delegationMembers = delegationMembersRepository.save(delegationMembers);
        return delegationMembersMapper.toDto(delegationMembers);
    }

    /**
     * Get all the delegationMembers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DelegationMembersDTO> findAll() {
        log.debug("Request to get all DelegationMembers");
        return delegationMembersRepository.findAll().stream()
            .map(delegationMembersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the delegationMembers where Delegation is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<DelegationMembersDTO> findAllWhereDelegationIsNull() {
        log.debug("Request to get all delegationMembers where Delegation is null");
        return StreamSupport
            .stream(delegationMembersRepository.findAll().spliterator(), false)
            .filter(delegationMembers -> delegationMembers.getDelegation() == null)
            .map(delegationMembersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one delegationMembers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
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
    public void delete(Long id) {
        log.debug("Request to delete DelegationMembers : {}", id);
        delegationMembersRepository.deleteById(id);
    }
}
