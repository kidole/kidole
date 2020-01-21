package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.domain.enumeration.AccreditationList;

/**
 * A AccreditationStep.
 */
@Entity
@Table(name = "accreditation_step")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AccreditationStep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "debut")
    private Instant debut;

    @Column(name = "fin")
    private Instant fin;

    @Column(name = "numero")
    private Integer numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AccreditationList type;

    @ManyToOne
    @JsonIgnoreProperties("accreditationSteps")
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDebut() {
        return debut;
    }

    public AccreditationStep debut(Instant debut) {
        this.debut = debut;
        return this;
    }

    public void setDebut(Instant debut) {
        this.debut = debut;
    }

    public Instant getFin() {
        return fin;
    }

    public AccreditationStep fin(Instant fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(Instant fin) {
        this.fin = fin;
    }

    public Integer getNumero() {
        return numero;
    }

    public AccreditationStep numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public AccreditationList getType() {
        return type;
    }

    public AccreditationStep type(AccreditationList type) {
        this.type = type;
        return this;
    }

    public void setType(AccreditationList type) {
        this.type = type;
    }

    public Competition getCompetition() {
        return competition;
    }

    public AccreditationStep competition(Competition competition) {
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
        if (!(o instanceof AccreditationStep)) {
            return false;
        }
        return id != null && id.equals(((AccreditationStep) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AccreditationStep{" +
            "id=" + getId() +
            ", debut='" + getDebut() + "'" +
            ", fin='" + getFin() + "'" +
            ", numero=" + getNumero() +
            ", type='" + getType() + "'" +
            "}";
    }
}
