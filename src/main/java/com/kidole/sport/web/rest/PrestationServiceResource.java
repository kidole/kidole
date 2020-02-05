package com.kidole.sport.web.rest;

import com.kidole.sport.service.PrestationServiceService;
import com.kidole.sport.web.rest.errors.BadRequestAlertException;
import com.kidole.sport.service.dto.PrestationServiceDTO;

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
 * REST controller for managing {@link com.kidole.sport.domain.PrestationService}.
 */
@RestController
@RequestMapping("/api")
public class PrestationServiceResource {

    private final Logger log = LoggerFactory.getLogger(PrestationServiceResource.class);

    private static final String ENTITY_NAME = "prestationService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrestationServiceService prestationServiceService;

    public PrestationServiceResource(PrestationServiceService prestationServiceService) {
        this.prestationServiceService = prestationServiceService;
    }

    /**
     * {@code POST  /prestation-services} : Create a new prestationService.
     *
     * @param prestationServiceDTO the prestationServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prestationServiceDTO, or with status {@code 400 (Bad Request)} if the prestationService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prestation-services")
    public ResponseEntity<PrestationServiceDTO> createPrestationService(@Valid @RequestBody PrestationServiceDTO prestationServiceDTO) throws URISyntaxException {
        log.debug("REST request to save PrestationService : {}", prestationServiceDTO);
        if (prestationServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new prestationService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrestationServiceDTO result = prestationServiceService.save(prestationServiceDTO);
        return ResponseEntity.created(new URI("/api/prestation-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prestation-services} : Updates an existing prestationService.
     *
     * @param prestationServiceDTO the prestationServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prestationServiceDTO,
     * or with status {@code 400 (Bad Request)} if the prestationServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prestationServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prestation-services")
    public ResponseEntity<PrestationServiceDTO> updatePrestationService(@Valid @RequestBody PrestationServiceDTO prestationServiceDTO) throws URISyntaxException {
        log.debug("REST request to update PrestationService : {}", prestationServiceDTO);
        if (prestationServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrestationServiceDTO result = prestationServiceService.save(prestationServiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prestationServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prestation-services} : get all the prestationServices.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prestationServices in body.
     */
    @GetMapping("/prestation-services")
    public ResponseEntity<List<PrestationServiceDTO>> getAllPrestationServices(Pageable pageable) {
        log.debug("REST request to get a page of PrestationServices");
        Page<PrestationServiceDTO> page = prestationServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prestation-services/:id} : get the "id" prestationService.
     *
     * @param id the id of the prestationServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prestationServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prestation-services/{id}")
    public ResponseEntity<PrestationServiceDTO> getPrestationService(@PathVariable Long id) {
        log.debug("REST request to get PrestationService : {}", id);
        Optional<PrestationServiceDTO> prestationServiceDTO = prestationServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prestationServiceDTO);
    }

    /**
     * {@code DELETE  /prestation-services/:id} : delete the "id" prestationService.
     *
     * @param id the id of the prestationServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prestation-services/{id}")
    public ResponseEntity<Void> deletePrestationService(@PathVariable Long id) {
        log.debug("REST request to delete PrestationService : {}", id);
        prestationServiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/prestation-services?query=:query} : search for the prestationService corresponding
     * to the query.
     *
     * @param query the query of the prestationService search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/prestation-services")
    public ResponseEntity<List<PrestationServiceDTO>> searchPrestationServices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PrestationServices for query {}", query);
        Page<PrestationServiceDTO> page = prestationServiceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
