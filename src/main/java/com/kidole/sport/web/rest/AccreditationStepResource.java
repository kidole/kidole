package com.kidole.sport.web.rest;

import com.kidole.sport.service.AccreditationStepService;
import com.kidole.sport.web.rest.errors.BadRequestAlertException;
import com.kidole.sport.service.dto.AccreditationStepDTO;

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
 * REST controller for managing {@link com.kidole.sport.domain.AccreditationStep}.
 */
@RestController
@RequestMapping("/api")
public class AccreditationStepResource {

    private final Logger log = LoggerFactory.getLogger(AccreditationStepResource.class);

    private static final String ENTITY_NAME = "accreditationStep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccreditationStepService accreditationStepService;

    public AccreditationStepResource(AccreditationStepService accreditationStepService) {
        this.accreditationStepService = accreditationStepService;
    }

    /**
     * {@code POST  /accreditation-steps} : Create a new accreditationStep.
     *
     * @param accreditationStepDTO the accreditationStepDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accreditationStepDTO, or with status {@code 400 (Bad Request)} if the accreditationStep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accreditation-steps")
    public ResponseEntity<AccreditationStepDTO> createAccreditationStep(@Valid @RequestBody AccreditationStepDTO accreditationStepDTO) throws URISyntaxException {
        log.debug("REST request to save AccreditationStep : {}", accreditationStepDTO);
        if (accreditationStepDTO.getId() != null) {
            throw new BadRequestAlertException("A new accreditationStep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccreditationStepDTO result = accreditationStepService.save(accreditationStepDTO);
        return ResponseEntity.created(new URI("/api/accreditation-steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /accreditation-steps} : Updates an existing accreditationStep.
     *
     * @param accreditationStepDTO the accreditationStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accreditationStepDTO,
     * or with status {@code 400 (Bad Request)} if the accreditationStepDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accreditationStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accreditation-steps")
    public ResponseEntity<AccreditationStepDTO> updateAccreditationStep(@Valid @RequestBody AccreditationStepDTO accreditationStepDTO) throws URISyntaxException {
        log.debug("REST request to update AccreditationStep : {}", accreditationStepDTO);
        if (accreditationStepDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccreditationStepDTO result = accreditationStepService.save(accreditationStepDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accreditationStepDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /accreditation-steps} : get all the accreditationSteps.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accreditationSteps in body.
     */
    @GetMapping("/accreditation-steps")
    public ResponseEntity<List<AccreditationStepDTO>> getAllAccreditationSteps(Pageable pageable) {
        log.debug("REST request to get a page of AccreditationSteps");
        Page<AccreditationStepDTO> page = accreditationStepService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /accreditation-steps/:id} : get the "id" accreditationStep.
     *
     * @param id the id of the accreditationStepDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accreditationStepDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accreditation-steps/{id}")
    public ResponseEntity<AccreditationStepDTO> getAccreditationStep(@PathVariable Long id) {
        log.debug("REST request to get AccreditationStep : {}", id);
        Optional<AccreditationStepDTO> accreditationStepDTO = accreditationStepService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accreditationStepDTO);
    }

    /**
     * {@code DELETE  /accreditation-steps/:id} : delete the "id" accreditationStep.
     *
     * @param id the id of the accreditationStepDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accreditation-steps/{id}")
    public ResponseEntity<Void> deleteAccreditationStep(@PathVariable Long id) {
        log.debug("REST request to delete AccreditationStep : {}", id);
        accreditationStepService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/accreditation-steps?query=:query} : search for the accreditationStep corresponding
     * to the query.
     *
     * @param query the query of the accreditationStep search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/accreditation-steps")
    public ResponseEntity<List<AccreditationStepDTO>> searchAccreditationSteps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AccreditationSteps for query {}", query);
        Page<AccreditationStepDTO> page = accreditationStepService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
