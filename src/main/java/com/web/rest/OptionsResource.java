package com.web.rest;

import com.service.OptionsService;
import com.web.rest.errors.BadRequestAlertException;
import com.service.dto.OptionsDTO;

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
 * REST controller for managing {@link com.domain.Options}.
 */
@RestController
@RequestMapping("/api")
public class OptionsResource {

    private final Logger log = LoggerFactory.getLogger(OptionsResource.class);

    private static final String ENTITY_NAME = "options";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptionsService optionsService;

    public OptionsResource(OptionsService optionsService) {
        this.optionsService = optionsService;
    }

    /**
     * {@code POST  /options} : Create a new options.
     *
     * @param optionsDTO the optionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new optionsDTO, or with status {@code 400 (Bad Request)} if the options has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/options")
    public ResponseEntity<OptionsDTO> createOptions(@RequestBody OptionsDTO optionsDTO) throws URISyntaxException {
        log.debug("REST request to save Options : {}", optionsDTO);
        if (optionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new options cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OptionsDTO result = optionsService.save(optionsDTO);
        return ResponseEntity.created(new URI("/api/options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /options} : Updates an existing options.
     *
     * @param optionsDTO the optionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optionsDTO,
     * or with status {@code 400 (Bad Request)} if the optionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the optionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/options")
    public ResponseEntity<OptionsDTO> updateOptions(@RequestBody OptionsDTO optionsDTO) throws URISyntaxException {
        log.debug("REST request to update Options : {}", optionsDTO);
        if (optionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OptionsDTO result = optionsService.save(optionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /options} : get all the options.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of options in body.
     */
    @GetMapping("/options")
    public List<OptionsDTO> getAllOptions() {
        log.debug("REST request to get all Options");
        return optionsService.findAll();
    }

    /**
     * {@code GET  /options/:id} : get the "id" options.
     *
     * @param id the id of the optionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/options/{id}")
    public ResponseEntity<OptionsDTO> getOptions(@PathVariable Long id) {
        log.debug("REST request to get Options : {}", id);
        Optional<OptionsDTO> optionsDTO = optionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionsDTO);
    }

    /**
     * {@code DELETE  /options/:id} : delete the "id" options.
     *
     * @param id the id of the optionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/options/{id}")
    public ResponseEntity<Void> deleteOptions(@PathVariable Long id) {
        log.debug("REST request to delete Options : {}", id);
        optionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
