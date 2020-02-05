package com.kidole.sport.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A MatchSheet.
 */
@Entity
@Table(name = "match_sheet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "matchsheet")
public class MatchSheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "match_sheet_name", length = 1024, nullable = false)
    private String matchSheetName;

    
    @Lob
    @Column(name = "match_sheet_resume", nullable = false)
    private String matchSheetResume;

    @Column(name = "isfirst")
    private Boolean isfirst;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatchSheetName() {
        return matchSheetName;
    }

    public MatchSheet matchSheetName(String matchSheetName) {
        this.matchSheetName = matchSheetName;
        return this;
    }

    public void setMatchSheetName(String matchSheetName) {
        this.matchSheetName = matchSheetName;
    }

    public String getMatchSheetResume() {
        return matchSheetResume;
    }

    public MatchSheet matchSheetResume(String matchSheetResume) {
        this.matchSheetResume = matchSheetResume;
        return this;
    }

    public void setMatchSheetResume(String matchSheetResume) {
        this.matchSheetResume = matchSheetResume;
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
            ", matchSheetName='" + getMatchSheetName() + "'" +
            ", matchSheetResume='" + getMatchSheetResume() + "'" +
            ", isfirst='" + isIsfirst() + "'" +
            "}";
    }
}
