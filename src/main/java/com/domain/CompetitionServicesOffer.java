package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CompetitionServicesOffer.
 */
@Entity
@Table(name = "competition_services_offer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompetitionServicesOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "details")
    private String details;

    @Column(name = "url")
    private String url;

    @OneToOne
    @JoinColumn(unique = true)
    private Rubrique rubrique;

    @OneToMany(mappedBy = "competitionServicesOffer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Files> files = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("competitionServicesOffers")
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

    public CompetitionServicesOffer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public CompetitionServicesOffer details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUrl() {
        return url;
    }

    public CompetitionServicesOffer url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Rubrique getRubrique() {
        return rubrique;
    }

    public CompetitionServicesOffer rubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
        return this;
    }

    public void setRubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
    }

    public Set<Files> getFiles() {
        return files;
    }

    public CompetitionServicesOffer files(Set<Files> files) {
        this.files = files;
        return this;
    }

    public CompetitionServicesOffer addFiles(Files files) {
        this.files.add(files);
        files.setCompetitionServicesOffer(this);
        return this;
    }

    public CompetitionServicesOffer removeFiles(Files files) {
        this.files.remove(files);
        files.setCompetitionServicesOffer(null);
        return this;
    }

    public void setFiles(Set<Files> files) {
        this.files = files;
    }

    public Competition getCompetition() {
        return competition;
    }

    public CompetitionServicesOffer competition(Competition competition) {
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
        if (!(o instanceof CompetitionServicesOffer)) {
            return false;
        }
        return id != null && id.equals(((CompetitionServicesOffer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompetitionServicesOffer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", details='" + getDetails() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
