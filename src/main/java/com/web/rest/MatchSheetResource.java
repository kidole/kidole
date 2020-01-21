package com.web.rest;

import com.service.MatchSheetService;
import com.web.rest.errors.BadRequestAlertException;
import com.service.dto.MatchSheetDTO;

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
 * REST controller for managing {@link com.domain.MatchSheet}.
 */
@RestController
@RequestMapping("/api")
public class MatchSheetResource {

    private final Logger log = LoggerFactory.getLogger(MatchSheetResource.class);

    private static final String ENTITY_NAME = "matchSheet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchSheetService matchSheetService;

    public MatchSheetResource(MatchSheetService matchSheetService) {
        this.matchSheetService = matchSheetService;
    }

    /**
     * {@code POST  /match-sheets} : Create a new matchSheet.
     *
     * @param matchSheetDTO the matchSheetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchSheetDTO, or with status {@code 400 (Bad Request)} if the matchSheet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/match-sheets")
    public ResponseEntity<MatchSheetDTO> createMatchSheet(@RequestBody MatchSheetDTO matchSheetDTO) throws URISyntaxException {
        log.debug("REST request to save MatchSheet : {}", matchSheetDTO);
        if (matchSheetDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchSheet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatchSheetDTO result = matchSheetService.save(matchSheetDTO);
        return ResponseEntity.created(new URI("/api/match-sheets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /match-sheets} : Updates an existing matchSheet.
     *
     * @param matchSheetDTO the matchSheetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchSheetDTO,
     * or with status {@code 400 (Bad Request)} if the matchSheetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchSheetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/match-sheets")
    public ResponseEntity<MatchSheetDTO> updateMatchSheet(@RequestBody MatchSheetDTO matchSheetDTO) throws URISyntaxException {
        log.debug("REST request to update MatchSheet : {}", matchSheetDTO);
        if (matchSheetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatchSheetDTO result = matchSheetService.save(matchSheetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchSheetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /match-sheets} : get all the matchSheets.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matchSheets in body.
     */
    @GetMapping("/match-sheets")
    public List<MatchSheetDTO> getAllMatchSheets(@RequestParam(required = false) String filter) {
        if ("confrontation-is-null".equals(filter)) {
            log.debug("REST request to get all MatchSheets where confrontation is null");
            return matchSheetService.findAllWhereConfrontationIsNull();
        }
        log.debug("REST request to get all MatchSheets");
        return matchSheetService.findAll();
    }

    /**
     * {@code GET  /match-sheets/:id} : get the "id" matchSheet.
     *
     * @param id the id of the matchSheetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchSheetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/match-sheets/{id}")
    public ResponseEntity<MatchSheetDTO> getMatchSheet(@PathVariable Long id) {
        log.debug("REST request to get MatchSheet : {}", id);
        Optional<MatchSheetDTO> matchSheetDTO = matchSheetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchSheetDTO);
    }

    /**
     * {@code DELETE  /match-sheets/:id} : delete the "id" matchSheet.
     *
     * @param id the id of the matchSheetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/match-sheets/{id}")
    public ResponseEntity<Void> deleteMatchSheet(@PathVariable Long id) {
        log.debug("REST request to delete MatchSheet : {}", id);
        matchSheetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
