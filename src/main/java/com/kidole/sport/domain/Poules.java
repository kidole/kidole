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
 * A Poules.
 */
@Entity
@Table(name = "poules")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "poules")
public class Poules implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "poules_name", length = 1024, nullable = false)
    private String poulesName;

    @OneToMany(mappedBy = "poules")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Team> teams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoulesName() {
        return poulesName;
    }

    public Poules poulesName(String poulesName) {
        this.poulesName = poulesName;
        return this;
    }

    public void setPoulesName(String poulesName) {
        this.poulesName = poulesName;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Poules teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public Poules addTeam(Team team) {
        this.teams.add(team);
        team.setPoules(this);
        return this;
    }

    public Poules removeTeam(Team team) {
        this.teams.remove(team);
        team.setPoules(null);
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
        if (!(o instanceof Poules)) {
            return false;
        }
        return id != null && id.equals(((Poules) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Poules{" +
            "id=" + getId() +
            ", poulesName='" + getPoulesName() + "'" +
            "}";
    }
}
