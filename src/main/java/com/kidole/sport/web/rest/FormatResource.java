package com.kidole.sport.web.rest;

import com.kidole.sport.service.FormatService;
import com.kidole.sport.web.rest.errors.BadRequestAlertException;
import com.kidole.sport.service.dto.FormatDTO;

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
 * REST controller for managing {@link com.kidole.sport.domain.Format}.
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
    public ResponseEntity<FormatDTO> createFormat(@Valid @RequestBody FormatDTO formatDTO) throws URISyntaxException {
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
    public ResponseEntity<FormatDTO> updateFormat(@Valid @RequestBody FormatDTO formatDTO) throws URISyntaxException {
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

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formats in body.
     */
    @GetMapping("/formats")
    public ResponseEntity<List<FormatDTO>> getAllFormats(Pageable pageable) {
        log.debug("REST request to get a page of Formats");
        Page<FormatDTO> page = formatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
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

    /**
     * {@code SEARCH  /_search/formats?query=:query} : search for the format corresponding
     * to the query.
     *
     * @param query the query of the format search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/formats")
    public ResponseEntity<List<FormatDTO>> searchFormats(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Formats for query {}", query);
        Page<FormatDTO> page = formatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
