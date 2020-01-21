package com.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Delegation.
 */
@Entity
@Table(name = "delegation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Delegation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "locality")
    private String locality;

    @Column(name = "code_1")
    private String code1;

    @OneToOne
    @JoinColumn(unique = true)
    private DelegationMembers delegationMembers;

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

    public String getName() {
        return name;
    }

    public Delegation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public Delegation country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocality() {
        return locality;
    }

    public Delegation locality(String locality) {
        this.locality = locality;
        return this;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCode1() {
        return code1;
    }

    public Delegation code1(String code1) {
        this.code1 = code1;
        return this;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public DelegationMembers getDelegationMembers() {
        return delegationMembers;
    }

    public Delegation delegationMembers(DelegationMembers delegationMembers) {
        this.delegationMembers = delegationMembers;
        return this;
    }

    public void setDelegationMembers(DelegationMembers delegationMembers) {
        this.delegationMembers = delegationMembers;
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
            ", name='" + getName() + "'" +
            ", country='" + getCountry() + "'" +
            ", locality='" + getLocality() + "'" +
            ", code1='" + getCode1() + "'" +
            "}";
    }
}
