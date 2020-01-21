package com.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.domain.Phase} entity.
 */
@ApiModel(description = "The Phase entity.\n@author joel jorle")
public class PhaseDTO implements Serializable {

    private Long id;

    /**
     * name
     */
    @ApiModelProperty(value = "name")
    private String name;

    /**
     * number
     */
    @ApiModelProperty(value = "number")
    private Integer numero;

    /**
     * daynumber
     */
    @ApiModelProperty(value = "daynumber")
    private Integer dayNumber;


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

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
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
            ", name='" + getName() + "'" +
            ", numero=" + getNumero() +
            ", dayNumber=" + getDayNumber() +
            ", disciplineId=" + getDisciplineId() +
            "}";
    }
}
