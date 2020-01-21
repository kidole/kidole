package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PrestationService.
 */
@Entity
@Table(name = "prestation_service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PrestationService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "detail")
    private String detail;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private Rubrique rubrique;

    @OneToMany(mappedBy = "prestationService")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Files> files = new HashSet<>();

    @OneToMany(mappedBy = "prestationService")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Localisation> localisations = new HashSet<>();

    @OneToOne(mappedBy = "prestationService")
    @JsonIgnore
    private Competitionservicejoined competitionservicejoined;

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

    public PrestationService name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public PrestationService detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public byte[] getImage() {
        return image;
    }

    public PrestationService image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public PrestationService imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Rubrique getRubrique() {
        return rubrique;
    }

    public PrestationService rubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
        return this;
    }

    public void setRubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
    }

    public Set<Files> getFiles() {
        return files;
    }

    public PrestationService files(Set<Files> files) {
        this.files = files;
        return this;
    }

    public PrestationService addFiles(Files files) {
        this.files.add(files);
        files.setPrestationService(this);
        return this;
    }

    public PrestationService removeFiles(Files files) {
        this.files.remove(files);
        files.setPrestationService(null);
        return this;
    }

    public void setFiles(Set<Files> files) {
        this.files = files;
    }

    public Set<Localisation> getLocalisations() {
        return localisations;
    }

    public PrestationService localisations(Set<Localisation> localisations) {
        this.localisations = localisations;
        return this;
    }

    public PrestationService addLocalisation(Localisation localisation) {
        this.localisations.add(localisation);
        localisation.setPrestationService(this);
        return this;
    }

    public PrestationService removeLocalisation(Localisation localisation) {
        this.localisations.remove(localisation);
        localisation.setPrestationService(null);
        return this;
    }

    public void setLocalisations(Set<Localisation> localisations) {
        this.localisations = localisations;
    }

    public Competitionservicejoined getCompetitionservicejoined() {
        return competitionservicejoined;
    }

    public PrestationService competitionservicejoined(Competitionservicejoined competitionservicejoined) {
        this.competitionservicejoined = competitionservicejoined;
        return this;
    }

    public void setCompetitionservicejoined(Competitionservicejoined competitionservicejoined) {
        this.competitionservicejoined = competitionservicejoined;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrestationService)) {
            return false;
        }
        return id != null && id.equals(((PrestationService) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PrestationService{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
