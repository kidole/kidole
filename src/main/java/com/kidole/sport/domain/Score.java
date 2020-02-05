package com.kidole.sport.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Score.
 */
@Entity
@Table(name = "score")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "score")
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "score_name", length = 1024, nullable = false)
    private String scoreName;

    @NotNull
    @Min(value = 0)
    @Column(name = "score_index", nullable = false)
    private Integer scoreIndex;

    @NotNull
    @Column(name = "score_value", nullable = false)
    private Double scoreValue;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "score_unit", length = 1024, nullable = false)
    private String scoreUnit;

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

    public String getScoreName() {
        return scoreName;
    }

    public Score scoreName(String scoreName) {
        this.scoreName = scoreName;
        return this;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
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

    public Double getScoreValue() {
        return scoreValue;
    }

    public Score scoreValue(Double scoreValue) {
        this.scoreValue = scoreValue;
        return this;
    }

    public void setScoreValue(Double scoreValue) {
        this.scoreValue = scoreValue;
    }

    public String getScoreUnit() {
        return scoreUnit;
    }

    public Score scoreUnit(String scoreUnit) {
        this.scoreUnit = scoreUnit;
        return this;
    }

    public void setScoreUnit(String scoreUnit) {
        this.scoreUnit = scoreUnit;
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
            ", scoreName='" + getScoreName() + "'" +
            ", scoreIndex=" + getScoreIndex() +
            ", scoreValue=" + getScoreValue() +
            ", scoreUnit='" + getScoreUnit() + "'" +
            "}";
    }
}
