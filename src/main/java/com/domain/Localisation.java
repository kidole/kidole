package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Localisation.
 */
@Entity
@Table(name = "localisation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Localisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "country")
    private String country;

    @Column(name = "town")
    private String town;

    @Column(name = "region")
    private String region;

    @Column(name = "locality")
    private String locality;

    @Column(name = "is_site")
    private Boolean isSite;

    @OneToOne(mappedBy = "localisation")
    @JsonIgnore
    private Confrontation confrontation;

    @ManyToOne
    @JsonIgnoreProperties("localisations")
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

    public String getName() {
        return name;
    }

    public Localisation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Localisation latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Localisation longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public Localisation country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public Localisation town(String town) {
        this.town = town;
        return this;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getRegion() {
        return region;
    }

    public Localisation region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocality() {
        return locality;
    }

    public Localisation locality(String locality) {
        this.locality = locality;
        return this;
    }

    public void setLocality(String locality) {
        this.locality = locality;
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

    public Confrontation getConfrontation() {
        return confrontation;
    }

    public Localisation confrontation(Confrontation confrontation) {
        this.confrontation = confrontation;
        return this;
    }

    public void setConfrontation(Confrontation confrontation) {
        this.confrontation = confrontation;
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
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", country='" + getCountry() + "'" +
            ", town='" + getTown() + "'" +
            ", region='" + getRegion() + "'" +
            ", locality='" + getLocality() + "'" +
            ", isSite='" + isIsSite() + "'" +
            "}";
    }
}
