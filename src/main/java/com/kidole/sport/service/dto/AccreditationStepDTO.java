package com.kidole.sport.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.kidole.sport.domain.enumeration.AccreditationList;

/**
 * A DTO for the {@link com.kidole.sport.domain.AccreditationStep} entity.
 */
public class AccreditationStepDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;

    @NotNull
    private Integer accreditationStepnumber;

    @NotNull
    private AccreditationList accreditationType;

    private Boolean isPublic;

    private Boolean uri;

    @Lob
    private String fields;


    private Long competitionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Integer getAccreditationStepnumber() {
        return accreditationStepnumber;
    }

    public void setAccreditationStepnumber(Integer accreditationStepnumber) {
        this.accreditationStepnumber = accreditationStepnumber;
    }

    public AccreditationList getAccreditationType() {
        return accreditationType;
    }

    public void setAccreditationType(AccreditationList accreditationType) {
        this.accreditationType = accreditationType;
    }

    public Boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean isUri() {
        return uri;
    }

    public void setUri(Boolean uri) {
        this.uri = uri;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccreditationStepDTO accreditationStepDTO = (AccreditationStepDTO) o;
        if (accreditationStepDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accreditationStepDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccreditationStepDTO{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", accreditationStepnumber=" + getAccreditationStepnumber() +
            ", accreditationType='" + getAccreditationType() + "'" +
            ", isPublic='" + isIsPublic() + "'" +
            ", uri='" + isUri() + "'" +
            ", fields='" + getFields() + "'" +
            ", competitionId=" + getCompetitionId() +
            "}";
    }
}
