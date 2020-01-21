package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

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
public class Confrontation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "debut")
    private Instant debut;

    @Column(name = "fin")
    private Instant fin;

    @Lob
    @Column(name = "details")
    private String details;

    @OneToOne
    @JoinColumn(unique = true)
    private MatchSheet matchSheet;

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

    public String getName() {
        return name;
    }

    public Confrontation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getDebut() {
        return debut;
    }

    public Confrontation debut(Instant debut) {
        this.debut = debut;
        return this;
    }

    public void setDebut(Instant debut) {
        this.debut = debut;
    }

    public Instant getFin() {
        return fin;
    }

    public Confrontation fin(Instant fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(Instant fin) {
        this.fin = fin;
    }

    public String getDetails() {
        return details;
    }

    public Confrontation details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public MatchSheet getMatchSheet() {
        return matchSheet;
    }

    public Confrontation matchSheet(MatchSheet matchSheet) {
        this.matchSheet = matchSheet;
        return this;
    }

    public void setMatchSheet(MatchSheet matchSheet) {
        this.matchSheet = matchSheet;
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
            ", name='" + getName() + "'" +
            ", debut='" + getDebut() + "'" +
            ", fin='" + getFin() + "'" +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
