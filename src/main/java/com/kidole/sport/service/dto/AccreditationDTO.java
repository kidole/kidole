package com.kidole.sport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.kidole.sport.domain.enumeration.AccreditationList;
import com.kidole.sport.domain.enumeration.AccreditationState;

/**
 * A DTO for the {@link com.kidole.sport.domain.Accreditation} entity.
 */
public class AccreditationDTO implements Serializable {

    private Long id;

    @NotNull
    private AccreditationList accreditationName;

    @NotNull
    @Size(min = 3, max = 1024)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 1024)
    private String lastName;

    @NotNull
    @Size(min = 3, max = 1024)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String accreditationEmail;

    @NotNull
    private AccreditationState accreditationStatus;

    
    @Lob
    private String accreditationDetail;


    private Long competitionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccreditationList getAccreditationName() {
        return accreditationName;
    }

    public void setAccreditationName(AccreditationList accreditationName) {
        this.accreditationName = accreditationName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccreditationEmail() {
        return accreditationEmail;
    }

    public void setAccreditationEmail(String accreditationEmail) {
        this.accreditationEmail = accreditationEmail;
    }

    public AccreditationState getAccreditationStatus() {
        return accreditationStatus;
    }

    public void setAccreditationStatus(AccreditationState accreditationStatus) {
        this.accreditationStatus = accreditationStatus;
    }

    public String getAccreditationDetail() {
        return accreditationDetail;
    }

    public void setAccreditationDetail(String accreditationDetail) {
        this.accreditationDetail = accreditationDetail;
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

        AccreditationDTO accreditationDTO = (AccreditationDTO) o;
        if (accreditationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accreditationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccreditationDTO{" +
            "id=" + getId() +
            ", accreditationName='" + getAccreditationName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", accreditationEmail='" + getAccreditationEmail() + "'" +
            ", accreditationStatus='" + getAccreditationStatus() + "'" +
            ", accreditationDetail='" + getAccreditationDetail() + "'" +
            ", competitionId=" + getCompetitionId() +
            "}";
    }
}
