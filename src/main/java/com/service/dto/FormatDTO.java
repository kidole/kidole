package com.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.domain.Format} entity.
 */
public class FormatDTO implements Serializable {

    private Long id;

    private String name;

    private Integer winerQty;


    private Long phaseId;

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
            ", name='" + getName() + "'" +
            ", winerQty=" + getWinerQty() +
            ", phaseId=" + getPhaseId() +
            ", competitionId=" + getCompetitionId() +
            "}";
    }
}
