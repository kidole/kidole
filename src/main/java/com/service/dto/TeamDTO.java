package com.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.domain.Team} entity.
 */
public class TeamDTO implements Serializable {

    private Long id;

    private String name;


    private Long confrontationId;

    private Long poulesId;

    private Long delegationId;

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

    public Long getConfrontationId() {
        return confrontationId;
    }

    public void setConfrontationId(Long confrontationId) {
        this.confrontationId = confrontationId;
    }

    public Long getPoulesId() {
        return poulesId;
    }

    public void setPoulesId(Long poulesId) {
        this.poulesId = poulesId;
    }

    public Long getDelegationId() {
        return delegationId;
    }

    public void setDelegationId(Long delegationId) {
        this.delegationId = delegationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TeamDTO teamDTO = (TeamDTO) o;
        if (teamDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teamDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeamDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", confrontationId=" + getConfrontationId() +
            ", poulesId=" + getPoulesId() +
            ", delegationId=" + getDelegationId() +
            "}";
    }
}
