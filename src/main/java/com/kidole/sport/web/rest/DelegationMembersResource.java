package com.kidole.sport.web.rest;

import com.kidole.sport.service.DelegationMembersService;
import com.kidole.sport.web.rest.errors.BadRequestAlertException;
import com.kidole.sport.service.dto.DelegationMembersDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.kidole.sport.domain.DelegationMembers}.
 */
@RestController
@RequestMapping("/api")
public class DelegationMembersResource {

    private final Logger log = LoggerFactory.getLogger(DelegationMembersResource.class);

    private static final String ENTITY_NAME = "delegationMembers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DelegationMembersService delegationMembersService;

    public DelegationMembersResource(DelegationMembersService delegationMembersService) {
        this.delegationMembersService = delegationMembersService;
    }

    /**
     * {@code POST  /delegation-members} : Create a new delegationMembers.
     *
     * @param delegationMembersDTO the delegationMembersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new delegationMembersDTO, or with status {@code 400 (Bad Request)} if the delegationMembers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delegation-members")
    public ResponseEntity<DelegationMembersDTO> createDelegationMembers(@Valid @RequestBody DelegationMembersDTO delegationMembersDTO) throws URISyntaxException {
        log.debug("REST request to save DelegationMembers : {}", delegationMembersDTO);
        if (delegationMembersDTO.getId() != null) {
            throw new BadRequestAlertException("A new delegationMembers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DelegationMembersDTO result = delegationMembersService.save(delegationMembersDTO);
        return ResponseEntity.created(new URI("/api/delegation-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delegation-members} : Updates an existing delegationMembers.
     *
     * @param delegationMembersDTO the delegationMembersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated delegationMembersDTO,
     * or with status {@code 400 (Bad Request)} if the delegationMembersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the delegationMembersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delegation-members")
    public ResponseEntity<DelegationMembersDTO> updateDelegationMembers(@Valid @RequestBody DelegationMembersDTO delegationMembersDTO) throws URISyntaxException {
        log.debug("REST request to update DelegationMembers : {}", delegationMembersDTO);
        if (delegationMembersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DelegationMembersDTO result = delegationMembersService.save(delegationMembersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, delegationMembersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /delegation-members} : get all the delegationMembers.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of delegationMembers in body.
     */
    @GetMapping("/delegation-members")
    public ResponseEntity<List<DelegationMembersDTO>> getAllDelegationMembers(Pageable pageable) {
        log.debug("REST request to get a page of DelegationMembers");
        Page<DelegationMembersDTO> page = delegationMembersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /delegation-members/:id} : get the "id" delegationMembers.
     *
     * @param id the id of the delegationMembersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the delegationMembersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delegation-members/{id}")
    public ResponseEntity<DelegationMembersDTO> getDelegationMembers(@PathVariable Long id) {
        log.debug("REST request to get DelegationMembers : {}", id);
        Optional<DelegationMembersDTO> delegationMembersDTO = delegationMembersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(delegationMembersDTO);
    }

    /**
     * {@code DELETE  /delegation-members/:id} : delete the "id" delegationMembers.
     *
     * @param id the id of the delegationMembersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delegation-members/{id}")
    public ResponseEntity<Void> deleteDelegationMembers(@PathVariable Long id) {
        log.debug("REST request to delete DelegationMembers : {}", id);
        delegationMembersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/delegation-members?query=:query} : search for the delegationMembers corresponding
     * to the query.
     *
     * @param query the query of the delegationMembers search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/delegation-members")
    public ResponseEntity<List<DelegationMembersDTO>> searchDelegationMembers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DelegationMembers for query {}", query);
        Page<DelegationMembersDTO> page = delegationMembersService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
