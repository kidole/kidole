package com.kidole.sport.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Rubrique.
 */
@Entity
@Table(name = "rubrique")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "rubrique")
public class Rubrique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "rubrique_name", length = 1024, nullable = false)
    private String rubriqueName;

    
    @Lob
    @Column(name = "rubrique_details", nullable = false)
    private String rubriqueDetails;

    @Lob
    @Column(name = "rubrique_image")
    private byte[] rubriqueImage;

    @Column(name = "rubrique_image_content_type")
    private String rubriqueImageContentType;

    @OneToMany(mappedBy = "rubrique")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Files> files = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRubriqueName() {
        return rubriqueName;
    }

    public Rubrique rubriqueName(String rubriqueName) {
        this.rubriqueName = rubriqueName;
        return this;
    }

    public void setRubriqueName(String rubriqueName) {
        this.rubriqueName = rubriqueName;
    }

    public String getRubriqueDetails() {
        return rubriqueDetails;
    }

    public Rubrique rubriqueDetails(String rubriqueDetails) {
        this.rubriqueDetails = rubriqueDetails;
        return this;
    }

    public void setRubriqueDetails(String rubriqueDetails) {
        this.rubriqueDetails = rubriqueDetails;
    }

    public byte[] getRubriqueImage() {
        return rubriqueImage;
    }

    public Rubrique rubriqueImage(byte[] rubriqueImage) {
        this.rubriqueImage = rubriqueImage;
        return this;
    }

    public void setRubriqueImage(byte[] rubriqueImage) {
        this.rubriqueImage = rubriqueImage;
    }

    public String getRubriqueImageContentType() {
        return rubriqueImageContentType;
    }

    public Rubrique rubriqueImageContentType(String rubriqueImageContentType) {
        this.rubriqueImageContentType = rubriqueImageContentType;
        return this;
    }

    public void setRubriqueImageContentType(String rubriqueImageContentType) {
        this.rubriqueImageContentType = rubriqueImageContentType;
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
            ", rubriqueName='" + getRubriqueName() + "'" +
            ", rubriqueDetails='" + getRubriqueDetails() + "'" +
            ", rubriqueImage='" + getRubriqueImage() + "'" +
            ", rubriqueImageContentType='" + getRubriqueImageContentType() + "'" +
            "}";
    }
}
