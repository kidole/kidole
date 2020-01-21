package com.web.rest;

import com.service.PoulesService;
import com.web.rest.errors.BadRequestAlertException;
import com.service.dto.PoulesDTO;

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
 * REST controller for managing {@link com.domain.Poules}.
 */
@RestController
@RequestMapping("/api")
public class PoulesResource {

    private final Logger log = LoggerFactory.getLogger(PoulesResource.class);

    private static final String ENTITY_NAME = "poules";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PoulesService poulesService;

    public PoulesResource(PoulesService poulesService) {
        this.poulesService = poulesService;
    }

    /**
     * {@code POST  /poules} : Create a new poules.
     *
     * @param poulesDTO the poulesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new poulesDTO, or with status {@code 400 (Bad Request)} if the poules has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/poules")
    public ResponseEntity<PoulesDTO> createPoules(@RequestBody PoulesDTO poulesDTO) throws URISyntaxException {
        log.debug("REST request to save Poules : {}", poulesDTO);
        if (poulesDTO.getId() != null) {
            throw new BadRequestAlertException("A new poules cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoulesDTO result = poulesService.save(poulesDTO);
        return ResponseEntity.created(new URI("/api/poules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /poules} : Updates an existing poules.
     *
     * @param poulesDTO the poulesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated poulesDTO,
     * or with status {@code 400 (Bad Request)} if the poulesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the poulesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/poules")
    public ResponseEntity<PoulesDTO> updatePoules(@RequestBody PoulesDTO poulesDTO) throws URISyntaxException {
        log.debug("REST request to update Poules : {}", poulesDTO);
        if (poulesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PoulesDTO result = poulesService.save(poulesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, poulesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /poules} : get all the poules.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of poules in body.
     */
    @GetMapping("/poules")
    public List<PoulesDTO> getAllPoules() {
        log.debug("REST request to get all Poules");
        return poulesService.findAll();
    }

    /**
     * {@code GET  /poules/:id} : get the "id" poules.
     *
     * @param id the id of the poulesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the poulesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/poules/{id}")
    public ResponseEntity<PoulesDTO> getPoules(@PathVariable Long id) {
        log.debug("REST request to get Poules : {}", id);
        Optional<PoulesDTO> poulesDTO = poulesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(poulesDTO);
    }

    /**
     * {@code DELETE  /poules/:id} : delete the "id" poules.
     *
     * @param id the id of the poulesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/poules/{id}")
    public ResponseEntity<Void> deletePoules(@PathVariable Long id) {
        log.debug("REST request to delete Poules : {}", id);
        poulesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
