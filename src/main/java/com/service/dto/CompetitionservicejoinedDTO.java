package com.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.domain.enumeration.ServicesState;

/**
 * A DTO for the {@link com.domain.Competitionservicejoined} entity.
 */
public class CompetitionservicejoinedDTO implements Serializable {

    private Long id;

    private ServicesState state;

    private String details;


    private Long prestationServiceId;

    private Long competitionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServicesState getState() {
        return state;
    }

    public void setState(ServicesState state) {
        this.state = state;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getPrestationServiceId() {
        return prestationServiceId;
    }

    public void setPrestationServiceId(Long prestationServiceId) {
        this.prestationServiceId = prestationServiceId;
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

        CompetitionservicejoinedDTO competitionservicejoinedDTO = (CompetitionservicejoinedDTO) o;
        if (competitionservicejoinedDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competitionservicejoinedDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompetitionservicejoinedDTO{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", details='" + getDetails() + "'" +
            ", prestationServiceId=" + getPrestationServiceId() +
            ", competitionId=" + getCompetitionId() +
            "}";
    }
}
