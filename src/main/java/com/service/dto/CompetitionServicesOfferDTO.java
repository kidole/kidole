package com.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.domain.CompetitionServicesOffer} entity.
 */
public class CompetitionServicesOfferDTO implements Serializable {

    private Long id;

    private String name;

    private String details;

    private String url;


    private Long rubriqueId;

    private Long competitionId;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getRubriqueId() {
        return rubriqueId;
    }

    public void setRubriqueId(Long rubriqueId) {
        this.rubriqueId = rubriqueId;
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

        CompetitionServicesOfferDTO competitionServicesOfferDTO = (CompetitionServicesOfferDTO) o;
        if (competitionServicesOfferDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competitionServicesOfferDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompetitionServicesOfferDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", details='" + getDetails() + "'" +
            ", url='" + getUrl() + "'" +
            ", rubriqueId=" + getRubriqueId() +
            ", competitionId=" + getCompetitionId() +
            "}";
    }
}
