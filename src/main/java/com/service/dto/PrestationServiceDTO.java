package com.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.domain.PrestationService} entity.
 */
public class PrestationServiceDTO implements Serializable {

    private Long id;

    private String name;

    @Lob
    private String detail;

    @Lob
    private byte[] image;

    private String imageContentType;

    private Long rubriqueId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public Long getRubriqueId() {
        return rubriqueId;
    }

    public void setRubriqueId(Long rubriqueId) {
        this.rubriqueId = rubriqueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrestationServiceDTO prestationServiceDTO = (PrestationServiceDTO) o;
        if (prestationServiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prestationServiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrestationServiceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", image='" + getImage() + "'" +
            ", rubriqueId=" + getRubriqueId() +
            "}";
    }
}
