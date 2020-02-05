package com.kidole.sport.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A DelegationMembers.
 */
@Entity
@Table(name = "delegation_members")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "delegationmembers")
public class DelegationMembers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "delegation_members_state", length = 1024, nullable = false)
    private String delegationMembersState;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "delegation_members_code", length = 1024, nullable = false)
    private String delegationMembersCode;

    
    @Lob
    @Column(name = "delegation_members_detail", nullable = false)
    private String delegationMembersDetail;

    @ManyToOne
    @JsonIgnoreProperties("delegationMembers")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDelegationMembersState() {
        return delegationMembersState;
    }

    public DelegationMembers delegationMembersState(String delegationMembersState) {
        this.delegationMembersState = delegationMembersState;
        return this;
    }

    public void setDelegationMembersState(String delegationMembersState) {
        this.delegationMembersState = delegationMembersState;
    }

    public String getDelegationMembersCode() {
        return delegationMembersCode;
    }

    public DelegationMembers delegationMembersCode(String delegationMembersCode) {
        this.delegationMembersCode = delegationMembersCode;
        return this;
    }

    public void setDelegationMembersCode(String delegationMembersCode) {
        this.delegationMembersCode = delegationMembersCode;
    }

    public String getDelegationMembersDetail() {
        return delegationMembersDetail;
    }

    public DelegationMembers delegationMembersDetail(String delegationMembersDetail) {
        this.delegationMembersDetail = delegationMembersDetail;
        return this;
    }

    public void setDelegationMembersDetail(String delegationMembersDetail) {
        this.delegationMembersDetail = delegationMembersDetail;
    }

    public User getUser() {
        return user;
    }

    public DelegationMembers user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DelegationMembers)) {
            return false;
        }
        return id != null && id.equals(((DelegationMembers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DelegationMembers{" +
            "id=" + getId() +
            ", delegationMembersState='" + getDelegationMembersState() + "'" +
            ", delegationMembersCode='" + getDelegationMembersCode() + "'" +
            ", delegationMembersDetail='" + getDelegationMembersDetail() + "'" +
            "}";
    }
}
