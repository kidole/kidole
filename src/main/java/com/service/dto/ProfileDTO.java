package com.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.domain.enumeration.Gender;
import com.domain.enumeration.AccreditationList;

/**
 * A DTO for the {@link com.domain.Profile} entity.
 */
@ApiModel(description = "The Profil entity.\n@author joel jorle")
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
    @ApiModelProperty(value = "photo")
    @Lob
    private byte[] photo;

    private String photoContentType;
    /**
     * isAccreditated
     */
    @ApiModelProperty(value = "isAccreditated")
    private Boolean isAccreditated;

    /**
     * type
     */
    @ApiModelProperty(value = "type")
    private AccreditationList accreditationtype;

    /**
     * date_of_birth
     */
    @ApiModelProperty(value = "date_of_birth")
    private Instant dateOfBirth;

    /**
     * place_of_birth
     */
    @ApiModelProperty(value = "place_of_birth")
    private String placeOfBbirth;

    /**
     * club_origin
     */
    @ApiModelProperty(value = "club_origin")
    private String clubOrigin;

    /**
     * nationality
     */
    @ApiModelProperty(value = "nationality")
    private String nationality;

    /**
     * height
     */
    @ApiModelProperty(value = "height")
    private Integer height;

    /**
     * weight
     */
    @ApiModelProperty(value = "weight")
    private Integer weight;

    /**
     * manuality
     */
    @ApiModelProperty(value = "manuality")
    private String manuality;

    /**
     * nic
     */
    @ApiModelProperty(value = "nic")
    private String nic;

    /**
     * phone
     */
    @ApiModelProperty(value = "phone")
    private String phone;

    /**
     * discipline
     */
    @ApiModelProperty(value = "discipline")
    private String discipline;

    /**
     * categorie
     */
    @ApiModelProperty(value = "categorie")
    private String categorie;

    /**
     * teamName
     */
    @ApiModelProperty(value = "teamName")
    private String teamName;

    /**
     * function
     */
    @ApiModelProperty(value = "function")
    private String fonction;

    /**
     * title
     */
    @ApiModelProperty(value = "title")
    private String titre;

    /**
     * residentCity
     */
    @ApiModelProperty(value = "residentCity")
    private String residentCity;

    /**
     * pressID
     */
    @ApiModelProperty(value = "pressID")
    private String pressID;

    /**
     * pressAgence
     */
    @ApiModelProperty(value = "pressAgence")
    private String pressAgence;

    /**
     * bataillon_rattachement
     */
    @ApiModelProperty(value = "bataillon_rattachement")
    private String bataillonRattachement;

    /**
     * social_denomination
     */
    @ApiModelProperty(value = "social_denomination")
    private String socialDenomination;

    /**
     * location
     */
    @ApiModelProperty(value = "location")
    private String locationBuilding;


    private Long userId;

    private Long userAccreditationId;

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

    public Boolean isIsAccreditated() {
        return isAccreditated;
    }

    public void setIsAccreditated(Boolean isAccreditated) {
        this.isAccreditated = isAccreditated;
    }

    public AccreditationList getAccreditationtype() {
        return accreditationtype;
    }

    public void setAccreditationtype(AccreditationList accreditationtype) {
        this.accreditationtype = accreditationtype;
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

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
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

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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

    public Long getUserAccreditationId() {
        return userAccreditationId;
    }

    public void setUserAccreditationId(Long accreditationId) {
        this.userAccreditationId = accreditationId;
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
            ", isAccreditated='" + isIsAccreditated() + "'" +
            ", accreditationtype='" + getAccreditationtype() + "'" +
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
            ", fonction='" + getFonction() + "'" +
            ", titre='" + getTitre() + "'" +
            ", residentCity='" + getResidentCity() + "'" +
            ", pressID='" + getPressID() + "'" +
            ", pressAgence='" + getPressAgence() + "'" +
            ", bataillonRattachement='" + getBataillonRattachement() + "'" +
            ", socialDenomination='" + getSocialDenomination() + "'" +
            ", locationBuilding='" + getLocationBuilding() + "'" +
            ", userId=" + getUserId() +
            ", userAccreditationId=" + getUserAccreditationId() +
            "}";
    }
}
