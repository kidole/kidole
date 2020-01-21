package com.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.domain.enumeration.GenderSex;

/**
 * A DTO for the {@link com.domain.Discipline} entity.
 */
public class DisciplineDTO implements Serializable {

    private Long id;

    private String name;

    private GenderSex sexGender;


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

    public GenderSex getSexGender() {
        return sexGender;
    }

    public void setSexGender(GenderSex sexGender) {
        this.sexGender = sexGender;
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

        DisciplineDTO disciplineDTO = (DisciplineDTO) o;
        if (disciplineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), disciplineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DisciplineDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sexGender='" + getSexGender() + "'" +
            ", competitionId=" + getCompetitionId() +
            "}";
    }
}
