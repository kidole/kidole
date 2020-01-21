package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Files.
 */
@Entity
@Table(name = "files")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "publique")
    private String publique;

    @Lob
    @Column(name = "content")
    private byte[] content;

    @Column(name = "content_content_type")
    private String contentContentType;

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

    public String getName() {
        return name;
    }

    public Files name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public Files type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublique() {
        return publique;
    }

    public Files publique(String publique) {
        this.publique = publique;
        return this;
    }

    public void setPublique(String publique) {
        this.publique = publique;
    }

    public byte[] getContent() {
        return content;
    }

    public Files content(byte[] content) {
        this.content = content;
        return this;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public Files contentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
        return this;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
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
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", publique='" + getPublique() + "'" +
            ", content='" + getContent() + "'" +
            ", contentContentType='" + getContentContentType() + "'" +
            "}";
    }
}
