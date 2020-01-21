package com.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.domain.enumeration.AccreditationList;
import com.domain.enumeration.AccreditationState;

/**
 * A DTO for the {@link com.domain.Accreditation} entity.
 */
public class AccreditationDTO implements Serializable {

    private Long id;

    private AccreditationList name;

    private AccreditationState status;

    private String details;


    private Long competitionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccreditationList getName() {
        return name;
    }

    public void setName(AccreditationList name) {
        this.name = name;
    }

    public AccreditationState getStatus() {
        return status;
    }

    public void setStatus(AccreditationState status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccreditationDTO accreditationDTO = (AccreditationDTO) o;
        if (accreditationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accreditationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccreditationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", details='" + getDetails() + "'" +
            ", competitionId=" + getCompetitionId() +
            "}";
    }
}
