package com.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.domain.Options} entity.
 */
public class OptionsDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean value1;

    private Integer value2;


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

    public Boolean isValue1() {
        return value1;
    }

    public void setValue1(Boolean value1) {
        this.value1 = value1;
    }

    public Integer getValue2() {
        return value2;
    }

    public void setValue2(Integer value2) {
        this.value2 = value2;
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
            ", name='" + getName() + "'" +
            ", value1='" + isValue1() + "'" +
            ", value2=" + getValue2() +
            ", competitionId=" + getCompetitionId() +
            "}";
    }
}
