package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The Phase entity.\n@author joel jorle
 */
@Entity
@Table(name = "phase")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Phase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * name
     */
    @Column(name = "name")
    private String name;

    /**
     * number
     */
    @Column(name = "numero")
    private Integer numero;

    /**
     * daynumber
     */
    @Column(name = "day_number")
    private Integer dayNumber;

    @OneToMany(mappedBy = "phase")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Journee> journees = new HashSet<>();

    @OneToOne(mappedBy = "phase")
    @JsonIgnore
    private Format format;

    @ManyToOne
    @JsonIgnoreProperties("phases")
    private Discipline discipline;

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

    public Phase name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumero() {
        return numero;
    }

    public Phase numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public Phase dayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
        return this;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Set<Journee> getJournees() {
        return journees;
    }

    public Phase journees(Set<Journee> journees) {
        this.journees = journees;
        return this;
    }

    public Phase addJournee(Journee journee) {
        this.journees.add(journee);
        journee.setPhase(this);
        return this;
    }

    public Phase removeJournee(Journee journee) {
        this.journees.remove(journee);
        journee.setPhase(null);
        return this;
    }

    public void setJournees(Set<Journee> journees) {
        this.journees = journees;
    }

    public Format getFormat() {
        return format;
    }

    public Phase format(Format format) {
        this.format = format;
        return this;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public Phase discipline(Discipline discipline) {
        this.discipline = discipline;
        return this;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
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
            ", name='" + getName() + "'" +
            ", numero=" + getNumero() +
            ", dayNumber=" + getDayNumber() +
            "}";
    }
}
