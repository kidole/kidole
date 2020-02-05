package com.kidole.sport.web.rest;

import com.kidole.sport.service.AccreditationService;
import com.kidole.sport.web.rest.errors.BadRequestAlertException;
import com.kidole.sport.service.dto.AccreditationDTO;

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
 * REST controller for managing {@link com.kidole.sport.domain.Accreditation}.
 */
@RestController
@RequestMapping("/api")
public class AccreditationResource {

    private final Logger log = LoggerFactory.getLogger(AccreditationResource.class);

    private static final String ENTITY_NAME = "accreditation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccreditationService accreditationService;

    public AccreditationResource(AccreditationService accreditationService) {
        this.accreditationService = accreditationService;
    }

    /**
     * {@code POST  /accreditations} : Create a new accreditation.
     *
     * @param accreditationDTO the accreditationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accreditationDTO, or with status {@code 400 (Bad Request)} if the accreditation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accreditations")
    public ResponseEntity<AccreditationDTO> createAccreditation(@Valid @RequestBody AccreditationDTO accreditationDTO) throws URISyntaxException {
        log.debug("REST request to save Accreditation : {}", accreditationDTO);
        if (accreditationDTO.getId() != null) {
            throw new BadRequestAlertException("A new accreditation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccreditationDTO result = accreditationService.save(accreditationDTO);
        return ResponseEntity.created(new URI("/api/accreditations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /accreditations} : Updates an existing accreditation.
     *
     * @param accreditationDTO the accreditationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accreditationDTO,
     * or with status {@code 400 (Bad Request)} if the accreditationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accreditationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accreditations")
    public ResponseEntity<AccreditationDTO> updateAccreditation(@Valid @RequestBody AccreditationDTO accreditationDTO) throws URISyntaxException {
        log.debug("REST request to update Accreditation : {}", accreditationDTO);
        if (accreditationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccreditationDTO result = accreditationService.save(accreditationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accreditationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /accreditations} : get all the accreditations.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accreditations in body.
     */
    @GetMapping("/accreditations")
    public ResponseEntity<List<AccreditationDTO>> getAllAccreditations(Pageable pageable) {
        log.debug("REST request to get a page of Accreditations");
        Page<AccreditationDTO> page = accreditationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /accreditations/:id} : get the "id" accreditation.
     *
     * @param id the id of the accreditationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accreditationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accreditations/{id}")
    public ResponseEntity<AccreditationDTO> getAccreditation(@PathVariable Long id) {
        log.debug("REST request to get Accreditation : {}", id);
        Optional<AccreditationDTO> accreditationDTO = accreditationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accreditationDTO);
    }

    /**
     * {@code DELETE  /accreditations/:id} : delete the "id" accreditation.
     *
     * @param id the id of the accreditationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accreditations/{id}")
    public ResponseEntity<Void> deleteAccreditation(@PathVariable Long id) {
        log.debug("REST request to delete Accreditation : {}", id);
        accreditationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/accreditations?query=:query} : search for the accreditation corresponding
     * to the query.
     *
     * @param query the query of the accreditation search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/accreditations")
    public ResponseEntity<List<AccreditationDTO>> searchAccreditations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Accreditations for query {}", query);
        Page<AccreditationDTO> page = accreditationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
