package com.kidole.sport.web.rest;

import com.kidole.sport.service.CompetitionServicesOfferService;
import com.kidole.sport.web.rest.errors.BadRequestAlertException;
import com.kidole.sport.service.dto.CompetitionServicesOfferDTO;

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
 * REST controller for managing {@link com.kidole.sport.domain.CompetitionServicesOffer}.
 */
@RestController
@RequestMapping("/api")
public class CompetitionServicesOfferResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionServicesOfferResource.class);

    private static final String ENTITY_NAME = "competitionServicesOffer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitionServicesOfferService competitionServicesOfferService;

    public CompetitionServicesOfferResource(CompetitionServicesOfferService competitionServicesOfferService) {
        this.competitionServicesOfferService = competitionServicesOfferService;
    }

    /**
     * {@code POST  /competition-services-offers} : Create a new competitionServicesOffer.
     *
     * @param competitionServicesOfferDTO the competitionServicesOfferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitionServicesOfferDTO, or with status {@code 400 (Bad Request)} if the competitionServicesOffer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competition-services-offers")
    public ResponseEntity<CompetitionServicesOfferDTO> createCompetitionServicesOffer(@Valid @RequestBody CompetitionServicesOfferDTO competitionServicesOfferDTO) throws URISyntaxException {
        log.debug("REST request to save CompetitionServicesOffer : {}", competitionServicesOfferDTO);
        if (competitionServicesOfferDTO.getId() != null) {
            throw new BadRequestAlertException("A new competitionServicesOffer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitionServicesOfferDTO result = competitionServicesOfferService.save(competitionServicesOfferDTO);
        return ResponseEntity.created(new URI("/api/competition-services-offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competition-services-offers} : Updates an existing competitionServicesOffer.
     *
     * @param competitionServicesOfferDTO the competitionServicesOfferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitionServicesOfferDTO,
     * or with status {@code 400 (Bad Request)} if the competitionServicesOfferDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitionServicesOfferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competition-services-offers")
    public ResponseEntity<CompetitionServicesOfferDTO> updateCompetitionServicesOffer(@Valid @RequestBody CompetitionServicesOfferDTO competitionServicesOfferDTO) throws URISyntaxException {
        log.debug("REST request to update CompetitionServicesOffer : {}", competitionServicesOfferDTO);
        if (competitionServicesOfferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompetitionServicesOfferDTO result = competitionServicesOfferService.save(competitionServicesOfferDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitionServicesOfferDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competition-services-offers} : get all the competitionServicesOffers.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitionServicesOffers in body.
     */
    @GetMapping("/competition-services-offers")
    public ResponseEntity<List<CompetitionServicesOfferDTO>> getAllCompetitionServicesOffers(Pageable pageable) {
        log.debug("REST request to get a page of CompetitionServicesOffers");
        Page<CompetitionServicesOfferDTO> page = competitionServicesOfferService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /competition-services-offers/:id} : get the "id" competitionServicesOffer.
     *
     * @param id the id of the competitionServicesOfferDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitionServicesOfferDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competition-services-offers/{id}")
    public ResponseEntity<CompetitionServicesOfferDTO> getCompetitionServicesOffer(@PathVariable Long id) {
        log.debug("REST request to get CompetitionServicesOffer : {}", id);
        Optional<CompetitionServicesOfferDTO> competitionServicesOfferDTO = competitionServicesOfferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competitionServicesOfferDTO);
    }

    /**
     * {@code DELETE  /competition-services-offers/:id} : delete the "id" competitionServicesOffer.
     *
     * @param id the id of the competitionServicesOfferDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competition-services-offers/{id}")
    public ResponseEntity<Void> deleteCompetitionServicesOffer(@PathVariable Long id) {
        log.debug("REST request to delete CompetitionServicesOffer : {}", id);
        competitionServicesOfferService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/competition-services-offers?query=:query} : search for the competitionServicesOffer corresponding
     * to the query.
     *
     * @param query the query of the competitionServicesOffer search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/competition-services-offers")
    public ResponseEntity<List<CompetitionServicesOfferDTO>> searchCompetitionServicesOffers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CompetitionServicesOffers for query {}", query);
        Page<CompetitionServicesOfferDTO> page = competitionServicesOfferService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
