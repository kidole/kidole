package com.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.domain.Delegation} entity.
 */
public class DelegationDTO implements Serializable {

    private Long id;

    private String name;

    private String country;

    private String locality;

    private String code1;


    private Long delegationMembersId;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public Long getDelegationMembersId() {
        return delegationMembersId;
    }

    public void setDelegationMembersId(Long delegationMembersId) {
        this.delegationMembersId = delegationMembersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DelegationDTO delegationDTO = (DelegationDTO) o;
        if (delegationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), delegationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DelegationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", country='" + getCountry() + "'" +
            ", locality='" + getLocality() + "'" +
            ", code1='" + getCode1() + "'" +
            ", delegationMembersId=" + getDelegationMembersId() +
            "}";
    }
}
