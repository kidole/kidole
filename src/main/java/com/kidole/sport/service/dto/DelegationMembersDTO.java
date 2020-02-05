package com.kidole.sport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.kidole.sport.domain.DelegationMembers} entity.
 */
public class DelegationMembersDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String delegationMembersState;

    @NotNull
    @Size(min = 3, max = 1024)
    private String delegationMembersCode;

    
    @Lob
    private String delegationMembersDetail;


    private Long userId;

    private String userFirstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDelegationMembersState() {
        return delegationMembersState;
    }

    public void setDelegationMembersState(String delegationMembersState) {
        this.delegationMembersState = delegationMembersState;
    }

    public String getDelegationMembersCode() {
        return delegationMembersCode;
    }

    public void setDelegationMembersCode(String delegationMembersCode) {
        this.delegationMembersCode = delegationMembersCode;
    }

    public String getDelegationMembersDetail() {
        return delegationMembersDetail;
    }

    public void setDelegationMembersDetail(String delegationMembersDetail) {
        this.delegationMembersDetail = delegationMembersDetail;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
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
            ", delegationMembersState='" + getDelegationMembersState() + "'" +
            ", delegationMembersCode='" + getDelegationMembersCode() + "'" +
            ", delegationMembersDetail='" + getDelegationMembersDetail() + "'" +
            ", userId=" + getUserId() +
            ", userFirstName='" + getUserFirstName() + "'" +
            "}";
    }
}
