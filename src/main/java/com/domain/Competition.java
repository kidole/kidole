package com.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

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
public class Competition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "debut")
    private Instant debut;

    @Column(name = "fin")
    private Instant fin;

    @Column(name = "date_limit")
    private Instant dateLimit;

    @Lob
    @Column(name = "detail")
    private String detail;

    @Lob
    @Column(name = "role")
    private byte[] role;

    @Column(name = "role_content_type")
    private String roleContentType;

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Localisation> localisations = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Accreditation> accreditateUsers = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompetitionServicesOffer> competitionServicesOffers = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AccreditationStep> accreditationSteps = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Competitionservicejoined> competitionservicejoineds = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Discipline> disciplines = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Options> options = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Format> formats = new HashSet<>();

    @OneToMany(mappedBy = "competition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Files> files = new HashSet<>();

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

    public Competition name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getDebut() {
        return debut;
    }

    public Competition debut(Instant debut) {
        this.debut = debut;
        return this;
    }

    public void setDebut(Instant debut) {
        this.debut = debut;
    }

    public Instant getFin() {
        return fin;
    }

    public Competition fin(Instant fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(Instant fin) {
        this.fin = fin;
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

    public byte[] getRole() {
        return role;
    }

    public Competition role(byte[] role) {
        this.role = role;
        return this;
    }

    public void setRole(byte[] role) {
        this.role = role;
    }

    public String getRoleContentType() {
        return roleContentType;
    }

    public Competition roleContentType(String roleContentType) {
        this.roleContentType = roleContentType;
        return this;
    }

    public void setRoleContentType(String roleContentType) {
        this.roleContentType = roleContentType;
    }

    public Set<Localisation> getLocalisations() {
        return localisations;
    }

    public Competition localisations(Set<Localisation> localisations) {
        this.localisations = localisations;
        return this;
    }

    public Competition addLocalisation(Localisation localisation) {
        this.localisations.add(localisation);
        localisation.setCompetition(this);
        return this;
    }

    public Competition removeLocalisation(Localisation localisation) {
        this.localisations.remove(localisation);
        localisation.setCompetition(null);
        return this;
    }

    public void setLocalisations(Set<Localisation> localisations) {
        this.localisations = localisations;
    }

    public Set<Accreditation> getAccreditateUsers() {
        return accreditateUsers;
    }

    public Competition accreditateUsers(Set<Accreditation> accreditations) {
        this.accreditateUsers = accreditations;
        return this;
    }

    public Competition addAccreditateUser(Accreditation accreditation) {
        this.accreditateUsers.add(accreditation);
        accreditation.setCompetition(this);
        return this;
    }

    public Competition removeAccreditateUser(Accreditation accreditation) {
        this.accreditateUsers.remove(accreditation);
        accreditation.setCompetition(null);
        return this;
    }

    public void setAccreditateUsers(Set<Accreditation> accreditations) {
        this.accreditateUsers = accreditations;
    }

    public Set<CompetitionServicesOffer> getCompetitionServicesOffers() {
        return competitionServicesOffers;
    }

    public Competition competitionServicesOffers(Set<CompetitionServicesOffer> competitionServicesOffers) {
        this.competitionServicesOffers = competitionServicesOffers;
        return this;
    }

    public Competition addCompetitionServicesOffer(CompetitionServicesOffer competitionServicesOffer) {
        this.competitionServicesOffers.add(competitionServicesOffer);
        competitionServicesOffer.setCompetition(this);
        return this;
    }

    public Competition removeCompetitionServicesOffer(CompetitionServicesOffer competitionServicesOffer) {
        this.competitionServicesOffers.remove(competitionServicesOffer);
        competitionServicesOffer.setCompetition(null);
        return this;
    }

    public void setCompetitionServicesOffers(Set<CompetitionServicesOffer> competitionServicesOffers) {
        this.competitionServicesOffers = competitionServicesOffers;
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

    public Set<Competitionservicejoined> getCompetitionservicejoineds() {
        return competitionservicejoineds;
    }

    public Competition competitionservicejoineds(Set<Competitionservicejoined> competitionservicejoineds) {
        this.competitionservicejoineds = competitionservicejoineds;
        return this;
    }

    public Competition addCompetitionservicejoined(Competitionservicejoined competitionservicejoined) {
        this.competitionservicejoineds.add(competitionservicejoined);
        competitionservicejoined.setCompetition(this);
        return this;
    }

    public Competition removeCompetitionservicejoined(Competitionservicejoined competitionservicejoined) {
        this.competitionservicejoineds.remove(competitionservicejoined);
        competitionservicejoined.setCompetition(null);
        return this;
    }

    public void setCompetitionservicejoineds(Set<Competitionservicejoined> competitionservicejoineds) {
        this.competitionservicejoineds = competitionservicejoineds;
    }

    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    public Competition disciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
        return this;
    }

    public Competition addDiscipline(Discipline discipline) {
        this.disciplines.add(discipline);
        discipline.setCompetition(this);
        return this;
    }

    public Competition removeDiscipline(Discipline discipline) {
        this.disciplines.remove(discipline);
        discipline.setCompetition(null);
        return this;
    }

    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public Set<Options> getOptions() {
        return options;
    }

    public Competition options(Set<Options> options) {
        this.options = options;
        return this;
    }

    public Competition addOptions(Options options) {
        this.options.add(options);
        options.setCompetition(this);
        return this;
    }

    public Competition removeOptions(Options options) {
        this.options.remove(options);
        options.setCompetition(null);
        return this;
    }

    public void setOptions(Set<Options> options) {
        this.options = options;
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
            ", name='" + getName() + "'" +
            ", debut='" + getDebut() + "'" +
            ", fin='" + getFin() + "'" +
            ", dateLimit='" + getDateLimit() + "'" +
            ", detail='" + getDetail() + "'" +
            ", role='" + getRole() + "'" +
            ", roleContentType='" + getRoleContentType() + "'" +
            "}";
    }
}
