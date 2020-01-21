package com.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.domain.Score} entity.
 */
public class ScoreDTO implements Serializable {

    private Long id;

    private String name;

    private Integer scoreIndex;

    private Double value;

    private String unite;


    private Long teamId;

    private Long confrontationId;

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

    public Integer getScoreIndex() {
        return scoreIndex;
    }

    public void setScoreIndex(Integer scoreIndex) {
        this.scoreIndex = scoreIndex;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
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
            ", name='" + getName() + "'" +
            ", scoreIndex=" + getScoreIndex() +
            ", value=" + getValue() +
            ", unite='" + getUnite() + "'" +
            ", teamId=" + getTeamId() +
            ", confrontationId=" + getConfrontationId() +
            "}";
    }
}
