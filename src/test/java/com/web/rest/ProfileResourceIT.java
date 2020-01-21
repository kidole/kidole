package com.web.rest;

import com.KidoleApp;
import com.domain.Profile;
import com.repository.ProfileRepository;
import com.service.ProfileService;
import com.service.dto.ProfileDTO;
import com.service.mapper.ProfileMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.domain.enumeration.Gender;
import com.domain.enumeration.AccreditationList;
/**
 * Integration tests for the {@link ProfileResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class ProfileResourceIT {

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IS_ACCREDITATED = false;
    private static final Boolean UPDATED_IS_ACCREDITATED = true;

    private static final AccreditationList DEFAULT_ACCREDITATIONTYPE = AccreditationList.ATHLETE;
    private static final AccreditationList UPDATED_ACCREDITATIONTYPE = AccreditationList.SPARING;

    private static final Instant DEFAULT_DATE_OF_BIRTH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_BIRTH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PLACE_OF_BBIRTH = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_OF_BBIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_CLUB_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_CLUB_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    private static final String DEFAULT_MANUALITY = "AAAAAAAAAA";
    private static final String UPDATED_MANUALITY = "BBBBBBBBBB";

    private static final String DEFAULT_NIC = "AAAAAAAAAA";
    private static final String UPDATED_NIC = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_DISCIPLINE = "AAAAAAAAAA";
    private static final String UPDATED_DISCIPLINE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_TEAM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FONCTION = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENT_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_PRESS_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRESS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRESS_AGENCE = "AAAAAAAAAA";
    private static final String UPDATED_PRESS_AGENCE = "BBBBBBBBBB";

    private static final String DEFAULT_BATAILLON_RATTACHEMENT = "AAAAAAAAAA";
    private static final String UPDATED_BATAILLON_RATTACHEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIAL_DENOMINATION = "AAAAAAAAAA";
    private static final String UPDATED_SOCIAL_DENOMINATION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_BUILDING = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_BUILDING = "BBBBBBBBBB";

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private ProfileService profileService;

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

    private MockMvc restProfileMockMvc;

    private Profile profile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileResource profileResource = new ProfileResource(profileService);
        this.restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
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
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
            .gender(DEFAULT_GENDER)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .isAccreditated(DEFAULT_IS_ACCREDITATED)
            .accreditationtype(DEFAULT_ACCREDITATIONTYPE)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .placeOfBbirth(DEFAULT_PLACE_OF_BBIRTH)
            .clubOrigin(DEFAULT_CLUB_ORIGIN)
            .nationality(DEFAULT_NATIONALITY)
            .height(DEFAULT_HEIGHT)
            .weight(DEFAULT_WEIGHT)
            .manuality(DEFAULT_MANUALITY)
            .nic(DEFAULT_NIC)
            .phone(DEFAULT_PHONE)
            .discipline(DEFAULT_DISCIPLINE)
            .categorie(DEFAULT_CATEGORIE)
            .teamName(DEFAULT_TEAM_NAME)
            .fonction(DEFAULT_FONCTION)
            .titre(DEFAULT_TITRE)
            .residentCity(DEFAULT_RESIDENT_CITY)
            .pressID(DEFAULT_PRESS_ID)
            .pressAgence(DEFAULT_PRESS_AGENCE)
            .bataillonRattachement(DEFAULT_BATAILLON_RATTACHEMENT)
            .socialDenomination(DEFAULT_SOCIAL_DENOMINATION)
            .locationBuilding(DEFAULT_LOCATION_BUILDING);
        return profile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createUpdatedEntity(EntityManager em) {
        Profile profile = new Profile()
            .gender(UPDATED_GENDER)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .isAccreditated(UPDATED_IS_ACCREDITATED)
            .accreditationtype(UPDATED_ACCREDITATIONTYPE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .placeOfBbirth(UPDATED_PLACE_OF_BBIRTH)
            .clubOrigin(UPDATED_CLUB_ORIGIN)
            .nationality(UPDATED_NATIONALITY)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .manuality(UPDATED_MANUALITY)
            .nic(UPDATED_NIC)
            .phone(UPDATED_PHONE)
            .discipline(UPDATED_DISCIPLINE)
            .categorie(UPDATED_CATEGORIE)
            .teamName(UPDATED_TEAM_NAME)
            .fonction(UPDATED_FONCTION)
            .titre(UPDATED_TITRE)
            .residentCity(UPDATED_RESIDENT_CITY)
            .pressID(UPDATED_PRESS_ID)
            .pressAgence(UPDATED_PRESS_AGENCE)
            .bataillonRattachement(UPDATED_BATAILLON_RATTACHEMENT)
            .socialDenomination(UPDATED_SOCIAL_DENOMINATION)
            .locationBuilding(UPDATED_LOCATION_BUILDING);
        return profile;
    }

    @BeforeEach
    public void initTest() {
        profile = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testProfile.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testProfile.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testProfile.isIsAccreditated()).isEqualTo(DEFAULT_IS_ACCREDITATED);
        assertThat(testProfile.getAccreditationtype()).isEqualTo(DEFAULT_ACCREDITATIONTYPE);
        assertThat(testProfile.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testProfile.getPlaceOfBbirth()).isEqualTo(DEFAULT_PLACE_OF_BBIRTH);
        assertThat(testProfile.getClubOrigin()).isEqualTo(DEFAULT_CLUB_ORIGIN);
        assertThat(testProfile.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testProfile.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testProfile.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testProfile.getManuality()).isEqualTo(DEFAULT_MANUALITY);
        assertThat(testProfile.getNic()).isEqualTo(DEFAULT_NIC);
        assertThat(testProfile.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testProfile.getDiscipline()).isEqualTo(DEFAULT_DISCIPLINE);
        assertThat(testProfile.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
        assertThat(testProfile.getTeamName()).isEqualTo(DEFAULT_TEAM_NAME);
        assertThat(testProfile.getFonction()).isEqualTo(DEFAULT_FONCTION);
        assertThat(testProfile.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testProfile.getResidentCity()).isEqualTo(DEFAULT_RESIDENT_CITY);
        assertThat(testProfile.getPressID()).isEqualTo(DEFAULT_PRESS_ID);
        assertThat(testProfile.getPressAgence()).isEqualTo(DEFAULT_PRESS_AGENCE);
        assertThat(testProfile.getBataillonRattachement()).isEqualTo(DEFAULT_BATAILLON_RATTACHEMENT);
        assertThat(testProfile.getSocialDenomination()).isEqualTo(DEFAULT_SOCIAL_DENOMINATION);
        assertThat(testProfile.getLocationBuilding()).isEqualTo(DEFAULT_LOCATION_BUILDING);
    }

    @Test
    @Transactional
    public void createProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile with an existing ID
        profile.setId(1L);
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setGender(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].isAccreditated").value(hasItem(DEFAULT_IS_ACCREDITATED.booleanValue())))
            .andExpect(jsonPath("$.[*].accreditationtype").value(hasItem(DEFAULT_ACCREDITATIONTYPE.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].placeOfBbirth").value(hasItem(DEFAULT_PLACE_OF_BBIRTH)))
            .andExpect(jsonPath("$.[*].clubOrigin").value(hasItem(DEFAULT_CLUB_ORIGIN)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].manuality").value(hasItem(DEFAULT_MANUALITY)))
            .andExpect(jsonPath("$.[*].nic").value(hasItem(DEFAULT_NIC)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].discipline").value(hasItem(DEFAULT_DISCIPLINE)))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE)))
            .andExpect(jsonPath("$.[*].teamName").value(hasItem(DEFAULT_TEAM_NAME)))
            .andExpect(jsonPath("$.[*].fonction").value(hasItem(DEFAULT_FONCTION)))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].residentCity").value(hasItem(DEFAULT_RESIDENT_CITY)))
            .andExpect(jsonPath("$.[*].pressID").value(hasItem(DEFAULT_PRESS_ID)))
            .andExpect(jsonPath("$.[*].pressAgence").value(hasItem(DEFAULT_PRESS_AGENCE)))
            .andExpect(jsonPath("$.[*].bataillonRattachement").value(hasItem(DEFAULT_BATAILLON_RATTACHEMENT)))
            .andExpect(jsonPath("$.[*].socialDenomination").value(hasItem(DEFAULT_SOCIAL_DENOMINATION)))
            .andExpect(jsonPath("$.[*].locationBuilding").value(hasItem(DEFAULT_LOCATION_BUILDING)));
    }
    
    @Test
    @Transactional
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.isAccreditated").value(DEFAULT_IS_ACCREDITATED.booleanValue()))
            .andExpect(jsonPath("$.accreditationtype").value(DEFAULT_ACCREDITATIONTYPE.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.placeOfBbirth").value(DEFAULT_PLACE_OF_BBIRTH))
            .andExpect(jsonPath("$.clubOrigin").value(DEFAULT_CLUB_ORIGIN))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.manuality").value(DEFAULT_MANUALITY))
            .andExpect(jsonPath("$.nic").value(DEFAULT_NIC))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.discipline").value(DEFAULT_DISCIPLINE))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE))
            .andExpect(jsonPath("$.teamName").value(DEFAULT_TEAM_NAME))
            .andExpect(jsonPath("$.fonction").value(DEFAULT_FONCTION))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.residentCity").value(DEFAULT_RESIDENT_CITY))
            .andExpect(jsonPath("$.pressID").value(DEFAULT_PRESS_ID))
            .andExpect(jsonPath("$.pressAgence").value(DEFAULT_PRESS_AGENCE))
            .andExpect(jsonPath("$.bataillonRattachement").value(DEFAULT_BATAILLON_RATTACHEMENT))
            .andExpect(jsonPath("$.socialDenomination").value(DEFAULT_SOCIAL_DENOMINATION))
            .andExpect(jsonPath("$.locationBuilding").value(DEFAULT_LOCATION_BUILDING));
    }

    @Test
    @Transactional
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);
        updatedProfile
            .gender(UPDATED_GENDER)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .isAccreditated(UPDATED_IS_ACCREDITATED)
            .accreditationtype(UPDATED_ACCREDITATIONTYPE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .placeOfBbirth(UPDATED_PLACE_OF_BBIRTH)
            .clubOrigin(UPDATED_CLUB_ORIGIN)
            .nationality(UPDATED_NATIONALITY)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .manuality(UPDATED_MANUALITY)
            .nic(UPDATED_NIC)
            .phone(UPDATED_PHONE)
            .discipline(UPDATED_DISCIPLINE)
            .categorie(UPDATED_CATEGORIE)
            .teamName(UPDATED_TEAM_NAME)
            .fonction(UPDATED_FONCTION)
            .titre(UPDATED_TITRE)
            .residentCity(UPDATED_RESIDENT_CITY)
            .pressID(UPDATED_PRESS_ID)
            .pressAgence(UPDATED_PRESS_AGENCE)
            .bataillonRattachement(UPDATED_BATAILLON_RATTACHEMENT)
            .socialDenomination(UPDATED_SOCIAL_DENOMINATION)
            .locationBuilding(UPDATED_LOCATION_BUILDING);
        ProfileDTO profileDTO = profileMapper.toDto(updatedProfile);

        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testProfile.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testProfile.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testProfile.isIsAccreditated()).isEqualTo(UPDATED_IS_ACCREDITATED);
        assertThat(testProfile.getAccreditationtype()).isEqualTo(UPDATED_ACCREDITATIONTYPE);
        assertThat(testProfile.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testProfile.getPlaceOfBbirth()).isEqualTo(UPDATED_PLACE_OF_BBIRTH);
        assertThat(testProfile.getClubOrigin()).isEqualTo(UPDATED_CLUB_ORIGIN);
        assertThat(testProfile.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testProfile.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testProfile.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testProfile.getManuality()).isEqualTo(UPDATED_MANUALITY);
        assertThat(testProfile.getNic()).isEqualTo(UPDATED_NIC);
        assertThat(testProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testProfile.getDiscipline()).isEqualTo(UPDATED_DISCIPLINE);
        assertThat(testProfile.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testProfile.getTeamName()).isEqualTo(UPDATED_TEAM_NAME);
        assertThat(testProfile.getFonction()).isEqualTo(UPDATED_FONCTION);
        assertThat(testProfile.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testProfile.getResidentCity()).isEqualTo(UPDATED_RESIDENT_CITY);
        assertThat(testProfile.getPressID()).isEqualTo(UPDATED_PRESS_ID);
        assertThat(testProfile.getPressAgence()).isEqualTo(UPDATED_PRESS_AGENCE);
        assertThat(testProfile.getBataillonRattachement()).isEqualTo(UPDATED_BATAILLON_RATTACHEMENT);
        assertThat(testProfile.getSocialDenomination()).isEqualTo(UPDATED_SOCIAL_DENOMINATION);
        assertThat(testProfile.getLocationBuilding()).isEqualTo(UPDATED_LOCATION_BUILDING);
    }

    @Test
    @Transactional
    public void updateNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Delete the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
