package com.web.rest;

import com.service.RubriqueService;
import com.web.rest.errors.BadRequestAlertException;
import com.service.dto.RubriqueDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.domain.Rubrique}.
 */
@RestController
@RequestMapping("/api")
public class RubriqueResource {

    private final Logger log = LoggerFactory.getLogger(RubriqueResource.class);

    private static final String ENTITY_NAME = "rubrique";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RubriqueService rubriqueService;

    public RubriqueResource(RubriqueService rubriqueService) {
        this.rubriqueService = rubriqueService;
    }

    /**
     * {@code POST  /rubriques} : Create a new rubrique.
     *
     * @param rubriqueDTO the rubriqueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rubriqueDTO, or with status {@code 400 (Bad Request)} if the rubrique has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rubriques")
    public ResponseEntity<RubriqueDTO> createRubrique(@RequestBody RubriqueDTO rubriqueDTO) throws URISyntaxException {
        log.debug("REST request to save Rubrique : {}", rubriqueDTO);
        if (rubriqueDTO.getId() != null) {
            throw new BadRequestAlertException("A new rubrique cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RubriqueDTO result = rubriqueService.save(rubriqueDTO);
        return ResponseEntity.created(new URI("/api/rubriques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rubriques} : Updates an existing rubrique.
     *
     * @param rubriqueDTO the rubriqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rubriqueDTO,
     * or with status {@code 400 (Bad Request)} if the rubriqueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rubriqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rubriques")
    public ResponseEntity<RubriqueDTO> updateRubrique(@RequestBody RubriqueDTO rubriqueDTO) throws URISyntaxException {
        log.debug("REST request to update Rubrique : {}", rubriqueDTO);
        if (rubriqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RubriqueDTO result = rubriqueService.save(rubriqueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rubriqueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rubriques} : get all the rubriques.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rubriques in body.
     */
    @GetMapping("/rubriques")
    public List<RubriqueDTO> getAllRubriques(@RequestParam(required = false) String filter) {
        if ("prestationservice-is-null".equals(filter)) {
            log.debug("REST request to get all Rubriques where prestationService is null");
            return rubriqueService.findAllWherePrestationServiceIsNull();
        }
        if ("competitionservicesoffer-is-null".equals(filter)) {
            log.debug("REST request to get all Rubriques where competitionServicesOffer is null");
            return rubriqueService.findAllWhereCompetitionServicesOfferIsNull();
        }
        log.debug("REST request to get all Rubriques");
        return rubriqueService.findAll();
    }

    /**
     * {@code GET  /rubriques/:id} : get the "id" rubrique.
     *
     * @param id the id of the rubriqueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rubriqueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rubriques/{id}")
    public ResponseEntity<RubriqueDTO> getRubrique(@PathVariable Long id) {
        log.debug("REST request to get Rubrique : {}", id);
        Optional<RubriqueDTO> rubriqueDTO = rubriqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rubriqueDTO);
    }

    /**
     * {@code DELETE  /rubriques/:id} : delete the "id" rubrique.
     *
     * @param id the id of the rubriqueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rubriques/{id}")
    public ResponseEntity<Void> deleteRubrique(@PathVariable Long id) {
        log.debug("REST request to delete Rubrique : {}", id);
        rubriqueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
