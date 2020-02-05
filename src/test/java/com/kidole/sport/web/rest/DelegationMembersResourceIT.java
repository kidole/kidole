package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.DelegationMembers;
import com.kidole.sport.repository.DelegationMembersRepository;
import com.kidole.sport.repository.search.DelegationMembersSearchRepository;
import com.kidole.sport.service.DelegationMembersService;
import com.kidole.sport.service.dto.DelegationMembersDTO;
import com.kidole.sport.service.mapper.DelegationMembersMapper;
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
 * Integration tests for the {@link DelegationMembersResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class DelegationMembersResourceIT {

    private static final String DEFAULT_DELEGATION_MEMBERS_STATE = "AAAAAAAAAA";
    private static final String UPDATED_DELEGATION_MEMBERS_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_DELEGATION_MEMBERS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DELEGATION_MEMBERS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DELEGATION_MEMBERS_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DELEGATION_MEMBERS_DETAIL = "BBBBBBBBBB";

    @Autowired
    private DelegationMembersRepository delegationMembersRepository;

    @Autowired
    private DelegationMembersMapper delegationMembersMapper;

    @Autowired
    private DelegationMembersService delegationMembersService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.DelegationMembersSearchRepositoryMockConfiguration
     */
    @Autowired
    private DelegationMembersSearchRepository mockDelegationMembersSearchRepository;

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

    private MockMvc restDelegationMembersMockMvc;

    private DelegationMembers delegationMembers;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DelegationMembersResource delegationMembersResource = new DelegationMembersResource(delegationMembersService);
        this.restDelegationMembersMockMvc = MockMvcBuilders.standaloneSetup(delegationMembersResource)
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
    public static DelegationMembers createEntity(EntityManager em) {
        DelegationMembers delegationMembers = new DelegationMembers()
            .delegationMembersState(DEFAULT_DELEGATION_MEMBERS_STATE)
            .delegationMembersCode(DEFAULT_DELEGATION_MEMBERS_CODE)
            .delegationMembersDetail(DEFAULT_DELEGATION_MEMBERS_DETAIL);
        return delegationMembers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DelegationMembers createUpdatedEntity(EntityManager em) {
        DelegationMembers delegationMembers = new DelegationMembers()
            .delegationMembersState(UPDATED_DELEGATION_MEMBERS_STATE)
            .delegationMembersCode(UPDATED_DELEGATION_MEMBERS_CODE)
            .delegationMembersDetail(UPDATED_DELEGATION_MEMBERS_DETAIL);
        return delegationMembers;
    }

    @BeforeEach
    public void initTest() {
        delegationMembers = createEntity(em);
    }

    @Test
    @Transactional
    public void createDelegationMembers() throws Exception {
        int databaseSizeBeforeCreate = delegationMembersRepository.findAll().size();

        // Create the DelegationMembers
        DelegationMembersDTO delegationMembersDTO = delegationMembersMapper.toDto(delegationMembers);
        restDelegationMembersMockMvc.perform(post("/api/delegation-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegationMembersDTO)))
            .andExpect(status().isCreated());

        // Validate the DelegationMembers in the database
        List<DelegationMembers> delegationMembersList = delegationMembersRepository.findAll();
        assertThat(delegationMembersList).hasSize(databaseSizeBeforeCreate + 1);
        DelegationMembers testDelegationMembers = delegationMembersList.get(delegationMembersList.size() - 1);
        assertThat(testDelegationMembers.getDelegationMembersState()).isEqualTo(DEFAULT_DELEGATION_MEMBERS_STATE);
        assertThat(testDelegationMembers.getDelegationMembersCode()).isEqualTo(DEFAULT_DELEGATION_MEMBERS_CODE);
        assertThat(testDelegationMembers.getDelegationMembersDetail()).isEqualTo(DEFAULT_DELEGATION_MEMBERS_DETAIL);

        // Validate the DelegationMembers in Elasticsearch
        verify(mockDelegationMembersSearchRepository, times(1)).save(testDelegationMembers);
    }

    @Test
    @Transactional
    public void createDelegationMembersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = delegationMembersRepository.findAll().size();

        // Create the DelegationMembers with an existing ID
        delegationMembers.setId(1L);
        DelegationMembersDTO delegationMembersDTO = delegationMembersMapper.toDto(delegationMembers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDelegationMembersMockMvc.perform(post("/api/delegation-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegationMembersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DelegationMembers in the database
        List<DelegationMembers> delegationMembersList = delegationMembersRepository.findAll();
        assertThat(delegationMembersList).hasSize(databaseSizeBeforeCreate);

        // Validate the DelegationMembers in Elasticsearch
        verify(mockDelegationMembersSearchRepository, times(0)).save(delegationMembers);
    }


    @Test
    @Transactional
    public void checkDelegationMembersStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = delegationMembersRepository.findAll().size();
        // set the field null
        delegationMembers.setDelegationMembersState(null);

        // Create the DelegationMembers, which fails.
        DelegationMembersDTO delegationMembersDTO = delegationMembersMapper.toDto(delegationMembers);

        restDelegationMembersMockMvc.perform(post("/api/delegation-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegationMembersDTO)))
            .andExpect(status().isBadRequest());

        List<DelegationMembers> delegationMembersList = delegationMembersRepository.findAll();
        assertThat(delegationMembersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDelegationMembersCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = delegationMembersRepository.findAll().size();
        // set the field null
        delegationMembers.setDelegationMembersCode(null);

        // Create the DelegationMembers, which fails.
        DelegationMembersDTO delegationMembersDTO = delegationMembersMapper.toDto(delegationMembers);

        restDelegationMembersMockMvc.perform(post("/api/delegation-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegationMembersDTO)))
            .andExpect(status().isBadRequest());

        List<DelegationMembers> delegationMembersList = delegationMembersRepository.findAll();
        assertThat(delegationMembersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDelegationMembers() throws Exception {
        // Initialize the database
        delegationMembersRepository.saveAndFlush(delegationMembers);

        // Get all the delegationMembersList
        restDelegationMembersMockMvc.perform(get("/api/delegation-members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(delegationMembers.getId().intValue())))
            .andExpect(jsonPath("$.[*].delegationMembersState").value(hasItem(DEFAULT_DELEGATION_MEMBERS_STATE)))
            .andExpect(jsonPath("$.[*].delegationMembersCode").value(hasItem(DEFAULT_DELEGATION_MEMBERS_CODE)))
            .andExpect(jsonPath("$.[*].delegationMembersDetail").value(hasItem(DEFAULT_DELEGATION_MEMBERS_DETAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getDelegationMembers() throws Exception {
        // Initialize the database
        delegationMembersRepository.saveAndFlush(delegationMembers);

        // Get the delegationMembers
        restDelegationMembersMockMvc.perform(get("/api/delegation-members/{id}", delegationMembers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(delegationMembers.getId().intValue()))
            .andExpect(jsonPath("$.delegationMembersState").value(DEFAULT_DELEGATION_MEMBERS_STATE))
            .andExpect(jsonPath("$.delegationMembersCode").value(DEFAULT_DELEGATION_MEMBERS_CODE))
            .andExpect(jsonPath("$.delegationMembersDetail").value(DEFAULT_DELEGATION_MEMBERS_DETAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDelegationMembers() throws Exception {
        // Get the delegationMembers
        restDelegationMembersMockMvc.perform(get("/api/delegation-members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDelegationMembers() throws Exception {
        // Initialize the database
        delegationMembersRepository.saveAndFlush(delegationMembers);

        int databaseSizeBeforeUpdate = delegationMembersRepository.findAll().size();

        // Update the delegationMembers
        DelegationMembers updatedDelegationMembers = delegationMembersRepository.findById(delegationMembers.getId()).get();
        // Disconnect from session so that the updates on updatedDelegationMembers are not directly saved in db
        em.detach(updatedDelegationMembers);
        updatedDelegationMembers
            .delegationMembersState(UPDATED_DELEGATION_MEMBERS_STATE)
            .delegationMembersCode(UPDATED_DELEGATION_MEMBERS_CODE)
            .delegationMembersDetail(UPDATED_DELEGATION_MEMBERS_DETAIL);
        DelegationMembersDTO delegationMembersDTO = delegationMembersMapper.toDto(updatedDelegationMembers);

        restDelegationMembersMockMvc.perform(put("/api/delegation-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegationMembersDTO)))
            .andExpect(status().isOk());

        // Validate the DelegationMembers in the database
        List<DelegationMembers> delegationMembersList = delegationMembersRepository.findAll();
        assertThat(delegationMembersList).hasSize(databaseSizeBeforeUpdate);
        DelegationMembers testDelegationMembers = delegationMembersList.get(delegationMembersList.size() - 1);
        assertThat(testDelegationMembers.getDelegationMembersState()).isEqualTo(UPDATED_DELEGATION_MEMBERS_STATE);
        assertThat(testDelegationMembers.getDelegationMembersCode()).isEqualTo(UPDATED_DELEGATION_MEMBERS_CODE);
        assertThat(testDelegationMembers.getDelegationMembersDetail()).isEqualTo(UPDATED_DELEGATION_MEMBERS_DETAIL);

        // Validate the DelegationMembers in Elasticsearch
        verify(mockDelegationMembersSearchRepository, times(1)).save(testDelegationMembers);
    }

    @Test
    @Transactional
    public void updateNonExistingDelegationMembers() throws Exception {
        int databaseSizeBeforeUpdate = delegationMembersRepository.findAll().size();

        // Create the DelegationMembers
        DelegationMembersDTO delegationMembersDTO = delegationMembersMapper.toDto(delegationMembers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDelegationMembersMockMvc.perform(put("/api/delegation-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegationMembersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DelegationMembers in the database
        List<DelegationMembers> delegationMembersList = delegationMembersRepository.findAll();
        assertThat(delegationMembersList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DelegationMembers in Elasticsearch
        verify(mockDelegationMembersSearchRepository, times(0)).save(delegationMembers);
    }

    @Test
    @Transactional
    public void deleteDelegationMembers() throws Exception {
        // Initialize the database
        delegationMembersRepository.saveAndFlush(delegationMembers);

        int databaseSizeBeforeDelete = delegationMembersRepository.findAll().size();

        // Delete the delegationMembers
        restDelegationMembersMockMvc.perform(delete("/api/delegation-members/{id}", delegationMembers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DelegationMembers> delegationMembersList = delegationMembersRepository.findAll();
        assertThat(delegationMembersList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DelegationMembers in Elasticsearch
        verify(mockDelegationMembersSearchRepository, times(1)).deleteById(delegationMembers.getId());
    }

    @Test
    @Transactional
    public void searchDelegationMembers() throws Exception {
        // Initialize the database
        delegationMembersRepository.saveAndFlush(delegationMembers);
        when(mockDelegationMembersSearchRepository.search(queryStringQuery("id:" + delegationMembers.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(delegationMembers), PageRequest.of(0, 1), 1));
        // Search the delegationMembers
        restDelegationMembersMockMvc.perform(get("/api/_search/delegation-members?query=id:" + delegationMembers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(delegationMembers.getId().intValue())))
            .andExpect(jsonPath("$.[*].delegationMembersState").value(hasItem(DEFAULT_DELEGATION_MEMBERS_STATE)))
            .andExpect(jsonPath("$.[*].delegationMembersCode").value(hasItem(DEFAULT_DELEGATION_MEMBERS_CODE)))
            .andExpect(jsonPath("$.[*].delegationMembersDetail").value(hasItem(DEFAULT_DELEGATION_MEMBERS_DETAIL.toString())));
    }
}
