package com.kidole.sport.service.impl;

import com.kidole.sport.service.FilesService;
import com.kidole.sport.domain.Files;
import com.kidole.sport.repository.FilesRepository;
import com.kidole.sport.repository.search.FilesSearchRepository;
import com.kidole.sport.service.dto.FilesDTO;
import com.kidole.sport.service.mapper.FilesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Files}.
 */
@Service
@Transactional
public class FilesServiceImpl implements FilesService {

    private final Logger log = LoggerFactory.getLogger(FilesServiceImpl.class);

    private final FilesRepository filesRepository;

    private final FilesMapper filesMapper;

    private final FilesSearchRepository filesSearchRepository;

    public FilesServiceImpl(FilesRepository filesRepository, FilesMapper filesMapper, FilesSearchRepository filesSearchRepository) {
        this.filesRepository = filesRepository;
        this.filesMapper = filesMapper;
        this.filesSearchRepository = filesSearchRepository;
    }

    /**
     * Save a files.
     *
     * @param filesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FilesDTO save(FilesDTO filesDTO) {
        log.debug("Request to save Files : {}", filesDTO);
        Files files = filesMapper.toEntity(filesDTO);
        files = filesRepository.save(files);
        FilesDTO result = filesMapper.toDto(files);
        filesSearchRepository.save(files);
        return result;
    }

    /**
     * Get all the files.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FilesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Files");
        return filesRepository.findAll(pageable)
            .map(filesMapper::toDto);
    }


    /**
     * Get one files by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FilesDTO> findOne(Long id) {
        log.debug("Request to get Files : {}", id);
        return filesRepository.findById(id)
            .map(filesMapper::toDto);
    }

    /**
     * Delete the files by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Files : {}", id);
        filesRepository.deleteById(id);
        filesSearchRepository.deleteById(id);
    }

    /**
     * Search for the files corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FilesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Files for query {}", query);
        return filesSearchRepository.search(queryStringQuery(query), pageable)
            .map(filesMapper::toDto);
    }
}
