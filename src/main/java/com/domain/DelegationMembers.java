package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A DelegationMembers.
 */
@Entity
@Table(name = "delegation_members")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DelegationMembers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state")
    private String state;

    @Column(name = "code_1")
    private String code1;

    @Column(name = "detail")
    private String detail;

    @OneToOne(mappedBy = "delegationMembers")
    @JsonIgnore
    private Delegation delegation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public DelegationMembers state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCode1() {
        return code1;
    }

    public DelegationMembers code1(String code1) {
        this.code1 = code1;
        return this;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getDetail() {
        return detail;
    }

    public DelegationMembers detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Delegation getDelegation() {
        return delegation;
    }

    public DelegationMembers delegation(Delegation delegation) {
        this.delegation = delegation;
        return this;
    }

    public void setDelegation(Delegation delegation) {
        this.delegation = delegation;
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
            ", state='" + getState() + "'" +
            ", code1='" + getCode1() + "'" +
            ", detail='" + getDetail() + "'" +
            "}";
    }
}
