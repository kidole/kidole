package com.kidole.sport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kidole.sport.domain.Poules} entity.
 */
public class PoulesDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String poulesName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoulesName() {
        return poulesName;
    }

    public void setPoulesName(String poulesName) {
        this.poulesName = poulesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PoulesDTO poulesDTO = (PoulesDTO) o;
        if (poulesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), poulesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PoulesDTO{" +
            "id=" + getId() +
            ", poulesName='" + getPoulesName() + "'" +
            "}";
    }
}
