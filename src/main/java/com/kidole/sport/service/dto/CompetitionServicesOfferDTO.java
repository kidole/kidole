package com.kidole.sport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.kidole.sport.domain.CompetitionServicesOffer} entity.
 */
public class CompetitionServicesOfferDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String competitionServicesOfferName;

    
    @Lob
    private String competitionServicesOfferDetail;

    @NotNull
    @Size(min = 3, max = 1024)
    private String competitionServicesOfferUrl;


    private Long rubricId;

    private String rubricRubriqueName;

    private Long competitionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompetitionServicesOfferName() {
        return competitionServicesOfferName;
    }

    public void setCompetitionServicesOfferName(String competitionServicesOfferName) {
        this.competitionServicesOfferName = competitionServicesOfferName;
    }

    public String getCompetitionServicesOfferDetail() {
        return competitionServicesOfferDetail;
    }

    public void setCompetitionServicesOfferDetail(String competitionServicesOfferDetail) {
        this.competitionServicesOfferDetail = competitionServicesOfferDetail;
    }

    public String getCompetitionServicesOfferUrl() {
        return competitionServicesOfferUrl;
    }

    public void setCompetitionServicesOfferUrl(String competitionServicesOfferUrl) {
        this.competitionServicesOfferUrl = competitionServicesOfferUrl;
    }

    public Long getRubricId() {
        return rubricId;
    }

    public void setRubricId(Long rubriqueId) {
        this.rubricId = rubriqueId;
    }

    public String getRubricRubriqueName() {
        return rubricRubriqueName;
    }

    public void setRubricRubriqueName(String rubriqueRubriqueName) {
        this.rubricRubriqueName = rubriqueRubriqueName;
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
            ", competitionServicesOfferName='" + getCompetitionServicesOfferName() + "'" +
            ", competitionServicesOfferDetail='" + getCompetitionServicesOfferDetail() + "'" +
            ", competitionServicesOfferUrl='" + getCompetitionServicesOfferUrl() + "'" +
            ", rubricId=" + getRubricId() +
            ", rubricRubriqueName='" + getRubricRubriqueName() + "'" +
            ", competitionId=" + getCompetitionId() +
            "}";
    }
}
