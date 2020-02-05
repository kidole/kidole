package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.CompetitionServicesOffer;
import com.kidole.sport.repository.CompetitionServicesOfferRepository;
import com.kidole.sport.repository.search.CompetitionServicesOfferSearchRepository;
import com.kidole.sport.service.CompetitionServicesOfferService;
import com.kidole.sport.service.dto.CompetitionServicesOfferDTO;
import com.kidole.sport.service.mapper.CompetitionServicesOfferMapper;
import com.kidole.sport.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.kidole.sport.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CompetitionServicesOfferResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class CompetitionServicesOfferResourceIT {

    private static final String DEFAULT_COMPETITION_SERVICES_OFFER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPETITION_SERVICES_OFFER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPETITION_SERVICES_OFFER_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_COMPETITION_SERVICES_OFFER_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_COMPETITION_SERVICES_OFFER_URL = "AAAAAAAAAA";
    private static final String UPDATED_COMPETITION_SERVICES_OFFER_URL = "BBBBBBBBBB";

    @Autowired
    private CompetitionServicesOfferRepository competitionServicesOfferRepository;

    @Autowired
    private CompetitionServicesOfferMapper competitionServicesOfferMapper;

    @Autowired
    private CompetitionServicesOfferService competitionServicesOfferService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.CompetitionServicesOfferSearchRepositoryMockConfiguration
     */
    @Autowired
    private CompetitionServicesOfferSearchRepository mockCompetitionServicesOfferSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCompetitionServicesOfferMockMvc;

    private CompetitionServicesOffer competitionServicesOffer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompetitionServicesOfferResource competitionServicesOfferResource = new CompetitionServicesOfferResource(competitionServicesOfferService);
        this.restCompetitionServicesOfferMockMvc = MockMvcBuilders.standaloneSetup(competitionServicesOfferResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitionServicesOffer createEntity(EntityManager em) {
        CompetitionServicesOffer competitionServicesOffer = new CompetitionServicesOffer()
            .competitionServicesOfferName(DEFAULT_COMPETITION_SERVICES_OFFER_NAME)
            .competitionServicesOfferDetail(DEFAULT_COMPETITION_SERVICES_OFFER_DETAIL)
            .competitionServicesOfferUrl(DEFAULT_COMPETITION_SERVICES_OFFER_URL);
        return competitionServicesOffer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitionServicesOffer createUpdatedEntity(EntityManager em) {
        CompetitionServicesOffer competitionServicesOffer = new CompetitionServicesOffer()
            .competitionServicesOfferName(UPDATED_COMPETITION_SERVICES_OFFER_NAME)
            .competitionServicesOfferDetail(UPDATED_COMPETITION_SERVICES_OFFER_DETAIL)
            .competitionServicesOfferUrl(UPDATED_COMPETITION_SERVICES_OFFER_URL);
        return competitionServicesOffer;
    }

    @BeforeEach
    public void initTest() {
        competitionServicesOffer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitionServicesOffer() throws Exception {
        int databaseSizeBeforeCreate = competitionServicesOfferRepository.findAll().size();

        // Create the CompetitionServicesOffer
        CompetitionServicesOfferDTO competitionServicesOfferDTO = competitionServicesOfferMapper.toDto(competitionServicesOffer);
        restCompetitionServicesOfferMockMvc.perform(post("/api/competition-services-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionServicesOfferDTO)))
            .andExpect(status().isCreated());

        // Validate the CompetitionServicesOffer in the database
        List<CompetitionServicesOffer> competitionServicesOfferList = competitionServicesOfferRepository.findAll();
        assertThat(competitionServicesOfferList).hasSize(databaseSizeBeforeCreate + 1);
        CompetitionServicesOffer testCompetitionServicesOffer = competitionServicesOfferList.get(competitionServicesOfferList.size() - 1);
        assertThat(testCompetitionServicesOffer.getCompetitionServicesOfferName()).isEqualTo(DEFAULT_COMPETITION_SERVICES_OFFER_NAME);
        assertThat(testCompetitionServicesOffer.getCompetitionServicesOfferDetail()).isEqualTo(DEFAULT_COMPETITION_SERVICES_OFFER_DETAIL);
        assertThat(testCompetitionServicesOffer.getCompetitionServicesOfferUrl()).isEqualTo(DEFAULT_COMPETITION_SERVICES_OFFER_URL);

        // Validate the CompetitionServicesOffer in Elasticsearch
        verify(mockCompetitionServicesOfferSearchRepository, times(1)).save(testCompetitionServicesOffer);
    }

    @Test
    @Transactional
    public void createCompetitionServicesOfferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitionServicesOfferRepository.findAll().size();

        // Create the CompetitionServicesOffer with an existing ID
        competitionServicesOffer.setId(1L);
        CompetitionServicesOfferDTO competitionServicesOfferDTO = competitionServicesOfferMapper.toDto(competitionServicesOffer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitionServicesOfferMockMvc.perform(post("/api/competition-services-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionServicesOfferDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitionServicesOffer in the database
        List<CompetitionServicesOffer> competitionServicesOfferList = competitionServicesOfferRepository.findAll();
        assertThat(competitionServicesOfferList).hasSize(databaseSizeBeforeCreate);

        // Validate the CompetitionServicesOffer in Elasticsearch
        verify(mockCompetitionServicesOfferSearchRepository, times(0)).save(competitionServicesOffer);
    }


    @Test
    @Transactional
    public void checkCompetitionServicesOfferNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitionServicesOfferRepository.findAll().size();
        // set the field null
        competitionServicesOffer.setCompetitionServicesOfferName(null);

        // Create the CompetitionServicesOffer, which fails.
        CompetitionServicesOfferDTO competitionServicesOfferDTO = competitionServicesOfferMapper.toDto(competitionServicesOffer);

        restCompetitionServicesOfferMockMvc.perform(post("/api/competition-services-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionServicesOfferDTO)))
            .andExpect(status().isBadRequest());

        List<CompetitionServicesOffer> competitionServicesOfferList = competitionServicesOfferRepository.findAll();
        assertThat(competitionServicesOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompetitionServicesOfferUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitionServicesOfferRepository.findAll().size();
        // set the field null
        competitionServicesOffer.setCompetitionServicesOfferUrl(null);

        // Create the CompetitionServicesOffer, which fails.
        CompetitionServicesOfferDTO competitionServicesOfferDTO = competitionServicesOfferMapper.toDto(competitionServicesOffer);

        restCompetitionServicesOfferMockMvc.perform(post("/api/competition-services-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionServicesOfferDTO)))
            .andExpect(status().isBadRequest());

        List<CompetitionServicesOffer> competitionServicesOfferList = competitionServicesOfferRepository.findAll();
        assertThat(competitionServicesOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompetitionServicesOffers() throws Exception {
        // Initialize the database
        competitionServicesOfferRepository.saveAndFlush(competitionServicesOffer);

        // Get all the competitionServicesOfferList
        restCompetitionServicesOfferMockMvc.perform(get("/api/competition-services-offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitionServicesOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].competitionServicesOfferName").value(hasItem(DEFAULT_COMPETITION_SERVICES_OFFER_NAME)))
            .andExpect(jsonPath("$.[*].competitionServicesOfferDetail").value(hasItem(DEFAULT_COMPETITION_SERVICES_OFFER_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].competitionServicesOfferUrl").value(hasItem(DEFAULT_COMPETITION_SERVICES_OFFER_URL)));
    }
    
    @Test
    @Transactional
    public void getCompetitionServicesOffer() throws Exception {
        // Initialize the database
        competitionServicesOfferRepository.saveAndFlush(competitionServicesOffer);

        // Get the competitionServicesOffer
        restCompetitionServicesOfferMockMvc.perform(get("/api/competition-services-offers/{id}", competitionServicesOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competitionServicesOffer.getId().intValue()))
            .andExpect(jsonPath("$.competitionServicesOfferName").value(DEFAULT_COMPETITION_SERVICES_OFFER_NAME))
            .andExpect(jsonPath("$.competitionServicesOfferDetail").value(DEFAULT_COMPETITION_SERVICES_OFFER_DETAIL.toString()))
            .andExpect(jsonPath("$.competitionServicesOfferUrl").value(DEFAULT_COMPETITION_SERVICES_OFFER_URL));
    }

    @Test
    @Transactional
    public void getNonExistingCompetitionServicesOffer() throws Exception {
        // Get the competitionServicesOffer
        restCompetitionServicesOfferMockMvc.perform(get("/api/competition-services-offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitionServicesOffer() throws Exception {
        // Initialize the database
        competitionServicesOfferRepository.saveAndFlush(competitionServicesOffer);

        int databaseSizeBeforeUpdate = competitionServicesOfferRepository.findAll().size();

        // Update the competitionServicesOffer
        CompetitionServicesOffer updatedCompetitionServicesOffer = competitionServicesOfferRepository.findById(competitionServicesOffer.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitionServicesOffer are not directly saved in db
        em.detach(updatedCompetitionServicesOffer);
        updatedCompetitionServicesOffer
            .competitionServicesOfferName(UPDATED_COMPETITION_SERVICES_OFFER_NAME)
            .competitionServicesOfferDetail(UPDATED_COMPETITION_SERVICES_OFFER_DETAIL)
            .competitionServicesOfferUrl(UPDATED_COMPETITION_SERVICES_OFFER_URL);
        CompetitionServicesOfferDTO competitionServicesOfferDTO = competitionServicesOfferMapper.toDto(updatedCompetitionServicesOffer);

        restCompetitionServicesOfferMockMvc.perform(put("/api/competition-services-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionServicesOfferDTO)))
            .andExpect(status().isOk());

        // Validate the CompetitionServicesOffer in the database
        List<CompetitionServicesOffer> competitionServicesOfferList = competitionServicesOfferRepository.findAll();
        assertThat(competitionServicesOfferList).hasSize(databaseSizeBeforeUpdate);
        CompetitionServicesOffer testCompetitionServicesOffer = competitionServicesOfferList.get(competitionServicesOfferList.size() - 1);
        assertThat(testCompetitionServicesOffer.getCompetitionServicesOfferName()).isEqualTo(UPDATED_COMPETITION_SERVICES_OFFER_NAME);
        assertThat(testCompetitionServicesOffer.getCompetitionServicesOfferDetail()).isEqualTo(UPDATED_COMPETITION_SERVICES_OFFER_DETAIL);
        assertThat(testCompetitionServicesOffer.getCompetitionServicesOfferUrl()).isEqualTo(UPDATED_COMPETITION_SERVICES_OFFER_URL);

        // Validate the CompetitionServicesOffer in Elasticsearch
        verify(mockCompetitionServicesOfferSearchRepository, times(1)).save(testCompetitionServicesOffer);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetitionServicesOffer() throws Exception {
        int databaseSizeBeforeUpdate = competitionServicesOfferRepository.findAll().size();

        // Create the CompetitionServicesOffer
        CompetitionServicesOfferDTO competitionServicesOfferDTO = competitionServicesOfferMapper.toDto(competitionServicesOffer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitionServicesOfferMockMvc.perform(put("/api/competition-services-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionServicesOfferDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitionServicesOffer in the database
        List<CompetitionServicesOffer> competitionServicesOfferList = competitionServicesOfferRepository.findAll();
        assertThat(competitionServicesOfferList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CompetitionServicesOffer in Elasticsearch
        verify(mockCompetitionServicesOfferSearchRepository, times(0)).save(competitionServicesOffer);
    }

    @Test
    @Transactional
    public void deleteCompetitionServicesOffer() throws Exception {
        // Initialize the database
        competitionServicesOfferRepository.saveAndFlush(competitionServicesOffer);

        int databaseSizeBeforeDelete = competitionServicesOfferRepository.findAll().size();

        // Delete the competitionServicesOffer
        restCompetitionServicesOfferMockMvc.perform(delete("/api/competition-services-offers/{id}", competitionServicesOffer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompetitionServicesOffer> competitionServicesOfferList = competitionServicesOfferRepository.findAll();
        assertThat(competitionServicesOfferList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CompetitionServicesOffer in Elasticsearch
        verify(mockCompetitionServicesOfferSearchRepository, times(1)).deleteById(competitionServicesOffer.getId());
    }

    @Test
    @Transactional
    public void searchCompetitionServicesOffer() throws Exception {
        // Initialize the database
        competitionServicesOfferRepository.saveAndFlush(competitionServicesOffer);
        when(mockCompetitionServicesOfferSearchRepository.search(queryStringQuery("id:" + competitionServicesOffer.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(competitionServicesOffer), PageRequest.of(0, 1), 1));
        // Search the competitionServicesOffer
        restCompetitionServicesOfferMockMvc.perform(get("/api/_search/competition-services-offers?query=id:" + competitionServicesOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitionServicesOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].competitionServicesOfferName").value(hasItem(DEFAULT_COMPETITION_SERVICES_OFFER_NAME)))
            .andExpect(jsonPath("$.[*].competitionServicesOfferDetail").value(hasItem(DEFAULT_COMPETITION_SERVICES_OFFER_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].competitionServicesOfferUrl").value(hasItem(DEFAULT_COMPETITION_SERVICES_OFFER_URL)));
    }
}
