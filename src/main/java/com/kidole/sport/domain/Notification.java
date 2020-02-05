package com.kidole.sport.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.kidole.sport.domain.enumeration.NotificationState;

import com.kidole.sport.domain.enumeration.NotificationType;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "notification")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "notification_title", length = 1024, nullable = false)
    private String notificationTitle;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "notification_subject", length = 1024, nullable = false)
    private String notificationSubject;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "notification_url", length = 1024, nullable = false)
    private String notificationUrl;

    
    @Lob
    @Column(name = "notification_image", nullable = false)
    private byte[] notificationImage;

    @Column(name = "notification_image_content_type", nullable = false)
    private String notificationImageContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_status", nullable = false)
    private NotificationState notificationStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    private NotificationType notificationType;

    @ManyToOne
    @JsonIgnoreProperties("notifications")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public Notification notificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
        return this;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationSubject() {
        return notificationSubject;
    }

    public Notification notificationSubject(String notificationSubject) {
        this.notificationSubject = notificationSubject;
        return this;
    }

    public void setNotificationSubject(String notificationSubject) {
        this.notificationSubject = notificationSubject;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public Notification notificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
        return this;
    }

    public void setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    public byte[] getNotificationImage() {
        return notificationImage;
    }

    public Notification notificationImage(byte[] notificationImage) {
        this.notificationImage = notificationImage;
        return this;
    }

    public void setNotificationImage(byte[] notificationImage) {
        this.notificationImage = notificationImage;
    }

    public String getNotificationImageContentType() {
        return notificationImageContentType;
    }

    public Notification notificationImageContentType(String notificationImageContentType) {
        this.notificationImageContentType = notificationImageContentType;
        return this;
    }

    public void setNotificationImageContentType(String notificationImageContentType) {
        this.notificationImageContentType = notificationImageContentType;
    }

    public NotificationState getNotificationStatus() {
        return notificationStatus;
    }

    public Notification notificationStatus(NotificationState notificationStatus) {
        this.notificationStatus = notificationStatus;
        return this;
    }

    public void setNotificationStatus(NotificationState notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public Notification notificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
        return this;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public User getUser() {
        return user;
    }

    public Notification user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notification)) {
            return false;
        }
        return id != null && id.equals(((Notification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Notification{" +
            "id=" + getId() +
            ", notificationTitle='" + getNotificationTitle() + "'" +
            ", notificationSubject='" + getNotificationSubject() + "'" +
            ", notificationUrl='" + getNotificationUrl() + "'" +
            ", notificationImage='" + getNotificationImage() + "'" +
            ", notificationImageContentType='" + getNotificationImageContentType() + "'" +
            ", notificationStatus='" + getNotificationStatus() + "'" +
            ", notificationType='" + getNotificationType() + "'" +
            "}";
    }
}
