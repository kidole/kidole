package com.web.rest;

import com.service.PhaseService;
import com.web.rest.errors.BadRequestAlertException;
import com.service.dto.PhaseDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.domain.Phase}.
 */
@RestController
@RequestMapping("/api")
public class PhaseResource {

    private final Logger log = LoggerFactory.getLogger(PhaseResource.class);

    private static final String ENTITY_NAME = "phase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhaseService phaseService;

    public PhaseResource(PhaseService phaseService) {
        this.phaseService = phaseService;
    }

    /**
     * {@code POST  /phases} : Create a new phase.
     *
     * @param phaseDTO the phaseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phaseDTO, or with status {@code 400 (Bad Request)} if the phase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phases")
    public ResponseEntity<PhaseDTO> createPhase(@RequestBody PhaseDTO phaseDTO) throws URISyntaxException {
        log.debug("REST request to save Phase : {}", phaseDTO);
        if (phaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new phase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhaseDTO result = phaseService.save(phaseDTO);
        return ResponseEntity.created(new URI("/api/phases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phases} : Updates an existing phase.
     *
     * @param phaseDTO the phaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phaseDTO,
     * or with status {@code 400 (Bad Request)} if the phaseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phases")
    public ResponseEntity<PhaseDTO> updatePhase(@RequestBody PhaseDTO phaseDTO) throws URISyntaxException {
        log.debug("REST request to update Phase : {}", phaseDTO);
        if (phaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhaseDTO result = phaseService.save(phaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /phases} : get all the phases.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phases in body.
     */
    @GetMapping("/phases")
    public List<PhaseDTO> getAllPhases(@RequestParam(required = false) String filter) {
        if ("format-is-null".equals(filter)) {
            log.debug("REST request to get all Phases where format is null");
            return phaseService.findAllWhereFormatIsNull();
        }
        log.debug("REST request to get all Phases");
        return phaseService.findAll();
    }

    /**
     * {@code GET  /phases/:id} : get the "id" phase.
     *
     * @param id the id of the phaseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phaseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phases/{id}")
    public ResponseEntity<PhaseDTO> getPhase(@PathVariable Long id) {
        log.debug("REST request to get Phase : {}", id);
        Optional<PhaseDTO> phaseDTO = phaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phaseDTO);
    }

    /**
     * {@code DELETE  /phases/:id} : delete the "id" phase.
     *
     * @param id the id of the phaseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phases/{id}")
    public ResponseEntity<Void> deletePhase(@PathVariable Long id) {
        log.debug("REST request to delete Phase : {}", id);
        phaseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
