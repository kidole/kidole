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
 * The Phase entity.\n@author joel jorle
 */
@Entity
@Table(name = "phase")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "phase")
public class Phase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    /**
     * phaseName
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "phase_name", length = 1024, nullable = false)
    private String phaseName;

    /**
     * phaseNumber
     */
    @Min(value = 1)
    @Column(name = "phase_number")
    private Integer phaseNumber;

    /**
     * phaseDayNumber
     */
    @Min(value = 1)
    @Column(name = "phase_day_number")
    private Integer phaseDayNumber;

    @OneToMany(mappedBy = "phase")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Journee> journnees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public Phase phaseName(String phaseName) {
        this.phaseName = phaseName;
        return this;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public Integer getPhaseNumber() {
        return phaseNumber;
    }

    public Phase phaseNumber(Integer phaseNumber) {
        this.phaseNumber = phaseNumber;
        return this;
    }

    public void setPhaseNumber(Integer phaseNumber) {
        this.phaseNumber = phaseNumber;
    }

    public Integer getPhaseDayNumber() {
        return phaseDayNumber;
    }

    public Phase phaseDayNumber(Integer phaseDayNumber) {
        this.phaseDayNumber = phaseDayNumber;
        return this;
    }

    public void setPhaseDayNumber(Integer phaseDayNumber) {
        this.phaseDayNumber = phaseDayNumber;
    }

    public Set<Journee> getJournnees() {
        return journnees;
    }

    public Phase journnees(Set<Journee> journees) {
        this.journnees = journees;
        return this;
    }

    public Phase addJournnee(Journee journee) {
        this.journnees.add(journee);
        journee.setPhase(this);
        return this;
    }

    public Phase removeJournnee(Journee journee) {
        this.journnees.remove(journee);
        journee.setPhase(null);
        return this;
    }

    public void setJournnees(Set<Journee> journees) {
        this.journnees = journees;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phase)) {
            return false;
        }
        return id != null && id.equals(((Phase) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Phase{" +
            "id=" + getId() +
            ", phaseName='" + getPhaseName() + "'" +
            ", phaseNumber=" + getPhaseNumber() +
            ", phaseDayNumber=" + getPhaseDayNumber() +
            "}";
    }
}
