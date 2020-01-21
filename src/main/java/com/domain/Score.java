package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Score.
 */
@Entity
@Table(name = "score")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "score_index")
    private Integer scoreIndex;

    @Column(name = "value")
    private Double value;

    @Column(name = "unite")
    private String unite;

    @OneToOne
    @JoinColumn(unique = true)
    private Team team;

    @ManyToOne
    @JsonIgnoreProperties("scores")
    private Confrontation confrontation;

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

    public Score name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScoreIndex() {
        return scoreIndex;
    }

    public Score scoreIndex(Integer scoreIndex) {
        this.scoreIndex = scoreIndex;
        return this;
    }

    public void setScoreIndex(Integer scoreIndex) {
        this.scoreIndex = scoreIndex;
    }

    public Double getValue() {
        return value;
    }

    public Score value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnite() {
        return unite;
    }

    public Score unite(String unite) {
        this.unite = unite;
        return this;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Team getTeam() {
        return team;
    }

    public Score team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Confrontation getConfrontation() {
        return confrontation;
    }

    public Score confrontation(Confrontation confrontation) {
        this.confrontation = confrontation;
        return this;
    }

    public void setConfrontation(Confrontation confrontation) {
        this.confrontation = confrontation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Score)) {
            return false;
        }
        return id != null && id.equals(((Score) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Score{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", scoreIndex=" + getScoreIndex() +
            ", value=" + getValue() +
            ", unite='" + getUnite() + "'" +
            "}";
    }
}
