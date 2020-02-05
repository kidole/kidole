package com.kidole.sport.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * The Category entity.\n@author joel jorle
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "category_name", length = 1024, nullable = false)
    private String categoryName;

    @NotNull
    @Min(value = 0)
    @Column(name = "yearlimit", nullable = false)
    private Integer yearlimit;

    @NotNull
    @Min(value = 0)
    @Column(name = "team_limit_numb", nullable = false)
    private Integer teamLimitNumb;

    @NotNull
    @Min(value = 0)
    @Column(name = "participant_limit_byteam", nullable = false)
    private Integer participantLimitByteam;

    
    @Lob
    @Column(name = "category_rule", nullable = false)
    private String categoryRule;

    @ManyToOne
    @JsonIgnoreProperties("categories")
    private Discipline discipline;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Category categoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getYearlimit() {
        return yearlimit;
    }

    public Category yearlimit(Integer yearlimit) {
        this.yearlimit = yearlimit;
        return this;
    }

    public void setYearlimit(Integer yearlimit) {
        this.yearlimit = yearlimit;
    }

    public Integer getTeamLimitNumb() {
        return teamLimitNumb;
    }

    public Category teamLimitNumb(Integer teamLimitNumb) {
        this.teamLimitNumb = teamLimitNumb;
        return this;
    }

    public void setTeamLimitNumb(Integer teamLimitNumb) {
        this.teamLimitNumb = teamLimitNumb;
    }

    public Integer getParticipantLimitByteam() {
        return participantLimitByteam;
    }

    public Category participantLimitByteam(Integer participantLimitByteam) {
        this.participantLimitByteam = participantLimitByteam;
        return this;
    }

    public void setParticipantLimitByteam(Integer participantLimitByteam) {
        this.participantLimitByteam = participantLimitByteam;
    }

    public String getCategoryRule() {
        return categoryRule;
    }

    public Category categoryRule(String categoryRule) {
        this.categoryRule = categoryRule;
        return this;
    }

    public void setCategoryRule(String categoryRule) {
        this.categoryRule = categoryRule;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public Category discipline(Discipline discipline) {
        this.discipline = discipline;
        return this;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", categoryName='" + getCategoryName() + "'" +
            ", yearlimit=" + getYearlimit() +
            ", teamLimitNumb=" + getTeamLimitNumb() +
            ", participantLimitByteam=" + getParticipantLimitByteam() +
            ", categoryRule='" + getCategoryRule() + "'" +
            "}";
    }
}
