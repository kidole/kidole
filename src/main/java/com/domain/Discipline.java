package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.domain.enumeration.GenderSex;

/**
 * A Discipline.
 */
@Entity
@Table(name = "discipline")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Discipline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex_gender")
    private GenderSex sexGender;

    @OneToMany(mappedBy = "discipline")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Phase> phases = new HashSet<>();

    @OneToMany(mappedBy = "discipline")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Category> categories = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("disciplines")
    private Competition competition;

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

    public Discipline name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenderSex getSexGender() {
        return sexGender;
    }

    public Discipline sexGender(GenderSex sexGender) {
        this.sexGender = sexGender;
        return this;
    }

    public void setSexGender(GenderSex sexGender) {
        this.sexGender = sexGender;
    }

    public Set<Phase> getPhases() {
        return phases;
    }

    public Discipline phases(Set<Phase> phases) {
        this.phases = phases;
        return this;
    }

    public Discipline addPhase(Phase phase) {
        this.phases.add(phase);
        phase.setDiscipline(this);
        return this;
    }

    public Discipline removePhase(Phase phase) {
        this.phases.remove(phase);
        phase.setDiscipline(null);
        return this;
    }

    public void setPhases(Set<Phase> phases) {
        this.phases = phases;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Discipline categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Discipline addCategory(Category category) {
        this.categories.add(category);
        category.setDiscipline(this);
        return this;
    }

    public Discipline removeCategory(Category category) {
        this.categories.remove(category);
        category.setDiscipline(null);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Competition getCompetition() {
        return competition;
    }

    public Discipline competition(Competition competition) {
        this.competition = competition;
        return this;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Discipline)) {
            return false;
        }
        return id != null && id.equals(((Discipline) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Discipline{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sexGender='" + getSexGender() + "'" +
            "}";
    }
}
