package com.kidole.sport.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.kidole.sport.domain.Competition} entity.
 */
public class CompetitionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String competitionName;

    @NotNull
    private Instant start;

    @NotNull
    private Instant end;

    @NotNull
    private Instant dateLimit;

    
    @Lob
    private String detail;

    
    @Lob
    private byte[] rule;

    private String ruleContentType;

    private Long userId;

    private String userFirstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Instant getDateLimit() {
        return dateLimit;
    }

    public void setDateLimit(Instant dateLimit) {
        this.dateLimit = dateLimit;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public byte[] getRule() {
        return rule;
    }

    public void setRule(byte[] rule) {
        this.rule = rule;
    }

    public String getRuleContentType() {
        return ruleContentType;
    }

    public void setRuleContentType(String ruleContentType) {
        this.ruleContentType = ruleContentType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompetitionDTO competitionDTO = (CompetitionDTO) o;
        if (competitionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competitionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompetitionDTO{" +
            "id=" + getId() +
            ", competitionName='" + getCompetitionName() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", dateLimit='" + getDateLimit() + "'" +
            ", detail='" + getDetail() + "'" +
            ", rule='" + getRule() + "'" +
            ", userId=" + getUserId() +
            ", userFirstName='" + getUserFirstName() + "'" +
            "}";
    }
}
