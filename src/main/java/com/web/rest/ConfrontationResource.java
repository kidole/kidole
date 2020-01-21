package com.web.rest;

import com.service.ConfrontationService;
import com.web.rest.errors.BadRequestAlertException;
import com.service.dto.ConfrontationDTO;

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
 * REST controller for managing {@link com.domain.Confrontation}.
 */
@RestController
@RequestMapping("/api")
public class ConfrontationResource {

    private final Logger log = LoggerFactory.getLogger(ConfrontationResource.class);

    private static final String ENTITY_NAME = "confrontation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfrontationService confrontationService;

    public ConfrontationResource(ConfrontationService confrontationService) {
        this.confrontationService = confrontationService;
    }

    /**
     * {@code POST  /confrontations} : Create a new confrontation.
     *
     * @param confrontationDTO the confrontationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new confrontationDTO, or with status {@code 400 (Bad Request)} if the confrontation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/confrontations")
    public ResponseEntity<ConfrontationDTO> createConfrontation(@RequestBody ConfrontationDTO confrontationDTO) throws URISyntaxException {
        log.debug("REST request to save Confrontation : {}", confrontationDTO);
        if (confrontationDTO.getId() != null) {
            throw new BadRequestAlertException("A new confrontation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfrontationDTO result = confrontationService.save(confrontationDTO);
        return ResponseEntity.created(new URI("/api/confrontations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /confrontations} : Updates an existing confrontation.
     *
     * @param confrontationDTO the confrontationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated confrontationDTO,
     * or with status {@code 400 (Bad Request)} if the confrontationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the confrontationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/confrontations")
    public ResponseEntity<ConfrontationDTO> updateConfrontation(@RequestBody ConfrontationDTO confrontationDTO) throws URISyntaxException {
        log.debug("REST request to update Confrontation : {}", confrontationDTO);
        if (confrontationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConfrontationDTO result = confrontationService.save(confrontationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, confrontationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /confrontations} : get all the confrontations.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of confrontations in body.
     */
    @GetMapping("/confrontations")
    public List<ConfrontationDTO> getAllConfrontations() {
        log.debug("REST request to get all Confrontations");
        return confrontationService.findAll();
    }

    /**
     * {@code GET  /confrontations/:id} : get the "id" confrontation.
     *
     * @param id the id of the confrontationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the confrontationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/confrontations/{id}")
    public ResponseEntity<ConfrontationDTO> getConfrontation(@PathVariable Long id) {
        log.debug("REST request to get Confrontation : {}", id);
        Optional<ConfrontationDTO> confrontationDTO = confrontationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(confrontationDTO);
    }

    /**
     * {@code DELETE  /confrontations/:id} : delete the "id" confrontation.
     *
     * @param id the id of the confrontationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/confrontations/{id}")
    public ResponseEntity<Void> deleteConfrontation(@PathVariable Long id) {
        log.debug("REST request to delete Confrontation : {}", id);
        confrontationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
