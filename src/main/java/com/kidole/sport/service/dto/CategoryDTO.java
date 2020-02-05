package com.kidole.sport.service.dto;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.kidole.sport.domain.Category} entity.
 */
@ApiModel(description = "The Category entity.\n@author joel jorle")
public class CategoryDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String categoryName;

    @NotNull
    @Min(value = 0)
    private Integer yearlimit;

    @NotNull
    @Min(value = 0)
    private Integer teamLimitNumb;

    @NotNull
    @Min(value = 0)
    private Integer participantLimitByteam;

    
    @Lob
    private String categoryRule;


    private Long disciplineId;

    private String disciplineDisciplineName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getCategoryRule() {
        return categoryRule;
    }

    public void setCategoryRule(String categoryRule) {
        this.categoryRule = categoryRule;
    }

    public Long getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Long disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getDisciplineDisciplineName() {
        return disciplineDisciplineName;
    }

    public void setDisciplineDisciplineName(String disciplineDisciplineName) {
        this.disciplineDisciplineName = disciplineDisciplineName;
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
            ", categoryName='" + getCategoryName() + "'" +
            ", yearlimit=" + getYearlimit() +
            ", teamLimitNumb=" + getTeamLimitNumb() +
            ", participantLimitByteam=" + getParticipantLimitByteam() +
            ", categoryRule='" + getCategoryRule() + "'" +
            ", disciplineId=" + getDisciplineId() +
            ", disciplineDisciplineName='" + getDisciplineDisciplineName() + "'" +
            "}";
    }
}
