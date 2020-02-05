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

/**
 * A CompetitionServicesOffer.
 */
@Entity
@Table(name = "competition_services_offer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "competitionservicesoffer")
public class CompetitionServicesOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "competition_services_offer_name", length = 1024, nullable = false)
    private String competitionServicesOfferName;

    
    @Lob
    @Column(name = "competition_services_offer_detail", nullable = false)
    private String competitionServicesOfferDetail;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "competition_services_offer_url", length = 1024, nullable = false)
    private String competitionServicesOfferUrl;

    @OneToOne
    @JoinColumn(unique = true)
    private Rubrique rubric;

    @OneToMany(mappedBy = "competitionServicesOffer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Files> files = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("competitionServices")
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompetitionServicesOfferName() {
        return competitionServicesOfferName;
    }

    public CompetitionServicesOffer competitionServicesOfferName(String competitionServicesOfferName) {
        this.competitionServicesOfferName = competitionServicesOfferName;
        return this;
    }

    public void setCompetitionServicesOfferName(String competitionServicesOfferName) {
        this.competitionServicesOfferName = competitionServicesOfferName;
    }

    public String getCompetitionServicesOfferDetail() {
        return competitionServicesOfferDetail;
    }

    public CompetitionServicesOffer competitionServicesOfferDetail(String competitionServicesOfferDetail) {
        this.competitionServicesOfferDetail = competitionServicesOfferDetail;
        return this;
    }

    public void setCompetitionServicesOfferDetail(String competitionServicesOfferDetail) {
        this.competitionServicesOfferDetail = competitionServicesOfferDetail;
    }

    public String getCompetitionServicesOfferUrl() {
        return competitionServicesOfferUrl;
    }

    public CompetitionServicesOffer competitionServicesOfferUrl(String competitionServicesOfferUrl) {
        this.competitionServicesOfferUrl = competitionServicesOfferUrl;
        return this;
    }

    public void setCompetitionServicesOfferUrl(String competitionServicesOfferUrl) {
        this.competitionServicesOfferUrl = competitionServicesOfferUrl;
    }

    public Rubrique getRubric() {
        return rubric;
    }

    public CompetitionServicesOffer rubric(Rubrique rubrique) {
        this.rubric = rubrique;
        return this;
    }

    public void setRubric(Rubrique rubrique) {
        this.rubric = rubrique;
    }

    public Set<Files> getFiles() {
        return files;
    }

    public CompetitionServicesOffer files(Set<Files> files) {
        this.files = files;
        return this;
    }

    public CompetitionServicesOffer addFile(Files files) {
        this.files.add(files);
        files.setCompetitionServicesOffer(this);
        return this;
    }

    public CompetitionServicesOffer removeFile(Files files) {
        this.files.remove(files);
        files.setCompetitionServicesOffer(null);
        return this;
    }

    public void setFiles(Set<Files> files) {
        this.files = files;
    }

    public Competition getCompetition() {
        return competition;
    }

    public CompetitionServicesOffer competition(Competition competition) {
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
        if (!(o instanceof CompetitionServicesOffer)) {
            return false;
        }
        return id != null && id.equals(((CompetitionServicesOffer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompetitionServicesOffer{" +
            "id=" + getId() +
            ", competitionServicesOfferName='" + getCompetitionServicesOfferName() + "'" +
            ", competitionServicesOfferDetail='" + getCompetitionServicesOfferDetail() + "'" +
            ", competitionServicesOfferUrl='" + getCompetitionServicesOfferUrl() + "'" +
            "}";
    }
}
