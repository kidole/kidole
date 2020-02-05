package com.kidole.sport.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.kidole.sport.domain.MatchSheet} entity.
 */
public class MatchSheetDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 1024)
    private String matchSheetName;

    
    @Lob
    private String matchSheetResume;

    private Boolean isfirst;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatchSheetName() {
        return matchSheetName;
    }

    public void setMatchSheetName(String matchSheetName) {
        this.matchSheetName = matchSheetName;
    }

    public String getMatchSheetResume() {
        return matchSheetResume;
    }

    public void setMatchSheetResume(String matchSheetResume) {
        this.matchSheetResume = matchSheetResume;
    }

    public Boolean isIsfirst() {
        return isfirst;
    }

    public void setIsfirst(Boolean isfirst) {
        this.isfirst = isfirst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MatchSheetDTO matchSheetDTO = (MatchSheetDTO) o;
        if (matchSheetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matchSheetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MatchSheetDTO{" +
            "id=" + getId() +
            ", matchSheetName='" + getMatchSheetName() + "'" +
            ", matchSheetResume='" + getMatchSheetResume() + "'" +
            ", isfirst='" + isIsfirst() + "'" +
            "}";
    }
}
