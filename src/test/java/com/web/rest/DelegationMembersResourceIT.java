package com.web.rest;

import com.KidoleApp;
import com.domain.DelegationMembers;
import com.repository.DelegationMembersRepository;
import com.service.DelegationMembersService;
import com.service.dto.DelegationMembersDTO;
import com.service.mapper.DelegationMembersMapper;
import com.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DelegationMembersResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class DelegationMembersResourceIT {

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_1 = "AAAAAAAAAA";
    private static final String UPDATED_CODE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    @Autowired
    private DelegationMembersRepository delegationMembersRepository;

    @Autowired
    private DelegationMembersMapper delegationMembersMapper;

    @Autowired
    private DelegationMembersService delegationMembersService;

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
            .state(DEFAULT_STATE)
            .code1(DEFAULT_CODE_1)
            .detail(DEFAULT_DETAIL);
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
            .state(UPDATED_STATE)
            .code1(UPDATED_CODE_1)
            .detail(UPDATED_DETAIL);
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
        assertThat(testDelegationMembers.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testDelegationMembers.getCode1()).isEqualTo(DEFAULT_CODE_1);
        assertThat(testDelegationMembers.getDetail()).isEqualTo(DEFAULT_DETAIL);
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
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].code1").value(hasItem(DEFAULT_CODE_1)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)));
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
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.code1").value(DEFAULT_CODE_1))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL));
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
            .state(UPDATED_STATE)
            .code1(UPDATED_CODE_1)
            .detail(UPDATED_DETAIL);
        DelegationMembersDTO delegationMembersDTO = delegationMembersMapper.toDto(updatedDelegationMembers);

        restDelegationMembersMockMvc.perform(put("/api/delegation-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegationMembersDTO)))
            .andExpect(status().isOk());

        // Validate the DelegationMembers in the database
        List<DelegationMembers> delegationMembersList = delegationMembersRepository.findAll();
        assertThat(delegationMembersList).hasSize(databaseSizeBeforeUpdate);
        DelegationMembers testDelegationMembers = delegationMembersList.get(delegationMembersList.size() - 1);
        assertThat(testDelegationMembers.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testDelegationMembers.getCode1()).isEqualTo(UPDATED_CODE_1);
        assertThat(testDelegationMembers.getDetail()).isEqualTo(UPDATED_DETAIL);
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
    }
}
