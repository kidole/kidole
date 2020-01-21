package com.web.rest;

import com.service.DelegationService;
import com.web.rest.errors.BadRequestAlertException;
import com.service.dto.DelegationDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.domain.Delegation}.
 */
@RestController
@RequestMapping("/api")
public class DelegationResource {

    private final Logger log = LoggerFactory.getLogger(DelegationResource.class);

    private static final String ENTITY_NAME = "delegation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DelegationService delegationService;

    public DelegationResource(DelegationService delegationService) {
        this.delegationService = delegationService;
    }

    /**
     * {@code POST  /delegations} : Create a new delegation.
     *
     * @param delegationDTO the delegationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new delegationDTO, or with status {@code 400 (Bad Request)} if the delegation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delegations")
    public ResponseEntity<DelegationDTO> createDelegation(@RequestBody DelegationDTO delegationDTO) throws URISyntaxException {
        log.debug("REST request to save Delegation : {}", delegationDTO);
        if (delegationDTO.getId() != null) {
            throw new BadRequestAlertException("A new delegation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DelegationDTO result = delegationService.save(delegationDTO);
        return ResponseEntity.created(new URI("/api/delegations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delegations} : Updates an existing delegation.
     *
     * @param delegationDTO the delegationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated delegationDTO,
     * or with status {@code 400 (Bad Request)} if the delegationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the delegationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delegations")
    public ResponseEntity<DelegationDTO> updateDelegation(@RequestBody DelegationDTO delegationDTO) throws URISyntaxException {
        log.debug("REST request to update Delegation : {}", delegationDTO);
        if (delegationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DelegationDTO result = delegationService.save(delegationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, delegationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /delegations} : get all the delegations.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of delegations in body.
     */
    @GetMapping("/delegations")
    public List<DelegationDTO> getAllDelegations() {
        log.debug("REST request to get all Delegations");
        return delegationService.findAll();
    }

    /**
     * {@code GET  /delegations/:id} : get the "id" delegation.
     *
     * @param id the id of the delegationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the delegationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delegations/{id}")
    public ResponseEntity<DelegationDTO> getDelegation(@PathVariable Long id) {
        log.debug("REST request to get Delegation : {}", id);
        Optional<DelegationDTO> delegationDTO = delegationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(delegationDTO);
    }

    /**
     * {@code DELETE  /delegations/:id} : delete the "id" delegation.
     *
     * @param id the id of the delegationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delegations/{id}")
    public ResponseEntity<Void> deleteDelegation(@PathVariable Long id) {
        log.debug("REST request to delete Delegation : {}", id);
        delegationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
