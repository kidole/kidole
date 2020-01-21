package com.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.domain.Category} entity.
 */
@ApiModel(description = "The Category entity.\n@author joel jorle")
public class CategoryDTO implements Serializable {

    private Long id;

    private String name;

    private Integer yearlimit;

    private Integer teamLimitNumb;

    private Integer participantLimitByteam;

    @Lob
    private String regles;


    private Long disciplineId;

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

    public Integer getYearlimit() {
        return yearlimit;
    }

    public void setYearlimit(Integer yearlimit) {
        this.yearlimit = yearlimit;
    }

    public Integer getTeamLimitNumb() {
        return teamLimitNumb;
    }

    public void setTeamLimitNumb(Integer teamLimitNumb) {
        this.teamLimitNumb = teamLimitNumb;
    }

    public Integer getParticipantLimitByteam() {
        return participantLimitByteam;
    }

    public void setParticipantLimitByteam(Integer participantLimitByteam) {
        this.participantLimitByteam = participantLimitByteam;
    }

    public String getRegles() {
        return regles;
    }

    public void setRegles(String regles) {
        this.regles = regles;
    }

    public Long getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Long disciplineId) {
        this.disciplineId = disciplineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoryDTO categoryDTO = (CategoryDTO) o;
        if (categoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", yearlimit=" + getYearlimit() +
            ", teamLimitNumb=" + getTeamLimitNumb() +
            ", participantLimitByteam=" + getParticipantLimitByteam() +
            ", regles='" + getRegles() + "'" +
            ", disciplineId=" + getDisciplineId() +
            "}";
    }
}
