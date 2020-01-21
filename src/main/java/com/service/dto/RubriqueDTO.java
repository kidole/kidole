package com.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.domain.Rubrique} entity.
 */
public class RubriqueDTO implements Serializable {

    private Long id;

    private String name;

    @Lob
    private String details;

    @Lob
    private byte[] image;

    private String imageContentType;

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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RubriqueDTO rubriqueDTO = (RubriqueDTO) o;
        if (rubriqueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rubriqueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RubriqueDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", details='" + getDetails() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}
