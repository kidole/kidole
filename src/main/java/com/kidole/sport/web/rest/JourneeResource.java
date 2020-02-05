package com.kidole.sport.web.rest;

import com.kidole.sport.service.JourneeService;
import com.kidole.sport.web.rest.errors.BadRequestAlertException;
import com.kidole.sport.service.dto.JourneeDTO;

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
 * REST controller for managing {@link com.kidole.sport.domain.Journee}.
 */
@RestController
@RequestMapping("/api")
public class JourneeResource {

    private final Logger log = LoggerFactory.getLogger(JourneeResource.class);

    private static final String ENTITY_NAME = "journee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JourneeService journeeService;

    public JourneeResource(JourneeService journeeService) {
        this.journeeService = journeeService;
    }

    /**
     * {@code POST  /journees} : Create a new journee.
     *
     * @param journeeDTO the journeeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new journeeDTO, or with status {@code 400 (Bad Request)} if the journee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/journees")
    public ResponseEntity<JourneeDTO> createJournee(@Valid @RequestBody JourneeDTO journeeDTO) throws URISyntaxException {
        log.debug("REST request to save Journee : {}", journeeDTO);
        if (journeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new journee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JourneeDTO result = journeeService.save(journeeDTO);
        return ResponseEntity.created(new URI("/api/journees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /journees} : Updates an existing journee.
     *
     * @param journeeDTO the journeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated journeeDTO,
     * or with status {@code 400 (Bad Request)} if the journeeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the journeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/journees")
    public ResponseEntity<JourneeDTO> updateJournee(@Valid @RequestBody JourneeDTO journeeDTO) throws URISyntaxException {
        log.debug("REST request to update Journee : {}", journeeDTO);
        if (journeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JourneeDTO result = journeeService.save(journeeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, journeeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /journees} : get all the journees.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of journees in body.
     */
    @GetMapping("/journees")
    public ResponseEntity<List<JourneeDTO>> getAllJournees(Pageable pageable) {
        log.debug("REST request to get a page of Journees");
        Page<JourneeDTO> page = journeeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /journees/:id} : get the "id" journee.
     *
     * @param id the id of the journeeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the journeeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/journees/{id}")
    public ResponseEntity<JourneeDTO> getJournee(@PathVariable Long id) {
        log.debug("REST request to get Journee : {}", id);
        Optional<JourneeDTO> journeeDTO = journeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(journeeDTO);
    }

    /**
     * {@code DELETE  /journees/:id} : delete the "id" journee.
     *
     * @param id the id of the journeeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/journees/{id}")
    public ResponseEntity<Void> deleteJournee(@PathVariable Long id) {
        log.debug("REST request to delete Journee : {}", id);
        journeeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/journees?query=:query} : search for the journee corresponding
     * to the query.
     *
     * @param query the query of the journee search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/journees")
    public ResponseEntity<List<JourneeDTO>> searchJournees(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Journees for query {}", query);
        Page<JourneeDTO> page = journeeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
