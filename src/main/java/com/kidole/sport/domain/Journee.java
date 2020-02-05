package com.kidole.sport.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Journee.
 */
@Entity
@Table(name = "journee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "journee")
public class Journee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "journee_name", length = 1024, nullable = false)
    private String journeeName;

    @OneToMany(mappedBy = "journee")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Confrontation> confrontations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("journnees")
    private Phase phase;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJourneeName() {
        return journeeName;
    }

    public Journee journeeName(String journeeName) {
        this.journeeName = journeeName;
        return this;
    }

    public void setJourneeName(String journeeName) {
        this.journeeName = journeeName;
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
            ", journeeName='" + getJourneeName() + "'" +
            "}";
    }
}
