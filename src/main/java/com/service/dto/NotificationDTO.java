package com.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.domain.enumeration.NotificationState;
import com.domain.enumeration.NotificationType;

/**
 * A DTO for the {@link com.domain.Notification} entity.
 */
public class NotificationDTO implements Serializable {

    private Long id;

    private String title;

    private String subject;

    private String url;

    @Lob
    private byte[] image;

    private String imageContentType;
    private NotificationState status;

    private NotificationType typeNotif;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public NotificationState getStatus() {
        return status;
    }

    public void setStatus(NotificationState status) {
        this.status = status;
    }

    public NotificationType getTypeNotif() {
        return typeNotif;
    }

    public void setTypeNotif(NotificationType typeNotif) {
        this.typeNotif = typeNotif;
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
            ", title='" + getTitle() + "'" +
            ", subject='" + getSubject() + "'" +
            ", url='" + getUrl() + "'" +
            ", image='" + getImage() + "'" +
            ", status='" + getStatus() + "'" +
            ", typeNotif='" + getTypeNotif() + "'" +
            "}";
    }
}
