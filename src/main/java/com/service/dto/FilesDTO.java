package com.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.domain.Files} entity.
 */
public class FilesDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private String publique;

    @Lob
    private byte[] content;

    private String contentContentType;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublique() {
        return publique;
    }

    public void setPublique(String publique) {
        this.publique = publique;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
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
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", publique='" + getPublique() + "'" +
            ", content='" + getContent() + "'" +
            ", competitionServicesOfferId=" + getCompetitionServicesOfferId() +
            ", prestationServiceId=" + getPrestationServiceId() +
            ", rubriqueId=" + getRubriqueId() +
            ", competitionId=" + getCompetitionId() +
            "}";
    }
}
