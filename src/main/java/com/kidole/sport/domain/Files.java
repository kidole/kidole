package com.kidole.sport.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Files.
 */
@Entity
@Table(name = "files")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "files")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "file_name", length = 1024, nullable = false)
    private String fileName;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "file_type", length = 1024, nullable = false)
    private String fileType;

    @NotNull
    @Column(name = "file_public", nullable = false)
    private Boolean filePublic;

    
    @Lob
    @Column(name = "file_to_upload", nullable = false)
    private byte[] fileToUpload;

    @Column(name = "file_to_upload_content_type", nullable = false)
    private String fileToUploadContentType;

    @ManyToOne
    @JsonIgnoreProperties("files")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("files")
    private CompetitionServicesOffer competitionServicesOffer;

    @ManyToOne
    @JsonIgnoreProperties("files")
    private PrestationService prestationService;

    @ManyToOne
    @JsonIgnoreProperties("files")
    private Rubrique rubrique;

    @ManyToOne
    @JsonIgnoreProperties("files")
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public Files fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public Files fileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Boolean isFilePublic() {
        return filePublic;
    }

    public Files filePublic(Boolean filePublic) {
        this.filePublic = filePublic;
        return this;
    }

    public void setFilePublic(Boolean filePublic) {
        this.filePublic = filePublic;
    }

    public byte[] getFileToUpload() {
        return fileToUpload;
    }

    public Files fileToUpload(byte[] fileToUpload) {
        this.fileToUpload = fileToUpload;
        return this;
    }

    public void setFileToUpload(byte[] fileToUpload) {
        this.fileToUpload = fileToUpload;
    }

    public String getFileToUploadContentType() {
        return fileToUploadContentType;
    }

    public Files fileToUploadContentType(String fileToUploadContentType) {
        this.fileToUploadContentType = fileToUploadContentType;
        return this;
    }

    public void setFileToUploadContentType(String fileToUploadContentType) {
        this.fileToUploadContentType = fileToUploadContentType;
    }

    public User getUser() {
        return user;
    }

    public Files user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CompetitionServicesOffer getCompetitionServicesOffer() {
        return competitionServicesOffer;
    }

    public Files competitionServicesOffer(CompetitionServicesOffer competitionServicesOffer) {
        this.competitionServicesOffer = competitionServicesOffer;
        return this;
    }

    public void setCompetitionServicesOffer(CompetitionServicesOffer competitionServicesOffer) {
        this.competitionServicesOffer = competitionServicesOffer;
    }

    public PrestationService getPrestationService() {
        return prestationService;
    }

    public Files prestationService(PrestationService prestationService) {
        this.prestationService = prestationService;
        return this;
    }

    public void setPrestationService(PrestationService prestationService) {
        this.prestationService = prestationService;
    }

    public Rubrique getRubrique() {
        return rubrique;
    }

    public Files rubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
        return this;
    }

    public void setRubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
    }

    public Competition getCompetition() {
        return competition;
    }

    public Files competition(Competition competition) {
        this.competition = competition;
        return this;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Files)) {
            return false;
        }
        return id != null && id.equals(((Files) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Files{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", filePublic='" + isFilePublic() + "'" +
            ", fileToUpload='" + getFileToUpload() + "'" +
            ", fileToUploadContentType='" + getFileToUploadContentType() + "'" +
            "}";
    }
}
