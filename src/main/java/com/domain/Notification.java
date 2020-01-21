package com.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.domain.enumeration.NotificationState;

import com.domain.enumeration.NotificationType;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "subject")
    private String subject;

    @Column(name = "url")
    private String url;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NotificationState status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_notif")
    private NotificationType typeNotif;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Notification title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public Notification subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUrl() {
        return url;
    }

    public Notification url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getImage() {
        return image;
    }

    public Notification image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Notification imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public NotificationState getStatus() {
        return status;
    }

    public Notification status(NotificationState status) {
        this.status = status;
        return this;
    }

    public void setStatus(NotificationState status) {
        this.status = status;
    }

    public NotificationType getTypeNotif() {
        return typeNotif;
    }

    public Notification typeNotif(NotificationType typeNotif) {
        this.typeNotif = typeNotif;
        return this;
    }

    public void setTypeNotif(NotificationType typeNotif) {
        this.typeNotif = typeNotif;
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
            ", title='" + getTitle() + "'" +
            ", subject='" + getSubject() + "'" +
            ", url='" + getUrl() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", status='" + getStatus() + "'" +
            ", typeNotif='" + getTypeNotif() + "'" +
            "}";
    }
}
