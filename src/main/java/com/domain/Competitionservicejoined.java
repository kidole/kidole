package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.domain.enumeration.ServicesState;

/**
 * A Competitionservicejoined.
 */
@Entity
@Table(name = "competitionservicejoined")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Competitionservicejoined implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private ServicesState state;

    @Column(name = "details")
    private String details;

    @OneToOne
    @JoinColumn(unique = true)
    private PrestationService prestationService;

    @ManyToOne
    @JsonIgnoreProperties("competitionservicejoineds")
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServicesState getState() {
        return state;
    }

    public Competitionservicejoined state(ServicesState state) {
        this.state = state;
        return this;
    }

    public void setState(ServicesState state) {
        this.state = state;
    }

    public String getDetails() {
        return details;
    }

    public Competitionservicejoined details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public PrestationService getPrestationService() {
        return prestationService;
    }

    public Competitionservicejoined prestationService(PrestationService prestationService) {
        this.prestationService = prestationService;
        return this;
    }

    public void setPrestationService(PrestationService prestationService) {
        this.prestationService = prestationService;
    }

    public Competition getCompetition() {
        return competition;
    }

    public Competitionservicejoined competition(Competition competition) {
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
        if (!(o instanceof Competitionservicejoined)) {
            return false;
        }
        return id != null && id.equals(((Competitionservicejoined) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Competitionservicejoined{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
