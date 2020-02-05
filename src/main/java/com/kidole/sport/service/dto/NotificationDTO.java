package com.kidole.sport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.kidole.sport.domain.enumeration.NotificationState;
import com.kidole.sport.domain.enumeration.NotificationType;

/**
 * A DTO for the {@link com.kidole.sport.domain.Notification} entity.
 */
public class NotificationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String notificationTitle;

    @NotNull
    @Size(min = 3, max = 1024)
    private String notificationSubject;

    @NotNull
    @Size(min = 3, max = 1024)
    private String notificationUrl;

    
    @Lob
    private byte[] notificationImage;

    private String notificationImageContentType;
    @NotNull
    private NotificationState notificationStatus;

    @NotNull
    private NotificationType notificationType;


    private Long userId;

    private String userFirstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationSubject() {
        return notificationSubject;
    }

    public void setNotificationSubject(String notificationSubject) {
        this.notificationSubject = notificationSubject;
    }

    public String getNotificationUrl() {
        return notificationUrl;
    }

    public void setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    public byte[] getNotificationImage() {
        return notificationImage;
    }

    public void setNotificationImage(byte[] notificationImage) {
        this.notificationImage = notificationImage;
    }

    public String getNotificationImageContentType() {
        return notificationImageContentType;
    }

    public void setNotificationImageContentType(String notificationImageContentType) {
        this.notificationImageContentType = notificationImageContentType;
    }

    public NotificationState getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationState notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NotificationDTO notificationDTO = (NotificationDTO) o;
        if (notificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
            "id=" + getId() +
            ", notificationTitle='" + getNotificationTitle() + "'" +
            ", notificationSubject='" + getNotificationSubject() + "'" +
            ", notificationUrl='" + getNotificationUrl() + "'" +
            ", notificationImage='" + getNotificationImage() + "'" +
            ", notificationStatus='" + getNotificationStatus() + "'" +
            ", notificationType='" + getNotificationType() + "'" +
            ", userId=" + getUserId() +
            ", userFirstName='" + getUserFirstName() + "'" +
            "}";
    }
}
