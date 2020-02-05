package com.kidole.sport.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Delegation.
 */
@Entity
@Table(name = "delegation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "delegation")
public class Delegation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "delegation_name", length = 1024, nullable = false)
    private String delegationName;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "delegation_country", length = 1024, nullable = false)
    private String delegationCountry;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "delegation_locality", length = 1024, nullable = false)
    private String delegationLocality;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "delegation_code", length = 1024, nullable = false)
    private String delegationCode;

    @OneToOne
    @JoinColumn(unique = true)
    private DelegationMembers delegateMember;

    @OneToMany(mappedBy = "delegation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Team> teams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDelegationName() {
        return delegationName;
    }

    public Delegation delegationName(String delegationName) {
        this.delegationName = delegationName;
        return this;
    }

    public void setDelegationName(String delegationName) {
        this.delegationName = delegationName;
    }

    public String getDelegationCountry() {
        return delegationCountry;
    }

    public Delegation delegationCountry(String delegationCountry) {
        this.delegationCountry = delegationCountry;
        return this;
    }

    public void setDelegationCountry(String delegationCountry) {
        this.delegationCountry = delegationCountry;
    }

    public String getDelegationLocality() {
        return delegationLocality;
    }

    public Delegation delegationLocality(String delegationLocality) {
        this.delegationLocality = delegationLocality;
        return this;
    }

    public void setDelegationLocality(String delegationLocality) {
        this.delegationLocality = delegationLocality;
    }

    public String getDelegationCode() {
        return delegationCode;
    }

    public Delegation delegationCode(String delegationCode) {
        this.delegationCode = delegationCode;
        return this;
    }

    public void setDelegationCode(String delegationCode) {
        this.delegationCode = delegationCode;
    }

    public DelegationMembers getDelegateMember() {
        return delegateMember;
    }

    public Delegation delegateMember(DelegationMembers delegationMembers) {
        this.delegateMember = delegationMembers;
        return this;
    }

    public void setDelegateMember(DelegationMembers delegationMembers) {
        this.delegateMember = delegationMembers;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Delegation teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public Delegation addTeam(Team team) {
        this.teams.add(team);
        team.setDelegation(this);
        return this;
    }

    public Delegation removeTeam(Team team) {
        this.teams.remove(team);
        team.setDelegation(null);
        return this;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Delegation)) {
            return false;
        }
        return id != null && id.equals(((Delegation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Delegation{" +
            "id=" + getId() +
            ", delegationName='" + getDelegationName() + "'" +
            ", delegationCountry='" + getDelegationCountry() + "'" +
            ", delegationLocality='" + getDelegationLocality() + "'" +
            ", delegationCode='" + getDelegationCode() + "'" +
            "}";
    }
}
