package com.kidole.sport.web.rest;

import com.kidole.sport.KidoleApp;
import com.kidole.sport.domain.Notification;
import com.kidole.sport.repository.NotificationRepository;
import com.kidole.sport.repository.search.NotificationSearchRepository;
import com.kidole.sport.service.NotificationService;
import com.kidole.sport.service.dto.NotificationDTO;
import com.kidole.sport.service.mapper.NotificationMapper;
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

import com.kidole.sport.domain.enumeration.NotificationState;
import com.kidole.sport.domain.enumeration.NotificationType;
/**
 * Integration tests for the {@link NotificationResource} REST controller.
 */
@SpringBootTest(classes = KidoleApp.class)
public class NotificationResourceIT {

    private static final String DEFAULT_NOTIFICATION_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFICATION_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFICATION_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFICATION_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFICATION_URL = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFICATION_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_NOTIFICATION_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_NOTIFICATION_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_NOTIFICATION_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_NOTIFICATION_IMAGE_CONTENT_TYPE = "image/png";

    private static final NotificationState DEFAULT_NOTIFICATION_STATUS = NotificationState.READ;
    private static final NotificationState UPDATED_NOTIFICATION_STATUS = NotificationState.UNREAD;

    private static final NotificationType DEFAULT_NOTIFICATION_TYPE = NotificationType.SMS;
    private static final NotificationType UPDATED_NOTIFICATION_TYPE = NotificationType.EMAIL;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationService notificationService;

    /**
     * This repository is mocked in the com.kidole.sport.repository.search test package.
     *
     * @see com.kidole.sport.repository.search.NotificationSearchRepositoryMockConfiguration
     */
    @Autowired
    private NotificationSearchRepository mockNotificationSearchRepository;

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

    private MockMvc restNotificationMockMvc;

    private Notification notification;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NotificationResource notificationResource = new NotificationResource(notificationService);
        this.restNotificationMockMvc = MockMvcBuilders.standaloneSetup(notificationResource)
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
    public static Notification createEntity(EntityManager em) {
        Notification notification = new Notification()
            .notificationTitle(DEFAULT_NOTIFICATION_TITLE)
            .notificationSubject(DEFAULT_NOTIFICATION_SUBJECT)
            .notificationUrl(DEFAULT_NOTIFICATION_URL)
            .notificationImage(DEFAULT_NOTIFICATION_IMAGE)
            .notificationImageContentType(DEFAULT_NOTIFICATION_IMAGE_CONTENT_TYPE)
            .notificationStatus(DEFAULT_NOTIFICATION_STATUS)
            .notificationType(DEFAULT_NOTIFICATION_TYPE);
        return notification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createUpdatedEntity(EntityManager em) {
        Notification notification = new Notification()
            .notificationTitle(UPDATED_NOTIFICATION_TITLE)
            .notificationSubject(UPDATED_NOTIFICATION_SUBJECT)
            .notificationUrl(UPDATED_NOTIFICATION_URL)
            .notificationImage(UPDATED_NOTIFICATION_IMAGE)
            .notificationImageContentType(UPDATED_NOTIFICATION_IMAGE_CONTENT_TYPE)
            .notificationStatus(UPDATED_NOTIFICATION_STATUS)
            .notificationType(UPDATED_NOTIFICATION_TYPE);
        return notification;
    }

    @BeforeEach
    public void initTest() {
        notification = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotification() throws Exception {
        int databaseSizeBeforeCreate = notificationRepository.findAll().size();

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);
        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate + 1);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getNotificationTitle()).isEqualTo(DEFAULT_NOTIFICATION_TITLE);
        assertThat(testNotification.getNotificationSubject()).isEqualTo(DEFAULT_NOTIFICATION_SUBJECT);
        assertThat(testNotification.getNotificationUrl()).isEqualTo(DEFAULT_NOTIFICATION_URL);
        assertThat(testNotification.getNotificationImage()).isEqualTo(DEFAULT_NOTIFICATION_IMAGE);
        assertThat(testNotification.getNotificationImageContentType()).isEqualTo(DEFAULT_NOTIFICATION_IMAGE_CONTENT_TYPE);
        assertThat(testNotification.getNotificationStatus()).isEqualTo(DEFAULT_NOTIFICATION_STATUS);
        assertThat(testNotification.getNotificationType()).isEqualTo(DEFAULT_NOTIFICATION_TYPE);

