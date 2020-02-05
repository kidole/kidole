package com.kidole.sport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kidole.sport.domain.Options} entity.
 */
public class OptionsDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String optionsName;

    @NotNull
    private Boolean optionsValue1;

    @NotNull
    @Min(value = 0)
    private Integer optionsValue2;


    private Long competitionId;

    private String competitionCompetitionName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionsName() {
        return optionsName;
    }

    public void setOptionsName(String optionsName) {
        this.optionsName = optionsName;
    }

    public Boolean isOptionsValue1() {
        return optionsValue1;
    }

    public void setOptionsValue1(Boolean optionsValue1) {
        this.optionsValue1 = optionsValue1;
    }

    public Integer getOptionsValue2() {
        return optionsValue2;
    }

    public void setOptionsValue2(Integer optionsValue2) {
        this.optionsValue2 = optionsValue2;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OptionsDTO optionsDTO = (OptionsDTO) o;
        if (optionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), optionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OptionsDTO{" +
            "id=" + getId() +
            ", optionsName='" + getOptionsName() + "'" +
            ", optionsValue1='" + isOptionsValue1() + "'" +
            ", optionsValue2=" + getOptionsValue2() +
            ", competitionId=" + getCompetitionId() +
            ", competitionCompetitionName='" + getCompetitionCompetitionName() + "'" +
            "}";
    }
}
