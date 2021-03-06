package com.kidole.sport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kidole.sport.domain.Journee} entity.
 */
public class JourneeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String journeeName;


    private Long phaseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJourneeName() {
        return journeeName;
    }

    public void setJourneeName(String journeeName) {
        this.journeeName = journeeName;
    }

    public Long getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(Long phaseId) {
        this.phaseId = phaseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JourneeDTO journeeDTO = (JourneeDTO) o;
        if (journeeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), journeeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JourneeDTO{" +
            "id=" + getId() +
            ", journeeName='" + getJourneeName() + "'" +
            ", phaseId=" + getPhaseId() +
            "}";
    }
}
