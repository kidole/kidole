package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "team")
    @JsonIgnore
    private Score score;

    @ManyToOne
    @JsonIgnoreProperties("teams")
    private Confrontation confrontation;

    @ManyToOne
    @JsonIgnoreProperties("teams")
    private Poules poules;

    @ManyToOne
    @JsonIgnoreProperties("teams")
    private Delegation delegation;

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

    public Team name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Score getScore() {
        return score;
    }

    public Team score(Score score) {
        this.score = score;
        return this;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Confrontation getConfrontation() {
        return confrontation;
    }

    public Team confrontation(Confrontation confrontation) {
        this.confrontation = confrontation;
        return this;
    }

    public void setConfrontation(Confrontation confrontation) {
        this.confrontation = confrontation;
    }

    public Poules getPoules() {
        return poules;
    }

    public Team poules(Poules poules) {
        this.poules = poules;
        return this;
    }

    public void setPoules(Poules poules) {
        this.poules = poules;
    }

    public Delegation getDelegation() {
        return delegation;
    }

    public Team delegation(Delegation delegation) {
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
        if (!(o instanceof Team)) {
            return false;
        }
        return id != null && id.equals(((Team) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
