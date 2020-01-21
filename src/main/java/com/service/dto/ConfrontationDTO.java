package com.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.domain.Confrontation} entity.
 */
public class ConfrontationDTO implements Serializable {

    private Long id;

    private String name;

    private Instant debut;

    private Instant fin;

    @Lob
    private String details;


    private Long matchSheetId;

    private Long localisationId;

    private Long journeeId;

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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getMatchSheetId() {
        return matchSheetId;
    }

    public void setMatchSheetId(Long matchSheetId) {
        this.matchSheetId = matchSheetId;
    }

    public Long getLocalisationId() {
        return localisationId;
    }

    public void setLocalisationId(Long localisationId) {
        this.localisationId = localisationId;
    }

    public Long getJourneeId() {
        return journeeId;
    }

    public void setJourneeId(Long journeeId) {
        this.journeeId = journeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConfrontationDTO confrontationDTO = (ConfrontationDTO) o;
        if (confrontationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), confrontationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConfrontationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", debut='" + getDebut() + "'" +
            ", fin='" + getFin() + "'" +
            ", details='" + getDetails() + "'" +
            ", matchSheetId=" + getMatchSheetId() +
            ", localisationId=" + getLocalisationId() +
            ", journeeId=" + getJourneeId() +
            "}";
    }
}
