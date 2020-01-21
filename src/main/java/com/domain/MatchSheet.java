package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A MatchSheet.
 */
@Entity
@Table(name = "match_sheet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MatchSheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "resume")
    private String resume;

    @Column(name = "isfirst")
    private Boolean isfirst;

    @OneToOne(mappedBy = "matchSheet")
    @JsonIgnore
    private Confrontation confrontation;

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

    public MatchSheet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResume() {
        return resume;
    }

    public MatchSheet resume(String resume) {
        this.resume = resume;
        return this;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Boolean isIsfirst() {
        return isfirst;
    }

    public MatchSheet isfirst(Boolean isfirst) {
        this.isfirst = isfirst;
        return this;
    }

    public void setIsfirst(Boolean isfirst) {
        this.isfirst = isfirst;
    }

    public Confrontation getConfrontation() {
        return confrontation;
    }

    public MatchSheet confrontation(Confrontation confrontation) {
        this.confrontation = confrontation;
        return this;
    }

    public void setConfrontation(Confrontation confrontation) {
        this.confrontation = confrontation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchSheet)) {
            return false;
        }
        return id != null && id.equals(((MatchSheet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MatchSheet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", resume='" + getResume() + "'" +
            ", isfirst='" + isIsfirst() + "'" +
            "}";
    }
}
