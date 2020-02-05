package com.kidole.sport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kidole.sport.domain.Format} entity.
 */
public class FormatDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String formatName;

    @Min(value = 0)
    private Integer winerQty;


    private Long phaseId;

    private String phasePhaseName;

    private Long competitionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public Integer getWinerQty() {
        return winerQty;
    }

    public void setWinerQty(Integer winerQty) {
        this.winerQty = winerQty;
    }

    public Long getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(Long phaseId) {
        this.phaseId = phaseId;
    }

    public String getPhasePhaseName() {
        return phasePhaseName;
    }

    public void setPhasePhaseName(String phasePhaseName) {
        this.phasePhaseName = phasePhaseName;
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

        FormatDTO formatDTO = (FormatDTO) o;
        if (formatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormatDTO{" +
            "id=" + getId() +
            ", formatName='" + getFormatName() + "'" +
            ", winerQty=" + getWinerQty() +
            ", phaseId=" + getPhaseId() +
            ", phasePhaseName='" + getPhasePhaseName() + "'" +
            ", competitionId=" + getCompetitionId() +
            "}";
    }
}
