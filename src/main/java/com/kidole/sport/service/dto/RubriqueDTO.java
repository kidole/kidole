package com.kidole.sport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.kidole.sport.domain.Rubrique} entity.
 */
public class RubriqueDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String rubriqueName;

    
    @Lob
    private String rubriqueDetails;

    @Lob
    private byte[] rubriqueImage;

    private String rubriqueImageContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRubriqueName() {
        return rubriqueName;
    }

    public void setRubriqueName(String rubriqueName) {
        this.rubriqueName = rubriqueName;
    }

    public String getRubriqueDetails() {
        return rubriqueDetails;
    }

    public void setRubriqueDetails(String rubriqueDetails) {
        this.rubriqueDetails = rubriqueDetails;
    }

    public byte[] getRubriqueImage() {
        return rubriqueImage;
    }

    public void setRubriqueImage(byte[] rubriqueImage) {
        this.rubriqueImage = rubriqueImage;
    }

    public String getRubriqueImageContentType() {
        return rubriqueImageContentType;
    }

    public void setRubriqueImageContentType(String rubriqueImageContentType) {
        this.rubriqueImageContentType = rubriqueImageContentType;
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
            ", rubriqueName='" + getRubriqueName() + "'" +
            ", rubriqueDetails='" + getRubriqueDetails() + "'" +
            ", rubriqueImage='" + getRubriqueImage() + "'" +
            "}";
    }
}
