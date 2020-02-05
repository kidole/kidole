package com.kidole.sport.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Format.
 */
@Entity
@Table(name = "format")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "format")
public class Format implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "format_name", length = 1024, nullable = false)
    private String formatName;

    @Min(value = 0)
    @Column(name = "winer_qty")
    private Integer winerQty;

    @OneToOne
    @JoinColumn(unique = true)
    private Phase phase;

    @ManyToOne
    @JsonIgnoreProperties("formats")
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormatName() {
        return formatName;
    }

    public Format formatName(String formatName) {
        this.formatName = formatName;
        return this;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public Integer getWinerQty() {
        return winerQty;
    }

    public Format winerQty(Integer winerQty) {
        this.winerQty = winerQty;
        return this;
    }

    public void setWinerQty(Integer winerQty) {
        this.winerQty = winerQty;
    }

    public Phase getPhase() {
        return phase;
    }

    public Format phase(Phase phase) {
        this.phase = phase;
        return this;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Competition getCompetition() {
        return competition;
    }

    public Format competition(Competition competition) {
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
        if (!(o instanceof Format)) {
            return false;
        }
        return id != null && id.equals(((Format) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Format{" +
            "id=" + getId() +
            ", formatName='" + getFormatName() + "'" +
            ", winerQty=" + getWinerQty() +
            "}";
    }
}
