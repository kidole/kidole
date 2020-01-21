package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Journee.
 */
@Entity
@Table(name = "journee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Journee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "journee")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Confrontation> confrontations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("journees")
    private Phase phase;

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

    public Journee name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Confrontation> getConfrontations() {
        return confrontations;
    }

    public Journee confrontations(Set<Confrontation> confrontations) {
        this.confrontations = confrontations;
        return this;
    }

    public Journee addConfrontation(Confrontation confrontation) {
        this.confrontations.add(confrontation);
        confrontation.setJournee(this);
        return this;
    }

    public Journee removeConfrontation(Confrontation confrontation) {
        this.confrontations.remove(confrontation);
        confrontation.setJournee(null);
        return this;
    }

    public void setConfrontations(Set<Confrontation> confrontations) {
        this.confrontations = confrontations;
    }

    public Phase getPhase() {
        return phase;
    }

    public Journee phase(Phase phase) {
        this.phase = phase;
        return this;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Journee)) {
            return false;
        }
        return id != null && id.equals(((Journee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Journee{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
