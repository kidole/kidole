package com.kidole.sport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.kidole.sport.domain.enumeration.GenderSex;

/**
 * A DTO for the {@link com.kidole.sport.domain.Discipline} entity.
 */
public class DisciplineDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String disciplineName;

    @NotNull
    private GenderSex sexGender;


    private Long competitionId;

    private String competitionCompetitionName;

    private Long phaseId;

    private String phasePhaseName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
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

    public String getCompetitionCompetitionName() {
        return competitionCompetitionName;
    }

    public void setCompetitionCompetitionName(String competitionCompetitionName) {
        this.competitionCompetitionName = competitionCompetitionName;
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
            ", disciplineName='" + getDisciplineName() + "'" +
            ", sexGender='" + getSexGender() + "'" +
            ", competitionId=" + getCompetitionId() +
            ", competitionCompetitionName='" + getCompetitionCompetitionName() + "'" +
            ", phaseId=" + getPhaseId() +
            ", phasePhaseName='" + getPhasePhaseName() + "'" +
            "}";
    }
}
