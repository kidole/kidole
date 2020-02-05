package com.kidole.sport.service;

import com.kidole.sport.service.dto.DisciplineDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kidole.sport.domain.Discipline}.
 */
public interface DisciplineService {

    /**
     * Save a discipline.
     *
     * @param disciplineDTO the entity to save.
     * @return the persisted entity.
     */
    DisciplineDTO save(DisciplineDTO disciplineDTO);

    /**
     * Get all the disciplines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DisciplineDTO> findAll(Pageable pageable);


    /**
     * Get the "id" discipline.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DisciplineDTO> findOne(Long id);

    /**
     * Delete the "id" discipline.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the discipline corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DisciplineDTO> search(String query, Pageable pageable);
}
