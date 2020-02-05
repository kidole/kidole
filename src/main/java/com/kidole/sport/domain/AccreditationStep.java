package com.kidole.sport.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

import com.kidole.sport.domain.enumeration.AccreditationList;

/**
 * A AccreditationStep.
 */
@Entity
@Table(name = "accreditation_step")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "accreditationstep")
public class AccreditationStep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @NotNull
    @Column(name = "accreditation_stepnumber", nullable = false)
    private Integer accreditationStepnumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "accreditation_type", nullable = false)
    private AccreditationList accreditationType;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "uri")
    private Boolean uri;

    @Lob
    @Column(name = "fields")
    private String fields;

    @ManyToOne
    @JsonIgnoreProperties("accreditationSteps")
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public AccreditationStep startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public AccreditationStep endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Integer getAccreditationStepnumber() {
        return accreditationStepnumber;
    }

    public AccreditationStep accreditationStepnumber(Integer accreditationStepnumber) {
        this.accreditationStepnumber = accreditationStepnumber;
        return this;
    }

    public void setAccreditationStepnumber(Integer accreditationStepnumber) {
        this.accreditationStepnumber = accreditationStepnumber;
    }

    public AccreditationList getAccreditationType() {
        return accreditationType;
    }

    public AccreditationStep accreditationType(AccreditationList accreditationType) {
        this.accreditationType = accreditationType;
        return this;
    }

    public void setAccreditationType(AccreditationList accreditationType) {
        this.accreditationType = accreditationType;
    }

    public Boolean isIsPublic() {
        return isPublic;
    }

    public AccreditationStep isPublic(Boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean isUri() {
        return uri;
    }

    public AccreditationStep uri(Boolean uri) {
        this.uri = uri;
        return this;
    }

    public void setUri(Boolean uri) {
        this.uri = uri;
    }

    public String getFields() {
        return fields;
    }

    public AccreditationStep fields(String fields) {
        this.fields = fields;
        return this;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public Competition getCompetition() {
        return competition;
    }

    public AccreditationStep competition(Competition competition) {
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
        if (!(o instanceof AccreditationStep)) {
            return false;
        }
        return id != null && id.equals(((AccreditationStep) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AccreditationStep{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", accreditationStepnumber=" + getAccreditationStepnumber() +
            ", accreditationType='" + getAccreditationType() + "'" +
            ", isPublic='" + isIsPublic() + "'" +
            ", uri='" + isUri() + "'" +
            ", fields='" + getFields() + "'" +
            "}";
    }
}
