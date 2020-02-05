package com.kidole.sport.service.dto;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.kidole.sport.domain.enumeration.Gender;

/**
 * A DTO for the {@link com.kidole.sport.domain.Profile} entity.
 */
public class ProfileDTO implements Serializable {

    private Long id;

    /**
     * Gender
     */
    @NotNull
    @ApiModelProperty(value = "Gender", required = true)
    private Gender gender;

    /**
     * photo
     */
    
    @ApiModelProperty(value = "photo", required = true)
    @Lob
    private byte[] photo;

    private String photoContentType;
    /**
     * date_of_birth
     */
    @NotNull
    @ApiModelProperty(value = "date_of_birth", required = true)
    private Instant dateOfBirth;

    /**
     * place_of_birth
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "place_of_birth", required = true)
    private String placeOfBbirth;

    /**
     * club_origin
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "club_origin", required = true)
    private String clubOrigin;

    /**
     * nationality
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "nationality", required = true)
    private String nationality;

    /**
     * height
     */
    @NotNull
    @DecimalMin(value = "1")
    @DecimalMax(value = "4")
    @ApiModelProperty(value = "height", required = true)
    private Float height;

    /**
     * weight
     */
    @NotNull
    @DecimalMin(value = "5")
    @DecimalMax(value = "500")
    @ApiModelProperty(value = "weight", required = true)
    private Float weight;

    /**
     * manuality
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "manuality", required = true)
    private String manuality;

    /**
     * nic
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "nic", required = true)
    private String nic;

    /**
     * phone
     */
    @NotNull
    @Size(min = 8, max = 1024)
    @ApiModelProperty(value = "phone", required = true)
    private String phone;

    /**
     * discipline
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "discipline", required = true)
    private String discipline;

    /**
     * categorie
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "categorie", required = true)
    private String categorie;

    /**
     * teamName
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "teamName", required = true)
    private String teamName;

    /**
     * function
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "function", required = true)
    private String functionOn;

    /**
     * title
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "title", required = true)
    private String titleAs;

    /**
     * residentCity
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "residentCity", required = true)
    private String residentCity;

    /**
     * pressID
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "pressID", required = true)
    private String pressID;

    /**
     * pressAgence
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "pressAgence", required = true)
    private String pressAgence;

    /**
     * bataillon_rattachement
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "bataillon_rattachement", required = true)
    private String bataillonRattachement;

    /**
     * social_denomination
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "social_denomination", required = true)
    private String socialDenomination;

    /**
     * location
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @ApiModelProperty(value = "location", required = true)
    private String locationBuilding;


    private Long userId;

    private String userFirstName;

    private Long accreditationId;

    private String accreditationAccreditationName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBbirth() {
        return placeOfBbirth;
    }

    public void setPlaceOfBbirth(String placeOfBbirth) {
        this.placeOfBbirth = placeOfBbirth;
    }

    public String getClubOrigin() {
        return clubOrigin;
    }

    public void setClubOrigin(String clubOrigin) {
        this.clubOrigin = clubOrigin;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getManuality() {
        return manuality;
    }

    public void setManuality(String manuality) {
        this.manuality = manuality;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getFunctionOn() {
        return functionOn;
    }

    public void setFunctionOn(String functionOn) {
        this.functionOn = functionOn;
    }

    public String getTitleAs() {
        return titleAs;
    }

    public void setTitleAs(String titleAs) {
        this.titleAs = titleAs;
    }

    public String getResidentCity() {
        return residentCity;
    }

    public void setResidentCity(String residentCity) {
        this.residentCity = residentCity;
    }

    public String getPressID() {
        return pressID;
    }

    public void setPressID(String pressID) {
        this.pressID = pressID;
    }

    public String getPressAgence() {
        return pressAgence;
    }

    public void setPressAgence(String pressAgence) {
        this.pressAgence = pressAgence;
    }

    public String getBataillonRattachement() {
        return bataillonRattachement;
    }

    public void setBataillonRattachement(String bataillonRattachement) {
        this.bataillonRattachement = bataillonRattachement;
    }

    public String getSocialDenomination() {
        return socialDenomination;
    }

    public void setSocialDenomination(String socialDenomination) {
        this.socialDenomination = socialDenomination;
    }

    public String getLocationBuilding() {
        return locationBuilding;
    }

    public void setLocationBuilding(String locationBuilding) {
        this.locationBuilding = locationBuilding;
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

    public Long getAccreditationId() {
        return accreditationId;
    }

    public void setAccreditationId(Long accreditationId) {
        this.accreditationId = accreditationId;
    }

    public String getAccreditationAccreditationName() {
        return accreditationAccreditationName;
    }

    public void setAccreditationAccreditationName(String accreditationAccreditationName) {
        this.accreditationAccreditationName = accreditationAccreditationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfileDTO profileDTO = (ProfileDTO) o;
        if (profileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
            "id=" + getId() +
            ", gender='" + getGender() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", placeOfBbirth='" + getPlaceOfBbirth() + "'" +
            ", clubOrigin='" + getClubOrigin() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            ", manuality='" + getManuality() + "'" +
            ", nic='" + getNic() + "'" +
            ", phone='" + getPhone() + "'" +
            ", discipline='" + getDiscipline() + "'" +
            ", categorie='" + getCategorie() + "'" +
            ", teamName='" + getTeamName() + "'" +
            ", functionOn='" + getFunctionOn() + "'" +
            ", titleAs='" + getTitleAs() + "'" +
            ", residentCity='" + getResidentCity() + "'" +
            ", pressID='" + getPressID() + "'" +
            ", pressAgence='" + getPressAgence() + "'" +
            ", bataillonRattachement='" + getBataillonRattachement() + "'" +
            ", socialDenomination='" + getSocialDenomination() + "'" +
            ", locationBuilding='" + getLocationBuilding() + "'" +
            ", userId=" + getUserId() +
            ", userFirstName='" + getUserFirstName() + "'" +
            ", accreditationId=" + getAccreditationId() +
            ", accreditationAccreditationName='" + getAccreditationAccreditationName() + "'" +
            "}";
    }
}
