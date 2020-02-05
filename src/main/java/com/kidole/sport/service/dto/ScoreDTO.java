package com.kidole.sport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kidole.sport.domain.Score} entity.
 */
public class ScoreDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String scoreName;

    @NotNull
    @Min(value = 0)
    private Integer scoreIndex;

    @NotNull
    private Double scoreValue;

    @NotNull
    @Size(min = 3, max = 1024)
    private String scoreUnit;


    private Long teamId;

    private String teamTeamName;

    private Long confrontationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScoreName() {
        return scoreName;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }

    public Integer getScoreIndex() {
        return scoreIndex;
    }

    public void setScoreIndex(Integer scoreIndex) {
        this.scoreIndex = scoreIndex;
    }

    public Double getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(Double scoreValue) {
        this.scoreValue = scoreValue;
    }

    public String getScoreUnit() {
        return scoreUnit;
    }

    public void setScoreUnit(String scoreUnit) {
        this.scoreUnit = scoreUnit;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamTeamName() {
        return teamTeamName;
    }

    public void setTeamTeamName(String teamTeamName) {
        this.teamTeamName = teamTeamName;
    }

    public Long getConfrontationId() {
        return confrontationId;
    }

    public void setConfrontationId(Long confrontationId) {
        this.confrontationId = confrontationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScoreDTO scoreDTO = (ScoreDTO) o;
        if (scoreDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scoreDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScoreDTO{" +
            "id=" + getId() +
            ", scoreName='" + getScoreName() + "'" +
            ", scoreIndex=" + getScoreIndex() +
            ", scoreValue=" + getScoreValue() +
            ", scoreUnit='" + getScoreUnit() + "'" +
            ", teamId=" + getTeamId() +
            ", teamTeamName='" + getTeamTeamName() + "'" +
            ", confrontationId=" + getConfrontationId() +
            "}";
    }
}
