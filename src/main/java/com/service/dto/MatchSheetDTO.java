package com.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.domain.MatchSheet} entity.
 */
public class MatchSheetDTO implements Serializable {

    private Long id;

    private String name;

    private String resume;

    private Boolean isfirst;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
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
            ", name='" + getName() + "'" +
            ", resume='" + getResume() + "'" +
            ", isfirst='" + isIsfirst() + "'" +
            "}";
    }
}
