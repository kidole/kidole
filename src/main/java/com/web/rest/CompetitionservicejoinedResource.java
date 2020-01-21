package com.web.rest;

import com.service.CompetitionservicejoinedService;
import com.web.rest.errors.BadRequestAlertException;
import com.service.dto.CompetitionservicejoinedDTO;

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
 * REST controller for managing {@link com.domain.Competitionservicejoined}.
 */
@RestController
@RequestMapping("/api")
public class CompetitionservicejoinedResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionservicejoinedResource.class);

    private static final String ENTITY_NAME = "competitionservicejoined";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitionservicejoinedService competitionservicejoinedService;

    public CompetitionservicejoinedResource(CompetitionservicejoinedService competitionservicejoinedService) {
        this.competitionservicejoinedService = competitionservicejoinedService;
    }

    /**
     * {@code POST  /competitionservicejoineds} : Create a new competitionservicejoined.
     *
     * @param competitionservicejoinedDTO the competitionservicejoinedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitionservicejoinedDTO, or with status {@code 400 (Bad Request)} if the competitionservicejoined has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competitionservicejoineds")
    public ResponseEntity<CompetitionservicejoinedDTO> createCompetitionservicejoined(@RequestBody CompetitionservicejoinedDTO competitionservicejoinedDTO) throws URISyntaxException {
        log.debug("REST request to save Competitionservicejoined : {}", competitionservicejoinedDTO);
        if (competitionservicejoinedDTO.getId() != null) {
            throw new BadRequestAlertException("A new competitionservicejoined cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitionservicejoinedDTO result = competitionservicejoinedService.save(competitionservicejoinedDTO);
        return ResponseEntity.created(new URI("/api/competitionservicejoineds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competitionservicejoineds} : Updates an existing competitionservicejoined.
     *
     * @param competitionservicejoinedDTO the competitionservicejoinedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitionservicejoinedDTO,
     * or with status {@code 400 (Bad Request)} if the competitionservicejoinedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitionservicejoinedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competitionservicejoineds")
    public ResponseEntity<CompetitionservicejoinedDTO> updateCompetitionservicejoined(@RequestBody CompetitionservicejoinedDTO competitionservicejoinedDTO) throws URISyntaxException {
        log.debug("REST request to update Competitionservicejoined : {}", competitionservicejoinedDTO);
        if (competitionservicejoinedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompetitionservicejoinedDTO result = competitionservicejoinedService.save(competitionservicejoinedDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitionservicejoinedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competitionservicejoineds} : get all the competitionservicejoineds.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitionservicejoineds in body.
     */
    @GetMapping("/competitionservicejoineds")
    public List<CompetitionservicejoinedDTO> getAllCompetitionservicejoineds() {
        log.debug("REST request to get all Competitionservicejoineds");
        return competitionservicejoinedService.findAll();
    }

    /**
     * {@code GET  /competitionservicejoineds/:id} : get the "id" competitionservicejoined.
     *
     * @param id the id of the competitionservicejoinedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitionservicejoinedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitionservicejoineds/{id}")
    public ResponseEntity<CompetitionservicejoinedDTO> getCompetitionservicejoined(@PathVariable Long id) {
        log.debug("REST request to get Competitionservicejoined : {}", id);
        Optional<CompetitionservicejoinedDTO> competitionservicejoinedDTO = competitionservicejoinedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competitionservicejoinedDTO);
    }

    /**
     * {@code DELETE  /competitionservicejoineds/:id} : delete the "id" competitionservicejoined.
     *
     * @param id the id of the competitionservicejoinedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competitionservicejoineds/{id}")
    public ResponseEntity<Void> deleteCompetitionservicejoined(@PathVariable Long id) {
        log.debug("REST request to delete Competitionservicejoined : {}", id);
        competitionservicejoinedService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
