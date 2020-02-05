package com.kidole.sport.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kidole.sport.domain.Phase} entity.
 */
@ApiModel(description = "The Phase entity.\n@author joel jorle")
public class PhaseDTO implements Serializable {

    private Long id;

    /**
     * phaseName
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "phaseName", required = true)
    private String phaseName;

    /**
     * phaseNumber
     */
    @Min(value = 1)
    @ApiModelProperty(value = "phaseNumber")
    private Integer phaseNumber;

    /**
     * phaseDayNumber
     */
    @Min(value = 1)
    @ApiModelProperty(value = "phaseDayNumber")
    private Integer phaseDayNumber;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public Integer getPhaseNumber() {
        return phaseNumber;
    }

    public void setPhaseNumber(Integer phaseNumber) {
        this.phaseNumber = phaseNumber;
    }

    public Integer getPhaseDayNumber() {
        return phaseDayNumber;
    }

    public void setPhaseDayNumber(Integer phaseDayNumber) {
        this.phaseDayNumber = phaseDayNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhaseDTO phaseDTO = (PhaseDTO) o;
        if (phaseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), phaseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PhaseDTO{" +
            "id=" + getId() +
            ", phaseName='" + getPhaseName() + "'" +
            ", phaseNumber=" + getPhaseNumber() +
            ", phaseDayNumber=" + getPhaseDayNumber() +
            "}";
    }
}
