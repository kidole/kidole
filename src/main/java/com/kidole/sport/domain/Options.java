package com.kidole.sport.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Options.
 */
@Entity
@Table(name = "options")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "options")
public class Options implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "options_name", length = 1024, nullable = false)
    private String optionsName;

    @NotNull
    @Column(name = "options_value_1", nullable = false)
    private Boolean optionsValue1;

    @NotNull
    @Min(value = 0)
    @Column(name = "options_value_2", nullable = false)
    private Integer optionsValue2;

    @ManyToOne
    @JsonIgnoreProperties("options")
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionsName() {
        return optionsName;
    }

    public Options optionsName(String optionsName) {
        this.optionsName = optionsName;
        return this;
    }

    public void setOptionsName(String optionsName) {
        this.optionsName = optionsName;
    }

    public Boolean isOptionsValue1() {
        return optionsValue1;
    }

    public Options optionsValue1(Boolean optionsValue1) {
        this.optionsValue1 = optionsValue1;
        return this;
    }

    public void setOptionsValue1(Boolean optionsValue1) {
        this.optionsValue1 = optionsValue1;
    }

    public Integer getOptionsValue2() {
        return optionsValue2;
    }

    public Options optionsValue2(Integer optionsValue2) {
        this.optionsValue2 = optionsValue2;
        return this;
    }

    public void setOptionsValue2(Integer optionsValue2) {
        this.optionsValue2 = optionsValue2;
    }

    public Competition getCompetition() {
        return competition;
    }

    public Options competition(Competition competition) {
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
        if (!(o instanceof Options)) {
            return false;
        }
        return id != null && id.equals(((Options) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Options{" +
            "id=" + getId() +
            ", optionsName='" + getOptionsName() + "'" +
            ", optionsValue1='" + isOptionsValue1() + "'" +
            ", optionsValue2=" + getOptionsValue2() +
            "}";
    }
}
