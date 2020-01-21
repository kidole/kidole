package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Rubrique.
 */
@Entity
@Table(name = "rubrique")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rubrique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "details")
    private String details;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @OneToMany(mappedBy = "rubrique")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Files> files = new HashSet<>();

    @OneToOne(mappedBy = "rubrique")
    @JsonIgnore
    private PrestationService prestationService;

    @OneToOne(mappedBy = "rubrique")
    @JsonIgnore
    private CompetitionServicesOffer competitionServicesOffer;

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

    public Rubrique name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public Rubrique details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public byte[] getImage() {
        return image;
    }

    public Rubrique image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Rubrique imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Set<Files> getFiles() {
        return files;
    }

    public Rubrique files(Set<Files> files) {
        this.files = files;
        return this;
    }

    public Rubrique addFiles(Files files) {
        this.files.add(files);
        files.setRubrique(this);
        return this;
    }

    public Rubrique removeFiles(Files files) {
        this.files.remove(files);
        files.setRubrique(null);
        return this;
    }

    public void setFiles(Set<Files> files) {
        this.files = files;
    }

    public PrestationService getPrestationService() {
        return prestationService;
    }

    public Rubrique prestationService(PrestationService prestationService) {
        this.prestationService = prestationService;
        return this;
    }

    public void setPrestationService(PrestationService prestationService) {
        this.prestationService = prestationService;
    }

    public CompetitionServicesOffer getCompetitionServicesOffer() {
        return competitionServicesOffer;
    }

    public Rubrique competitionServicesOffer(CompetitionServicesOffer competitionServicesOffer) {
        this.competitionServicesOffer = competitionServicesOffer;
        return this;
    }

    public void setCompetitionServicesOffer(CompetitionServicesOffer competitionServicesOffer) {
        this.competitionServicesOffer = competitionServicesOffer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rubrique)) {
            return false;
        }
        return id != null && id.equals(((Rubrique) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Rubrique{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", details='" + getDetails() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
