package com.kidole.sport.web.rest;

import com.kidole.sport.service.PoulesService;
import com.kidole.sport.web.rest.errors.BadRequestAlertException;
import com.kidole.sport.service.dto.PoulesDTO;

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
 * REST controller for managing {@link com.kidole.sport.domain.Poules}.
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
    public ResponseEntity<PoulesDTO> createPoules(@Valid @RequestBody PoulesDTO poulesDTO) throws URISyntaxException {
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
    public ResponseEntity<PoulesDTO> updatePoules(@Valid @RequestBody PoulesDTO poulesDTO) throws URISyntaxException {
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

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of poules in body.
     */
    @GetMapping("/poules")
    public ResponseEntity<List<PoulesDTO>> getAllPoules(Pageable pageable) {
        log.debug("REST request to get a page of Poules");
        Page<PoulesDTO> page = poulesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
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

    /**
     * {@code SEARCH  /_search/poules?query=:query} : search for the poules corresponding
     * to the query.
     *
     * @param query the query of the poules search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/poules")
    public ResponseEntity<List<PoulesDTO>> searchPoules(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Poules for query {}", query);
        Page<PoulesDTO> page = poulesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
