package com.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Options.
 */
@Entity
@Table(name = "options")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Options implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value_1")
    private Boolean value1;

    @Column(name = "value_2")
    private Integer value2;

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

    public String getName() {
        return name;
    }

    public Options name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isValue1() {
        return value1;
    }

    public Options value1(Boolean value1) {
        this.value1 = value1;
        return this;
    }

    public void setValue1(Boolean value1) {
        this.value1 = value1;
    }

    public Integer getValue2() {
        return value2;
    }

    public Options value2(Integer value2) {
        this.value2 = value2;
        return this;
    }

    public void setValue2(Integer value2) {
        this.value2 = value2;
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
            ", name='" + getName() + "'" +
            ", value1='" + isValue1() + "'" +
            ", value2=" + getValue2() +
            "}";
    }
}
