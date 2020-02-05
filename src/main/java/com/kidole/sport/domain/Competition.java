package com.kidole.sport.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Competition.
 */
@Entity
@Table(name = "competition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "competition")
public class Competition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "competition_name", length = 1024, nullable = false)
    private String competitionName;

    @NotNull
    @Column(name = "start", nullable = false)
    private Instant start;

    @NotNull
    @Column(name = "end", nullable = false)
    private Instant end;

    @NotNull
    @Column(name = "date_limit", nullable = false)
    private Instant dateLimit;

    
    @Lob
    @Column(name = "detail", nullable = false)
    private String detail;

    
    @Lob
    @Column(name = "rule", nullable = false)
    private byte[] rule;

    @Column(name = "rule_content_type", nullable = false)
    private String ruleContentType;

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Localisation> localises = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Accreditation> accreditations = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompetitionServicesOffer> competitionServices = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AccreditationStep> accreditationSteps = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Format> formats = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Files> files = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("competitions")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public Competition competitionName(String competitionName) {
        this.competitionName = competitionName;
        return this;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public Instant getStart() {
        return start;
    }

    public Competition start(Instant start) {
        this.start = start;
        return this;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public Competition end(Instant end) {
        this.end = end;
        return this;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Instant getDateLimit() {
        return dateLimit;
    }

    public Competition dateLimit(Instant dateLimit) {
        this.dateLimit = dateLimit;
        return this;
    }

    public void setDateLimit(Instant dateLimit) {
        this.dateLimit = dateLimit;
    }

    public String getDetail() {
        return detail;
    }

    public Competition detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public byte[] getRule() {
        return rule;
    }

    public Competition rule(byte[] rule) {
        this.rule = rule;
        return this;
    }

    public void setRule(byte[] rule) {
        this.rule = rule;
    }

    public String getRuleContentType() {
        return ruleContentType;
    }

    public Competition ruleContentType(String ruleContentType) {
        this.ruleContentType = ruleContentType;
        return this;
    }

    public void setRuleContentType(String ruleContentType) {
        this.ruleContentType = ruleContentType;
    }

    public Set<Localisation> getLocalises() {
        return localises;
    }

    public Competition localises(Set<Localisation> localisations) {
        this.localises = localisations;
        return this;
    }

    public Competition addLocalise(Localisation localisation) {
        this.localises.add(localisation);
        localisation.setCompetition(this);
        return this;
    }

    public Competition removeLocalise(Localisation localisation) {
        this.localises.remove(localisation);
        localisation.setCompetition(null);
        return this;
    }

    public void setLocalises(Set<Localisation> localisations) {
        this.localises = localisations;
    }

    public Set<Accreditation> getAccreditations() {
        return accreditations;
    }

    public Competition accreditations(Set<Accreditation> accreditations) {
        this.accreditations = accreditations;
        return this;
    }

    public Competition addAccreditation(Accreditation accreditation) {
        this.accreditations.add(accreditation);
        accreditation.setCompetition(this);
        return this;
    }

    public Competition removeAccreditation(Accreditation accreditation) {
        this.accreditations.remove(accreditation);
        accreditation.setCompetition(null);
        return this;
    }

    public void setAccreditations(Set<Accreditation> accreditations) {
        this.accreditations = accreditations;
    }

    public Set<CompetitionServicesOffer> getCompetitionServices() {
        return competitionServices;
    }

    public Competition competitionServices(Set<CompetitionServicesOffer> competitionServicesOffers) {
        this.competitionServices = competitionServicesOffers;
        return this;
    }

    public Competition addCompetitionService(CompetitionServicesOffer competitionServicesOffer) {
        this.competitionServices.add(competitionServicesOffer);
        competitionServicesOffer.setCompetition(this);
        return this;
    }

    public Competition removeCompetitionService(CompetitionServicesOffer competitionServicesOffer) {
        this.competitionServices.remove(competitionServicesOffer);
        competitionServicesOffer.setCompetition(null);
        return this;
    }

    public void setCompetitionServices(Set<CompetitionServicesOffer> competitionServicesOffers) {
        this.competitionServices = competitionServicesOffers;
    }

    public Set<AccreditationStep> getAccreditationSteps() {
        return accreditationSteps;
    }

    public Competition accreditationSteps(Set<AccreditationStep> accreditationSteps) {
        this.accreditationSteps = accreditationSteps;
        return this;
    }

    public Competition addAccreditationStep(AccreditationStep accreditationStep) {
        this.accreditationSteps.add(accreditationStep);
        accreditationStep.setCompetition(this);
        return this;
    }

    public Competition removeAccreditationStep(AccreditationStep accreditationStep) {
        this.accreditationSteps.remove(accreditationStep);
        accreditationStep.setCompetition(null);
        return this;
    }

    public void setAccreditationSteps(Set<AccreditationStep> accreditationSteps) {
        this.accreditationSteps = accreditationSteps;
    }

    public Set<Format> getFormats() {
        return formats;
    }

    public Competition formats(Set<Format> formats) {
        this.formats = formats;
        return this;
    }

    public Competition addFormat(Format format) {
        this.formats.add(format);
        format.setCompetition(this);
        return this;
    }

    public Competition removeFormat(Format format) {
        this.formats.remove(format);
        format.setCompetition(null);
        return this;
    }

    public void setFormats(Set<Format> formats) {
        this.formats = formats;
    }

    public Set<Files> getFiles() {
        return files;
    }

    public Competition files(Set<Files> files) {
        this.files = files;
        return this;
    }

    public Competition addFiles(Files files) {
        this.files.add(files);
        files.setCompetition(this);
        return this;
    }

    public Competition removeFiles(Files files) {
        this.files.remove(files);
        files.setCompetition(null);
        return this;
    }

    public void setFiles(Set<Files> files) {
        this.files = files;
    }

    public User getUser() {
        return user;
    }

    public Competition user(User user) {
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
        if (!(o instanceof Competition)) {
            return false;
        }
        return id != null && id.equals(((Competition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Competition{" +
            "id=" + getId() +
            ", competitionName='" + getCompetitionName() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", dateLimit='" + getDateLimit() + "'" +
            ", detail='" + getDetail() + "'" +
            ", rule='" + getRule() + "'" +
            ", ruleContentType='" + getRuleContentType() + "'" +
            "}";
    }
}
