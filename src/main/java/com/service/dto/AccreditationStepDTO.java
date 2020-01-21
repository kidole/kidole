package com.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import com.domain.enumeration.AccreditationList;

/**
 * A DTO for the {@link com.domain.AccreditationStep} entity.
 */
public class AccreditationStepDTO implements Serializable {

    private Long id;

    private Instant debut;

    private Instant fin;

    private Integer numero;

    private AccreditationList type;


    private Long competitionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDebut() {
        return debut;
    }

    public void setDebut(Instant debut) {
        this.debut = debut;
    }

    public Instant getFin() {
        return fin;
    }

    public void setFin(Instant fin) {
        this.fin = fin;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public AccreditationList getType() {
        return type;
    }

    public void setType(AccreditationList type) {
        this.type = type;
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

        AccreditationStepDTO accreditationStepDTO = (AccreditationStepDTO) o;
        if (accreditationStepDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accreditationStepDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccreditationStepDTO{" +
            "id=" + getId() +
            ", debut='" + getDebut() + "'" +
            ", fin='" + getFin() + "'" +
            ", numero=" + getNumero() +
            ", type='" + getType() + "'" +
            ", competitionId=" + getCompetitionId() +
            "}";
    }
}
