package com.kidole.sport.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

import com.kidole.sport.domain.enumeration.Gender;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    /**
     * Gender
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    /**
     * photo
     */
    
    @Lob
    @Column(name = "photo", nullable = false)
    private byte[] photo;

    @Column(name = "photo_content_type", nullable = false)
    private String photoContentType;

    /**
     * date_of_birth
     */
    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private Instant dateOfBirth;

    /**
     * place_of_birth
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "place_of_bbirth", length = 1024, nullable = false)
    private String placeOfBbirth;

    /**
     * club_origin
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "club_origin", length = 1024, nullable = false)
    private String clubOrigin;

    /**
     * nationality
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "nationality", length = 1024, nullable = false)
    private String nationality;

    /**
     * height
     */
    @NotNull
    @DecimalMin(value = "1")
    @DecimalMax(value = "4")
    @Column(name = "height", nullable = false)
    private Float height;

    /**
     * weight
     */
    @NotNull
    @DecimalMin(value = "5")
    @DecimalMax(value = "500")
    @Column(name = "weight", nullable = false)
    private Float weight;

    /**
     * manuality
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "manuality", length = 1024, nullable = false)
    private String manuality;

    /**
     * nic
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "nic", length = 1024, nullable = false)
    private String nic;

    /**
     * phone
     */
    @NotNull
    @Size(min = 8, max = 1024)
    @Column(name = "phone", length = 1024, nullable = false)
    private String phone;

    /**
     * discipline
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "discipline", length = 1024, nullable = false)
    private String discipline;

    /**
     * categorie
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "categorie", length = 1024, nullable = false)
    private String categorie;

    /**
     * teamName
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "team_name", length = 1024, nullable = false)
    private String teamName;

    /**
     * function
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "function_on", length = 1024, nullable = false)
    private String functionOn;

    /**
     * title
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "title_as", length = 1024, nullable = false)
    private String titleAs;

    /**
     * residentCity
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "resident_city", length = 1024, nullable = false)
    private String residentCity;

    /**
     * pressID
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "press_id", length = 1024, nullable = false)
    private String pressID;

    /**
     * pressAgence
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "press_agence", length = 1024, nullable = false)
    private String pressAgence;

    /**
     * bataillon_rattachement
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "bataillon_rattachement", length = 1024, nullable = false)
    private String bataillonRattachement;

    /**
     * social_denomination
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "social_denomination", length = 1024, nullable = false)
    private String socialDenomination;

    /**
     * location
     */
    @NotNull
    @Size(min = 3, max = 1024)
    @Column(name = "location_building", length = 1024, nullable = false)
    private String locationBuilding;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Accreditation accreditation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public Profile gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Profile photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Profile photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public Profile dateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBbirth() {
        return placeOfBbirth;
    }

    public Profile placeOfBbirth(String placeOfBbirth) {
        this.placeOfBbirth = placeOfBbirth;
        return this;
    }

    public void setPlaceOfBbirth(String placeOfBbirth) {
        this.placeOfBbirth = placeOfBbirth;
    }

    public String getClubOrigin() {
        return clubOrigin;
    }

    public Profile clubOrigin(String clubOrigin) {
        this.clubOrigin = clubOrigin;
        return this;
    }

    public void setClubOrigin(String clubOrigin) {
        this.clubOrigin = clubOrigin;
    }

    public String getNationality() {
        return nationality;
    }

    public Profile nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Float getHeight() {
        return height;
    }

    public Profile height(Float height) {
        this.height = height;
        return this;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public Profile weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getManuality() {
        return manuality;
    }

    public Profile manuality(String manuality) {
        this.manuality = manuality;
        return this;
    }

    public void setManuality(String manuality) {
        this.manuality = manuality;
    }

    public String getNic() {
        return nic;
    }

    public Profile nic(String nic) {
        this.nic = nic;
        return this;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPhone() {
        return phone;
    }

    public Profile phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDiscipline() {
        return discipline;
    }

    public Profile discipline(String discipline) {
        this.discipline = discipline;
        return this;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getCategorie() {
        return categorie;
    }

    public Profile categorie(String categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getTeamName() {
        return teamName;
    }

    public Profile teamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getFunctionOn() {
        return functionOn;
    }

    public Profile functionOn(String functionOn) {
        this.functionOn = functionOn;
        return this;
    }

    public void setFunctionOn(String functionOn) {
        this.functionOn = functionOn;
    }

    public String getTitleAs() {
        return titleAs;
    }

    public Profile titleAs(String titleAs) {
        this.titleAs = titleAs;
        return this;
    }

    public void setTitleAs(String titleAs) {
        this.titleAs = titleAs;
    }

    public String getResidentCity() {
        return residentCity;
    }

    public Profile residentCity(String residentCity) {
        this.residentCity = residentCity;
        return this;
    }

    public void setResidentCity(String residentCity) {
        this.residentCity = residentCity;
    }

    public String getPressID() {
        return pressID;
    }

    public Profile pressID(String pressID) {
        this.pressID = pressID;
        return this;
    }

    public void setPressID(String pressID) {
        this.pressID = pressID;
    }

    public String getPressAgence() {
        return pressAgence;
    }

    public Profile pressAgence(String pressAgence) {
        this.pressAgence = pressAgence;
        return this;
    }

    public void setPressAgence(String pressAgence) {
        this.pressAgence = pressAgence;
    }

    public String getBataillonRattachement() {
        return bataillonRattachement;
    }

    public Profile bataillonRattachement(String bataillonRattachement) {
        this.bataillonRattachement = bataillonRattachement;
        return this;
    }

    public void setBataillonRattachement(String bataillonRattachement) {
        this.bataillonRattachement = bataillonRattachement;
    }

    public String getSocialDenomination() {
        return socialDenomination;
    }

    public Profile socialDenomination(String socialDenomination) {
        this.socialDenomination = socialDenomination;
        return this;
    }

    public void setSocialDenomination(String socialDenomination) {
        this.socialDenomination = socialDenomination;
    }

    public String getLocationBuilding() {
        return locationBuilding;
    }

    public Profile locationBuilding(String locationBuilding) {
        this.locationBuilding = locationBuilding;
        return this;
    }

    public void setLocationBuilding(String locationBuilding) {
        this.locationBuilding = locationBuilding;
    }

    public User getUser() {
        return user;
    }

    public Profile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Accreditation getAccreditation() {
        return accreditation;
    }

    public Profile accreditation(Accreditation accreditation) {
        this.accreditation = accreditation;
        return this;
    }

    public void setAccreditation(Accreditation accreditation) {
        this.accreditation = accreditation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", gender='" + getGender() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
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
            "}";
    }
}
