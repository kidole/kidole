package com.kidole.sport.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.kidole.sport.domain.enumeration.AccreditationList;

import com.kidole.sport.domain.enumeration.AccreditationState;

/**
 * A Accreditation.
 */
@Entity
@Table(name = "accreditation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "accreditation")
public class Accreditation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "accreditation_name", nullable = false)
    private AccreditationList accreditationName;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "first_name", length = 1024, nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "last_name", length = 1024, nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 3, max = 1024)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "accreditation_email", length = 1024, nullable = false)
    private String accreditationEmail;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "accreditation_status", nullable = false)
    private AccreditationState accreditationStatus;

    
    @Lob
    @Column(name = "accreditation_detail", nullable = false)
    private String accreditationDetail;

    @ManyToOne
    @JsonIgnoreProperties("accreditations")
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccreditationList getAccreditationName() {
        return accreditationName;
    }

    public Accreditation accreditationName(AccreditationList accreditationName) {
        this.accreditationName = accreditationName;
        return this;
    }

    public void setAccreditationName(AccreditationList accreditationName) {
        this.accreditationName = accreditationName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Accreditation firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Accreditation lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccreditationEmail() {
        return accreditationEmail;
    }

    public Accreditation accreditationEmail(String accreditationEmail) {
        this.accreditationEmail = accreditationEmail;
        return this;
    }

    public void setAccreditationEmail(String accreditationEmail) {
        this.accreditationEmail = accreditationEmail;
    }

    public AccreditationState getAccreditationStatus() {
        return accreditationStatus;
    }

    public Accreditation accreditationStatus(AccreditationState accreditationStatus) {
        this.accreditationStatus = accreditationStatus;
        return this;
    }

    public void setAccreditationStatus(AccreditationState accreditationStatus) {
        this.accreditationStatus = accreditationStatus;
    }

    public String getAccreditationDetail() {
        return accreditationDetail;
    }

    public Accreditation accreditationDetail(String accreditationDetail) {
        this.accreditationDetail = accreditationDetail;
        return this;
    }

    public void setAccreditationDetail(String accreditationDetail) {
        this.accreditationDetail = accreditationDetail;
    }

    public Competition getCompetition() {
        return competition;
    }

    public Accreditation competition(Competition competition) {
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
        if (!(o instanceof Accreditation)) {
            return false;
        }
        return id != null && id.equals(((Accreditation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Accreditation{" +
            "id=" + getId() +
            ", accreditationName='" + getAccreditationName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", accreditationEmail='" + getAccreditationEmail() + "'" +
            ", accreditationStatus='" + getAccreditationStatus() + "'" +
            ", accreditationDetail='" + getAccreditationDetail() + "'" +
            "}";
    }
}
