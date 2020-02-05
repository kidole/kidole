package com.kidole.sport.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Confrontation.
 */
@Entity
@Table(name = "confrontation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "confrontation")
public class Confrontation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "confrontation_name", length = 1024, nullable = false)
    private String confrontationName;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @Lob
    @Column(name = "confrontation_details")
    private String confrontationDetails;

    @OneToOne
    @JoinColumn(unique = true)
    private MatchSheet matchsheet;

    @OneToOne
    @JoinColumn(unique = true)
    private Localisation localisation;

    @OneToMany(mappedBy = "confrontation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Score> scores = new HashSet<>();

    @OneToMany(mappedBy = "confrontation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Team> teams = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("confrontations")
    private Journee journee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfrontationName() {
        return confrontationName;
    }

    public Confrontation confrontationName(String confrontationName) {
        this.confrontationName = confrontationName;
        return this;
    }

    public void setConfrontationName(String confrontationName) {
        this.confrontationName = confrontationName;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Confrontation startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Confrontation endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getConfrontationDetails() {
        return confrontationDetails;
    }

    public Confrontation confrontationDetails(String confrontationDetails) {
        this.confrontationDetails = confrontationDetails;
        return this;
    }

    public void setConfrontationDetails(String confrontationDetails) {
        this.confrontationDetails = confrontationDetails;
    }

    public MatchSheet getMatchsheet() {
        return matchsheet;
    }

    public Confrontation matchsheet(MatchSheet matchSheet) {
        this.matchsheet = matchSheet;
        return this;
    }

    public void setMatchsheet(MatchSheet matchSheet) {
        this.matchsheet = matchSheet;
    }

    public Localisation getLocalisation() {
        return localisation;
    }

    public Confrontation localisation(Localisation localisation) {
        this.localisation = localisation;
        return this;
    }

    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public Confrontation scores(Set<Score> scores) {
        this.scores = scores;
        return this;
    }

    public Confrontation addScore(Score score) {
        this.scores.add(score);
        score.setConfrontation(this);
        return this;
    }

    public Confrontation removeScore(Score score) {
        this.scores.remove(score);
        score.setConfrontation(null);
        return this;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Confrontation teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public Confrontation addTeam(Team team) {
        this.teams.add(team);
        team.setConfrontation(this);
        return this;
    }

    public Confrontation removeTeam(Team team) {
        this.teams.remove(team);
        team.setConfrontation(null);
        return this;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Journee getJournee() {
        return journee;
    }

    public Confrontation journee(Journee journee) {
        this.journee = journee;
        return this;
    }

    public void setJournee(Journee journee) {
        this.journee = journee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Confrontation)) {
            return false;
        }
        return id != null && id.equals(((Confrontation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Confrontation{" +
            "id=" + getId() +
            ", confrontationName='" + getConfrontationName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", confrontationDetails='" + getConfrontationDetails() + "'" +
            "}";
    }
}
