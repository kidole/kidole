package com.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.domain.DelegationMembers} entity.
 */
public class DelegationMembersDTO implements Serializable {

    private Long id;

    private String state;

    private String code1;

    private String detail;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DelegationMembersDTO delegationMembersDTO = (DelegationMembersDTO) o;
        if (delegationMembersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), delegationMembersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DelegationMembersDTO{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", code1='" + getCode1() + "'" +
            ", detail='" + getDetail() + "'" +
            "}";
    }
}
