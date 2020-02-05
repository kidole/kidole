package com.kidole.sport.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.kidole.sport.domain.Confrontation} entity.
 */
public class ConfrontationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String confrontationName;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    @Lob
    private String confrontationDetails;


    private Long matchsheetId;

    private String matchsheetMatchSheetName;

    private Long localisationId;

    private String localisationLocalisationName;

    private Long journeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfrontationName() {
        return confrontationName;
    }

    public void setConfrontationName(String confrontationName) {
        this.confrontationName = confrontationName;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getConfrontationDetails() {
        return confrontationDetails;
    }

    public void setConfrontationDetails(String confrontationDetails) {
        this.confrontationDetails = confrontationDetails;
    }

    public Long getMatchsheetId() {
        return matchsheetId;
    }

    public void setMatchsheetId(Long matchSheetId) {
        this.matchsheetId = matchSheetId;
    }

    public String getMatchsheetMatchSheetName() {
        return matchsheetMatchSheetName;
    }

    public void setMatchsheetMatchSheetName(String matchSheetMatchSheetName) {
        this.matchsheetMatchSheetName = matchSheetMatchSheetName;
    }

    public Long getLocalisationId() {
        return localisationId;
    }

    public void setLocalisationId(Long localisationId) {
        this.localisationId = localisationId;
    }

    public String getLocalisationLocalisationName() {
        return localisationLocalisationName;
    }

    public void setLocalisationLocalisationName(String localisationLocalisationName) {
        this.localisationLocalisationName = localisationLocalisationName;
    }

    public Long getJourneeId() {
        return journeeId;
    }

    public void setJourneeId(Long journeeId) {
        this.journeeId = journeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConfrontationDTO confrontationDTO = (ConfrontationDTO) o;
        if (confrontationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), confrontationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConfrontationDTO{" +
            "id=" + getId() +
            ", confrontationName='" + getConfrontationName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", confrontationDetails='" + getConfrontationDetails() + "'" +
            ", matchsheetId=" + getMatchsheetId() +
            ", matchsheetMatchSheetName='" + getMatchsheetMatchSheetName() + "'" +
            ", localisationId=" + getLocalisationId() +
            ", localisationLocalisationName='" + getLocalisationLocalisationName() + "'" +
            ", journeeId=" + getJourneeId() +
            "}";
    }
}