        // Validate the Notification in Elasticsearch
        verify(mockNotificationSearchRepository, times(1)).save(testNotification);
    }

    @Test
    @Transactional
    public void createNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificationRepository.findAll().size();

        // Create the Notification with an existing ID
        notification.setId(1L);
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Notification in Elasticsearch
        verify(mockNotificationSearchRepository, times(0)).save(notification);
    }


    @Test
    @Transactional
    public void checkNotificationTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationRepository.findAll().size();
        // set the field null
        notification.setNotificationTitle(null);

        // Create the Notification, which fails.
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificationSubjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationRepository.findAll().size();
        // set the field null
        notification.setNotificationSubject(null);

        // Create the Notification, which fails.
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificationUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationRepository.findAll().size();
        // set the field null
        notification.setNotificationUrl(null);

        // Create the Notification, which fails.
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificationStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationRepository.findAll().size();
        // set the field null
        notification.setNotificationStatus(null);

        // Create the Notification, which fails.
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationRepository.findAll().size();
        // set the field null
        notification.setNotificationType(null);

        // Create the Notification, which fails.
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotifications() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList
        restNotificationMockMvc.perform(get("/api/notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId().intValue())))
            .andExpect(jsonPath("$.[*].notificationTitle").value(hasItem(DEFAULT_NOTIFICATION_TITLE)))
            .andExpect(jsonPath("$.[*].notificationSubject").value(hasItem(DEFAULT_NOTIFICATION_SUBJECT)))
            .andExpect(jsonPath("$.[*].notificationUrl").value(hasItem(DEFAULT_NOTIFICATION_URL)))
            .andExpect(jsonPath("$.[*].notificationImageContentType").value(hasItem(DEFAULT_NOTIFICATION_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].notificationImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_NOTIFICATION_IMAGE))))
            .andExpect(jsonPath("$.[*].notificationStatus").value(hasItem(DEFAULT_NOTIFICATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].notificationType").value(hasItem(DEFAULT_NOTIFICATION_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get the notification
        restNotificationMockMvc.perform(get("/api/notifications/{id}", notification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(notification.getId().intValue()))
            .andExpect(jsonPath("$.notificationTitle").value(DEFAULT_NOTIFICATION_TITLE))
            .andExpect(jsonPath("$.notificationSubject").value(DEFAULT_NOTIFICATION_SUBJECT))
            .andExpect(jsonPath("$.notificationUrl").value(DEFAULT_NOTIFICATION_URL))
            .andExpect(jsonPath("$.notificationImageContentType").value(DEFAULT_NOTIFICATION_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.notificationImage").value(Base64Utils.encodeToString(DEFAULT_NOTIFICATION_IMAGE)))
            .andExpect(jsonPath("$.notificationStatus").value(DEFAULT_NOTIFICATION_STATUS.toString()))
            .andExpect(jsonPath("$.notificationType").value(DEFAULT_NOTIFICATION_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNotification() throws Exception {
        // Get the notification
        restNotificationMockMvc.perform(get("/api/notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Update the notification
        Notification updatedNotification = notificationRepository.findById(notification.getId()).get();
        // Disconnect from session so that the updates on updatedNotification are not directly saved in db
        em.detach(updatedNotification);
        updatedNotification
            .notificationTitle(UPDATED_NOTIFICATION_TITLE)
            .notificationSubject(UPDATED_NOTIFICATION_SUBJECT)
            .notificationUrl(UPDATED_NOTIFICATION_URL)
            .notificationImage(UPDATED_NOTIFICATION_IMAGE)
            .notificationImageContentType(UPDATED_NOTIFICATION_IMAGE_CONTENT_TYPE)
            .notificationStatus(UPDATED_NOTIFICATION_STATUS)
            .notificationType(UPDATED_NOTIFICATION_TYPE);
        NotificationDTO notificationDTO = notificationMapper.toDto(updatedNotification);

        restNotificationMockMvc.perform(put("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isOk());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getNotificationTitle()).isEqualTo(UPDATED_NOTIFICATION_TITLE);
        assertThat(testNotification.getNotificationSubject()).isEqualTo(UPDATED_NOTIFICATION_SUBJECT);
        assertThat(testNotification.getNotificationUrl()).isEqualTo(UPDATED_NOTIFICATION_URL);
        assertThat(testNotification.getNotificationImage()).isEqualTo(UPDATED_NOTIFICATION_IMAGE);
        assertThat(testNotification.getNotificationImageContentType()).isEqualTo(UPDATED_NOTIFICATION_IMAGE_CONTENT_TYPE);
        assertThat(testNotification.getNotificationStatus()).isEqualTo(UPDATED_NOTIFICATION_STATUS);
        assertThat(testNotification.getNotificationType()).isEqualTo(UPDATED_NOTIFICATION_TYPE);

        // Validate the Notification in Elasticsearch
        verify(mockNotificationSearchRepository, times(1)).save(testNotification);
    }

    @Test
    @Transactional
    public void updateNonExistingNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationMockMvc.perform(put("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Notification in Elasticsearch
        verify(mockNotificationSearchRepository, times(0)).save(notification);
    }

    @Test
    @Transactional
    public void deleteNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        int databaseSizeBeforeDelete = notificationRepository.findAll().size();

        // Delete the notification
        restNotificationMockMvc.perform(delete("/api/notifications/{id}", notification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Notification in Elasticsearch
        verify(mockNotificationSearchRepository, times(1)).deleteById(notification.getId());
    }

    @Test
    @Transactional
    public void searchNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);
        when(mockNotificationSearchRepository.search(queryStringQuery("id:" + notification.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(notification), PageRequest.of(0, 1), 1));
        // Search the notification
        restNotificationMockMvc.perform(get("/api/_search/notifications?query=id:" + notification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId().intValue())))
            .andExpect(jsonPath("$.[*].notificationTitle").value(hasItem(DEFAULT_NOTIFICATION_TITLE)))
            .andExpect(jsonPath("$.[*].notificationSubject").value(hasItem(DEFAULT_NOTIFICATION_SUBJECT)))
            .andExpect(jsonPath("$.[*].notificationUrl").value(hasItem(DEFAULT_NOTIFICATION_URL)))
            .andExpect(jsonPath("$.[*].notificationImageContentType").value(hasItem(DEFAULT_NOTIFICATION_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].notificationImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_NOTIFICATION_IMAGE))))
            .andExpect(jsonPath("$.[*].notificationStatus").value(hasItem(DEFAULT_NOTIFICATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].notificationType").value(hasItem(DEFAULT_NOTIFICATION_TYPE.toString())));
    }
}
