package com.kidole.sport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kidole.sport.domain.Localisation} entity.
 */
public class LocalisationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String localisationName;

    @NotNull
    private Double localisationLatitude;

    @NotNull
    private Double localisationLongitude;

    @NotNull
    @Size(min = 3, max = 1024)
    private String localisationCountry;

    @NotNull
    @Size(min = 3, max = 1024)
    private String localisationTown;

    @NotNull
    @Size(min = 3, max = 1024)
    private String localisationRegion;

    @NotNull
    @Size(min = 3, max = 1024)
    private String localisationLocality;

    @NotNull
    private Boolean isSite;


    private Long competitionId;

    private Long prestationServiceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalisationName() {
        return localisationName;
    }

    public void setLocalisationName(String localisationName) {
        this.localisationName = localisationName;
    }

    public Double getLocalisationLatitude() {
        return localisationLatitude;
    }

    public void setLocalisationLatitude(Double localisationLatitude) {
        this.localisationLatitude = localisationLatitude;
    }

    public Double getLocalisationLongitude() {
        return localisationLongitude;
    }

    public void setLocalisationLongitude(Double localisationLongitude) {
        this.localisationLongitude = localisationLongitude;
    }

    public String getLocalisationCountry() {
        return localisationCountry;
    }

    public void setLocalisationCountry(String localisationCountry) {
        this.localisationCountry = localisationCountry;
    }

    public String getLocalisationTown() {
        return localisationTown;
    }

    public void setLocalisationTown(String localisationTown) {
        this.localisationTown = localisationTown;
    }

    public String getLocalisationRegion() {
        return localisationRegion;
    }

    public void setLocalisationRegion(String localisationRegion) {
        this.localisationRegion = localisationRegion;
    }

    public String getLocalisationLocality() {
        return localisationLocality;
    }

    public void setLocalisationLocality(String localisationLocality) {
        this.localisationLocality = localisationLocality;
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
            ", localisationName='" + getLocalisationName() + "'" +
            ", localisationLatitude=" + getLocalisationLatitude() +
            ", localisationLongitude=" + getLocalisationLongitude() +
            ", localisationCountry='" + getLocalisationCountry() + "'" +
            ", localisationTown='" + getLocalisationTown() + "'" +
            ", localisationRegion='" + getLocalisationRegion() + "'" +
            ", localisationLocality='" + getLocalisationLocality() + "'" +
            ", isSite='" + isIsSite() + "'" +
            ", competitionId=" + getCompetitionId() +
            ", prestationServiceId=" + getPrestationServiceId() +
            "}";
    }
}
