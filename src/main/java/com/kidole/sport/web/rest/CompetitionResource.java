package com.kidole.sport.web.rest;

import com.kidole.sport.service.CompetitionService;
import com.kidole.sport.web.rest.errors.BadRequestAlertException;
import com.kidole.sport.service.dto.CompetitionDTO;

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
 * REST controller for managing {@link com.kidole.sport.domain.Competition}.
 */
@RestController
@RequestMapping("/api")
public class CompetitionResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionResource.class);

    private static final String ENTITY_NAME = "competition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitionService competitionService;

    public CompetitionResource(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    /**
     * {@code POST  /competitions} : Create a new competition.
     *
     * @param competitionDTO the competitionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitionDTO, or with status {@code 400 (Bad Request)} if the competition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competitions")
    public ResponseEntity<CompetitionDTO> createCompetition(@Valid @RequestBody CompetitionDTO competitionDTO) throws URISyntaxException {
        log.debug("REST request to save Competition : {}", competitionDTO);
        if (competitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new competition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitionDTO result = competitionService.save(competitionDTO);
        return ResponseEntity.created(new URI("/api/competitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competitions} : Updates an existing competition.
     *
     * @param competitionDTO the competitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitionDTO,
     * or with status {@code 400 (Bad Request)} if the competitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competitions")
    public ResponseEntity<CompetitionDTO> updateCompetition(@Valid @RequestBody CompetitionDTO competitionDTO) throws URISyntaxException {
        log.debug("REST request to update Competition : {}", competitionDTO);
        if (competitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompetitionDTO result = competitionService.save(competitionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competitions} : get all the competitions.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitions in body.
     */
    @GetMapping("/competitions")
    public ResponseEntity<List<CompetitionDTO>> getAllCompetitions(Pageable pageable) {
        log.debug("REST request to get a page of Competitions");
        Page<CompetitionDTO> page = competitionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /competitions/:id} : get the "id" competition.
     *
     * @param id the id of the competitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitions/{id}")
    public ResponseEntity<CompetitionDTO> getCompetition(@PathVariable Long id) {
        log.debug("REST request to get Competition : {}", id);
        Optional<CompetitionDTO> competitionDTO = competitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competitionDTO);
    }

    /**
     * {@code DELETE  /competitions/:id} : delete the "id" competition.
     *
     * @param id the id of the competitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competitions/{id}")
    public ResponseEntity<Void> deleteCompetition(@PathVariable Long id) {
        log.debug("REST request to delete Competition : {}", id);
        competitionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/competitions?query=:query} : search for the competition corresponding
     * to the query.
     *
     * @param query the query of the competition search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/competitions")
    public ResponseEntity<List<CompetitionDTO>> searchCompetitions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Competitions for query {}", query);
        Page<CompetitionDTO> page = competitionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
