package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.domain.enumeration.AccreditationList;

import com.domain.enumeration.AccreditationState;

/**
 * A Accreditation.
 */
@Entity
@Table(name = "accreditation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Accreditation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private AccreditationList name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccreditationState status;

    @Column(name = "details")
    private String details;

    @ManyToOne
    @JsonIgnoreProperties("accreditateUsers")
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccreditationList getName() {
        return name;
    }

    public Accreditation name(AccreditationList name) {
        this.name = name;
        return this;
    }

    public void setName(AccreditationList name) {
        this.name = name;
    }

    public AccreditationState getStatus() {
        return status;
    }

    public Accreditation status(AccreditationState status) {
        this.status = status;
        return this;
    }

    public void setStatus(AccreditationState status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public Accreditation details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Competition getCompetition() {
        return competition;
    }

    public Accreditation competition(Competition competition) {
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
        if (!(o instanceof Accreditation)) {
            return false;
        }
        return id != null && id.equals(((Accreditation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Accreditation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
