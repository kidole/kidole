package com.kidole.sport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kidole.sport.domain.Delegation} entity.
 */
public class DelegationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String delegationName;

    @NotNull
    @Size(min = 3, max = 1024)
    private String delegationCountry;

    @NotNull
    @Size(min = 3, max = 1024)
    private String delegationLocality;

    @NotNull
    @Size(min = 3, max = 1024)
    private String delegationCode;


    private Long delegateMemberId;

    private String delegateMemberDelegationMembersCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDelegationName() {
        return delegationName;
    }

    public void setDelegationName(String delegationName) {
        this.delegationName = delegationName;
    }

    public String getDelegationCountry() {
        return delegationCountry;
    }

    public void setDelegationCountry(String delegationCountry) {
        this.delegationCountry = delegationCountry;
    }

    public String getDelegationLocality() {
        return delegationLocality;
    }

    public void setDelegationLocality(String delegationLocality) {
        this.delegationLocality = delegationLocality;
    }

    public String getDelegationCode() {
        return delegationCode;
    }

    public void setDelegationCode(String delegationCode) {
        this.delegationCode = delegationCode;
    }

    public Long getDelegateMemberId() {
        return delegateMemberId;
    }

    public void setDelegateMemberId(Long delegationMembersId) {
        this.delegateMemberId = delegationMembersId;
    }

    public String getDelegateMemberDelegationMembersCode() {
        return delegateMemberDelegationMembersCode;
    }

    public void setDelegateMemberDelegationMembersCode(String delegationMembersDelegationMembersCode) {
        this.delegateMemberDelegationMembersCode = delegationMembersDelegationMembersCode;
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
            ", delegationName='" + getDelegationName() + "'" +
            ", delegationCountry='" + getDelegationCountry() + "'" +
            ", delegationLocality='" + getDelegationLocality() + "'" +
            ", delegationCode='" + getDelegationCode() + "'" +
            ", delegateMemberId=" + getDelegateMemberId() +
            ", delegateMemberDelegationMembersCode='" + getDelegateMemberDelegationMembersCode() + "'" +
            "}";
    }
}
