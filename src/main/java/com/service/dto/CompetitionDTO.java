package com.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.domain.Competition} entity.
 */
public class CompetitionDTO implements Serializable {

    private Long id;

    private String name;

    private Instant debut;

    private Instant fin;

    private Instant dateLimit;

    @Lob
    private String detail;

    @Lob
    private byte[] role;

    private String roleContentType;

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

    public Instant getDateLimit() {
        return dateLimit;
    }

    public void setDateLimit(Instant dateLimit) {
        this.dateLimit = dateLimit;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public byte[] getRole() {
        return role;
    }

    public void setRole(byte[] role) {
        this.role = role;
    }

    public String getRoleContentType() {
        return roleContentType;
    }

    public void setRoleContentType(String roleContentType) {
        this.roleContentType = roleContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompetitionDTO competitionDTO = (CompetitionDTO) o;
        if (competitionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competitionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompetitionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", debut='" + getDebut() + "'" +
            ", fin='" + getFin() + "'" +
            ", dateLimit='" + getDateLimit() + "'" +
            ", detail='" + getDetail() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
