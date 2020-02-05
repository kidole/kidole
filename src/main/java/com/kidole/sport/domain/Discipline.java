package com.kidole.sport.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.kidole.sport.domain.enumeration.GenderSex;

/**
 * A Discipline.
 */
@Entity
@Table(name = "discipline")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "discipline")
public class Discipline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "discipline_name", length = 1024, nullable = false)
    private String disciplineName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sex_gender", nullable = false)
    private GenderSex sexGender;

    @ManyToOne
    @JsonIgnoreProperties("disciplines")
    private Competition competition;

    @ManyToOne
    @JsonIgnoreProperties("disciplines")
    private Phase phase;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public Discipline disciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
        return this;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
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

    public Phase getPhase() {
        return phase;
    }

    public Discipline phase(Phase phase) {
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
            ", disciplineName='" + getDisciplineName() + "'" +
            ", sexGender='" + getSexGender() + "'" +
            "}";
    }
}
