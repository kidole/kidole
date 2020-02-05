package com.kidole.sport.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.kidole.sport.domain.enumeration.ServicesState;

/**
 * A PrestationService.
 */
@Entity
@Table(name = "prestation_service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "prestationservice")
public class PrestationService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "prestation_service_name", length = 1024, nullable = false)
    private String prestationServiceName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "prestation_service_name_state", nullable = false)
    private ServicesState prestationServiceNameState;

    
    @Lob
    @Column(name = "prestation_service_name_detail", nullable = false)
    private String prestationServiceNameDetail;

    @Lob
    @Column(name = "prestation_service_name_image")
    private byte[] prestationServiceNameImage;

    @Column(name = "prestation_service_name_image_content_type")
    private String prestationServiceNameImageContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private Rubrique rubrique;

    @OneToMany(mappedBy = "prestationService")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Files> files = new HashSet<>();

    @OneToMany(mappedBy = "prestationService")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Localisation> localisations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("prestationServices")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrestationServiceName() {
        return prestationServiceName;
    }

    public PrestationService prestationServiceName(String prestationServiceName) {
        this.prestationServiceName = prestationServiceName;
        return this;
    }

    public void setPrestationServiceName(String prestationServiceName) {
        this.prestationServiceName = prestationServiceName;
    }

    public ServicesState getPrestationServiceNameState() {
        return prestationServiceNameState;
    }

    public PrestationService prestationServiceNameState(ServicesState prestationServiceNameState) {
        this.prestationServiceNameState = prestationServiceNameState;
        return this;
    }

    public void setPrestationServiceNameState(ServicesState prestationServiceNameState) {
        this.prestationServiceNameState = prestationServiceNameState;
    }

    public String getPrestationServiceNameDetail() {
        return prestationServiceNameDetail;
    }

    public PrestationService prestationServiceNameDetail(String prestationServiceNameDetail) {
        this.prestationServiceNameDetail = prestationServiceNameDetail;
        return this;
    }

    public void setPrestationServiceNameDetail(String prestationServiceNameDetail) {
        this.prestationServiceNameDetail = prestationServiceNameDetail;
    }

    public byte[] getPrestationServiceNameImage() {
        return prestationServiceNameImage;
    }

    public PrestationService prestationServiceNameImage(byte[] prestationServiceNameImage) {
        this.prestationServiceNameImage = prestationServiceNameImage;
        return this;
    }

    public void setPrestationServiceNameImage(byte[] prestationServiceNameImage) {
        this.prestationServiceNameImage = prestationServiceNameImage;
    }

    public String getPrestationServiceNameImageContentType() {
        return prestationServiceNameImageContentType;
    }

    public PrestationService prestationServiceNameImageContentType(String prestationServiceNameImageContentType) {
        this.prestationServiceNameImageContentType = prestationServiceNameImageContentType;
        return this;
    }

    public void setPrestationServiceNameImageContentType(String prestationServiceNameImageContentType) {
        this.prestationServiceNameImageContentType = prestationServiceNameImageContentType;
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

    public User getUser() {
        return user;
    }

    public PrestationService user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
            ", prestationServiceName='" + getPrestationServiceName() + "'" +
            ", prestationServiceNameState='" + getPrestationServiceNameState() + "'" +
            ", prestationServiceNameDetail='" + getPrestationServiceNameDetail() + "'" +
            ", prestationServiceNameImage='" + getPrestationServiceNameImage() + "'" +
            ", prestationServiceNameImageContentType='" + getPrestationServiceNameImageContentType() + "'" +
            "}";
    }
}
