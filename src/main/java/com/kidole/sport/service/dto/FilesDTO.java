package com.kidole.sport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.kidole.sport.domain.Files} entity.
 */
public class FilesDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String fileName;

    @NotNull
    @Size(min = 3, max = 1024)
    private String fileType;

    @NotNull
    private Boolean filePublic;

    
    @Lob
    private byte[] fileToUpload;

    private String fileToUploadContentType;

    private Long userId;

    private String userFirstName;

    private Long competitionServicesOfferId;

    private Long prestationServiceId;

    private Long rubriqueId;

    private Long competitionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Boolean isFilePublic() {
        return filePublic;
    }

    public void setFilePublic(Boolean filePublic) {
        this.filePublic = filePublic;
    }

    public byte[] getFileToUpload() {
        return fileToUpload;
    }

    public void setFileToUpload(byte[] fileToUpload) {
        this.fileToUpload = fileToUpload;
    }

    public String getFileToUploadContentType() {
        return fileToUploadContentType;
    }

    public void setFileToUploadContentType(String fileToUploadContentType) {
        this.fileToUploadContentType = fileToUploadContentType;
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

    public Long getCompetitionServicesOfferId() {
        return competitionServicesOfferId;
    }

    public void setCompetitionServicesOfferId(Long competitionServicesOfferId) {
        this.competitionServicesOfferId = competitionServicesOfferId;
    }

    public Long getPrestationServiceId() {
        return prestationServiceId;
    }

    public void setPrestationServiceId(Long prestationServiceId) {
        this.prestationServiceId = prestationServiceId;
    }

    public Long getRubriqueId() {
        return rubriqueId;
    }

    public void setRubriqueId(Long rubriqueId) {
        this.rubriqueId = rubriqueId;
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

        FilesDTO filesDTO = (FilesDTO) o;
        if (filesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FilesDTO{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", filePublic='" + isFilePublic() + "'" +
            ", fileToUpload='" + getFileToUpload() + "'" +
            ", userId=" + getUserId() +
            ", userFirstName='" + getUserFirstName() + "'" +
            ", competitionServicesOfferId=" + getCompetitionServicesOfferId() +
            ", prestationServiceId=" + getPrestationServiceId() +
            ", rubriqueId=" + getRubriqueId() +
            ", competitionId=" + getCompetitionId() +
            "}";
    }
}
