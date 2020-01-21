package com.web.rest;

import com.service.FormatService;
import com.web.rest.errors.BadRequestAlertException;
import com.service.dto.FormatDTO;

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
 * REST controller for managing {@link com.domain.Format}.
 */
@RestController
@RequestMapping("/api")
public class FormatResource {

    private final Logger log = LoggerFactory.getLogger(FormatResource.class);

    private static final String ENTITY_NAME = "format";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormatService formatService;

    public FormatResource(FormatService formatService) {
        this.formatService = formatService;
    }

    /**
     * {@code POST  /formats} : Create a new format.
     *
     * @param formatDTO the formatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formatDTO, or with status {@code 400 (Bad Request)} if the format has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/formats")
    public ResponseEntity<FormatDTO> createFormat(@RequestBody FormatDTO formatDTO) throws URISyntaxException {
        log.debug("REST request to save Format : {}", formatDTO);
        if (formatDTO.getId() != null) {
            throw new BadRequestAlertException("A new format cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormatDTO result = formatService.save(formatDTO);
        return ResponseEntity.created(new URI("/api/formats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /formats} : Updates an existing format.
     *
     * @param formatDTO the formatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formatDTO,
     * or with status {@code 400 (Bad Request)} if the formatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/formats")
    public ResponseEntity<FormatDTO> updateFormat(@RequestBody FormatDTO formatDTO) throws URISyntaxException {
        log.debug("REST request to update Format : {}", formatDTO);
        if (formatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FormatDTO result = formatService.save(formatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /formats} : get all the formats.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formats in body.
     */
    @GetMapping("/formats")
    public List<FormatDTO> getAllFormats() {
        log.debug("REST request to get all Formats");
        return formatService.findAll();
    }

    /**
     * {@code GET  /formats/:id} : get the "id" format.
     *
     * @param id the id of the formatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/formats/{id}")
    public ResponseEntity<FormatDTO> getFormat(@PathVariable Long id) {
        log.debug("REST request to get Format : {}", id);
        Optional<FormatDTO> formatDTO = formatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formatDTO);
    }

    /**
     * {@code DELETE  /formats/:id} : delete the "id" format.
     *
     * @param id the id of the formatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/formats/{id}")
    public ResponseEntity<Void> deleteFormat(@PathVariable Long id) {
        log.debug("REST request to delete Format : {}", id);
        formatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
