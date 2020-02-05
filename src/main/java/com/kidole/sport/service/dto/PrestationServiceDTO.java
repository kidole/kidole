package com.kidole.sport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.kidole.sport.domain.enumeration.ServicesState;

/**
 * A DTO for the {@link com.kidole.sport.domain.PrestationService} entity.
 */
public class PrestationServiceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String prestationServiceName;

    @NotNull
    private ServicesState prestationServiceNameState;

    
    @Lob
    private String prestationServiceNameDetail;

    @Lob
    private byte[] prestationServiceNameImage;

    private String prestationServiceNameImageContentType;

    private Long rubriqueId;

    private String rubriqueRubriqueName;

    private Long userId;

    private String userFirstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrestationServiceName() {
        return prestationServiceName;
    }

    public void setPrestationServiceName(String prestationServiceName) {
        this.prestationServiceName = prestationServiceName;
    }

    public ServicesState getPrestationServiceNameState() {
        return prestationServiceNameState;
    }

    public void setPrestationServiceNameState(ServicesState prestationServiceNameState) {
        this.prestationServiceNameState = prestationServiceNameState;
    }

    public String getPrestationServiceNameDetail() {
        return prestationServiceNameDetail;
    }

    public void setPrestationServiceNameDetail(String prestationServiceNameDetail) {
        this.prestationServiceNameDetail = prestationServiceNameDetail;
    }

    public byte[] getPrestationServiceNameImage() {
        return prestationServiceNameImage;
    }

    public void setPrestationServiceNameImage(byte[] prestationServiceNameImage) {
        this.prestationServiceNameImage = prestationServiceNameImage;
    }

    public String getPrestationServiceNameImageContentType() {
        return prestationServiceNameImageContentType;
    }

    public void setPrestationServiceNameImageContentType(String prestationServiceNameImageContentType) {
        this.prestationServiceNameImageContentType = prestationServiceNameImageContentType;
    }

    public Long getRubriqueId() {
        return rubriqueId;
    }

    public void setRubriqueId(Long rubriqueId) {
        this.rubriqueId = rubriqueId;
    }

    public String getRubriqueRubriqueName() {
        return rubriqueRubriqueName;
    }

    public void setRubriqueRubriqueName(String rubriqueRubriqueName) {
        this.rubriqueRubriqueName = rubriqueRubriqueName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrestationServiceDTO prestationServiceDTO = (PrestationServiceDTO) o;
        if (prestationServiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prestationServiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrestationServiceDTO{" +
            "id=" + getId() +
            ", prestationServiceName='" + getPrestationServiceName() + "'" +
            ", prestationServiceNameState='" + getPrestationServiceNameState() + "'" +
            ", prestationServiceNameDetail='" + getPrestationServiceNameDetail() + "'" +
            ", prestationServiceNameImage='" + getPrestationServiceNameImage() + "'" +
            ", rubriqueId=" + getRubriqueId() +
            ", rubriqueRubriqueName='" + getRubriqueRubriqueName() + "'" +
            ", userId=" + getUserId() +
            ", userFirstName='" + getUserFirstName() + "'" +
            "}";
    }
}
