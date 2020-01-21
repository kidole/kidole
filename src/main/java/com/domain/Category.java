package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * The Category entity.\n@author joel jorle
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "yearlimit")
    private Integer yearlimit;

    @Column(name = "team_limit_numb")
    private Integer teamLimitNumb;

    @Column(name = "participant_limit_byteam")
    private Integer participantLimitByteam;

    @Lob
    @Column(name = "regles")
    private String regles;

    @ManyToOne
    @JsonIgnoreProperties("categories")
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

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYearlimit() {
        return yearlimit;
    }

    public Category yearlimit(Integer yearlimit) {
        this.yearlimit = yearlimit;
        return this;
    }

    public void setYearlimit(Integer yearlimit) {
        this.yearlimit = yearlimit;
    }

    public Integer getTeamLimitNumb() {
        return teamLimitNumb;
    }

    public Category teamLimitNumb(Integer teamLimitNumb) {
        this.teamLimitNumb = teamLimitNumb;
        return this;
    }

    public void setTeamLimitNumb(Integer teamLimitNumb) {
        this.teamLimitNumb = teamLimitNumb;
    }

    public Integer getParticipantLimitByteam() {
        return participantLimitByteam;
    }

    public Category participantLimitByteam(Integer participantLimitByteam) {
        this.participantLimitByteam = participantLimitByteam;
        return this;
    }

    public void setParticipantLimitByteam(Integer participantLimitByteam) {
        this.participantLimitByteam = participantLimitByteam;
    }

    public String getRegles() {
        return regles;
    }

    public Category regles(String regles) {
        this.regles = regles;
        return this;
    }

    public void setRegles(String regles) {
        this.regles = regles;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public Category discipline(Discipline discipline) {
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
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", yearlimit=" + getYearlimit() +
            ", teamLimitNumb=" + getTeamLimitNumb() +
            ", participantLimitByteam=" + getParticipantLimitByteam() +
            ", regles='" + getRegles() + "'" +
            "}";
    }
}
