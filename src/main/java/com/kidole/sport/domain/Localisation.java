package com.kidole.sport.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Localisation.
 */
@Entity
@Table(name = "localisation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "localisation")
public class Localisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "localisation_name", length = 1024, nullable = false)
    private String localisationName;

    @NotNull
    @Column(name = "localisation_latitude", nullable = false)
    private Double localisationLatitude;

    @NotNull
    @Column(name = "localisation_longitude", nullable = false)
    private Double localisationLongitude;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "localisation_country", length = 1024, nullable = false)
    private String localisationCountry;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "localisation_town", length = 1024, nullable = false)
    private String localisationTown;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "localisation_region", length = 1024, nullable = false)
    private String localisationRegion;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "localisation_locality", length = 1024, nullable = false)
    private String localisationLocality;

    @NotNull
    @Column(name = "is_site", nullable = false)
    private Boolean isSite;

    @ManyToOne
    @JsonIgnoreProperties("localises")
    private Competition competition;

    @ManyToOne
    @JsonIgnoreProperties("localisations")
    private PrestationService prestationService;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalisationName() {
        return localisationName;
    }

    public Localisation localisationName(String localisationName) {
        this.localisationName = localisationName;
        return this;
    }

    public void setLocalisationName(String localisationName) {
        this.localisationName = localisationName;
    }

    public Double getLocalisationLatitude() {
        return localisationLatitude;
    }

    public Localisation localisationLatitude(Double localisationLatitude) {
        this.localisationLatitude = localisationLatitude;
        return this;
    }

    public void setLocalisationLatitude(Double localisationLatitude) {
        this.localisationLatitude = localisationLatitude;
    }

    public Double getLocalisationLongitude() {
        return localisationLongitude;
    }

    public Localisation localisationLongitude(Double localisationLongitude) {
        this.localisationLongitude = localisationLongitude;
        return this;
    }

    public void setLocalisationLongitude(Double localisationLongitude) {
        this.localisationLongitude = localisationLongitude;
    }

    public String getLocalisationCountry() {
        return localisationCountry;
    }

    public Localisation localisationCountry(String localisationCountry) {
        this.localisationCountry = localisationCountry;
        return this;
    }

    public void setLocalisationCountry(String localisationCountry) {
        this.localisationCountry = localisationCountry;
    }

    public String getLocalisationTown() {
        return localisationTown;
    }

    public Localisation localisationTown(String localisationTown) {
        this.localisationTown = localisationTown;
        return this;
    }

    public void setLocalisationTown(String localisationTown) {
        this.localisationTown = localisationTown;
    }

    public String getLocalisationRegion() {
        return localisationRegion;
    }

    public Localisation localisationRegion(String localisationRegion) {
        this.localisationRegion = localisationRegion;
        return this;
    }

    public void setLocalisationRegion(String localisationRegion) {
        this.localisationRegion = localisationRegion;
    }

    public String getLocalisationLocality() {
        return localisationLocality;
    }

    public Localisation localisationLocality(String localisationLocality) {
        this.localisationLocality = localisationLocality;
        return this;
    }

    public void setLocalisationLocality(String localisationLocality) {
        this.localisationLocality = localisationLocality;
    }

    public Boolean isIsSite() {
        return isSite;
    }

    public Localisation isSite(Boolean isSite) {
        this.isSite = isSite;
        return this;
    }

    public void setIsSite(Boolean isSite) {
        this.isSite = isSite;
    }

    public Competition getCompetition() {
        return competition;
    }

    public Localisation competition(Competition competition) {
        this.competition = competition;
        return this;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public PrestationService getPrestationService() {
        return prestationService;
    }

    public Localisation prestationService(PrestationService prestationService) {
        this.prestationService = prestationService;
        return this;
    }

    public void setPrestationService(PrestationService prestationService) {
        this.prestationService = prestationService;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Localisation)) {
            return false;
        }
        return id != null && id.equals(((Localisation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Localisation{" +
            "id=" + getId() +
            ", localisationName='" + getLocalisationName() + "'" +
            ", localisationLatitude=" + getLocalisationLatitude() +
            ", localisationLongitude=" + getLocalisationLongitude() +
            ", localisationCountry='" + getLocalisationCountry() + "'" +
            ", localisationTown='" + getLocalisationTown() + "'" +
            ", localisationRegion='" + getLocalisationRegion() + "'" +
            ", localisationLocality='" + getLocalisationLocality() + "'" +
            ", isSite='" + isIsSite() + "'" +
            "}";
    }
}
