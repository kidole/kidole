package com.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.domain.Localisation} entity.
 */
public class LocalisationDTO implements Serializable {

    private Long id;

    private String name;

    private Double latitude;

    private Double longitude;

    private String country;

    private String town;

    private String region;

    private String locality;

    private Boolean isSite;


    private Long competitionId;

    private Long prestationServiceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Boolean isIsSite() {
        return isSite;
    }

    public void setIsSite(Boolean isSite) {
        this.isSite = isSite;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public Long getPrestationServiceId() {
        return prestationServiceId;
    }

    public void setPrestationServiceId(Long prestationServiceId) {
        this.prestationServiceId = prestationServiceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocalisationDTO localisationDTO = (LocalisationDTO) o;
        if (localisationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), localisationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocalisationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", country='" + getCountry() + "'" +
            ", town='" + getTown() + "'" +
            ", region='" + getRegion() + "'" +
            ", locality='" + getLocality() + "'" +
            ", isSite='" + isIsSite() + "'" +
            ", competitionId=" + getCompetitionId() +
            ", prestationServiceId=" + getPrestationServiceId() +
            "}";
    }
}
