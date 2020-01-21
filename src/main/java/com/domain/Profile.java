package com.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.domain.enumeration.Gender;

import com.domain.enumeration.AccreditationList;

/**
 * The Profil entity.\n@author joel jorle
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    /**
     * isAccreditated
     */
    @Column(name = "is_accreditated")
    private Boolean isAccreditated;

    /**
     * type
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "accreditationtype")
    private AccreditationList accreditationtype;

    /**
     * date_of_birth
     */
    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    /**
     * place_of_birth
     */
    @Column(name = "place_of_bbirth")
    private String placeOfBbirth;

    /**
     * club_origin
     */
    @Column(name = "club_origin")
    private String clubOrigin;

    /**
     * nationality
     */
    @Column(name = "nationality")
    private String nationality;

    /**
     * height
     */
    @Column(name = "height")
    private Integer height;

    /**
     * weight
     */
    @Column(name = "weight")
    private Integer weight;

    /**
     * manuality
     */
    @Column(name = "manuality")
    private String manuality;

    /**
     * nic
     */
    @Column(name = "nic")
    private String nic;

    /**
     * phone
     */
    @Column(name = "phone")
    private String phone;

    /**
     * discipline
     */
    @Column(name = "discipline")
    private String discipline;

    /**
     * categorie
     */
    @Column(name = "categorie")
    private String categorie;

    /**
     * teamName
     */
    @Column(name = "team_name")
    private String teamName;

    /**
     * function
     */
    @Column(name = "fonction")
    private String fonction;

    /**
     * title
     */
    @Column(name = "titre")
    private String titre;

    /**
     * residentCity
     */
    @Column(name = "resident_city")
    private String residentCity;

    /**
     * pressID
     */
    @Column(name = "press_id")
    private String pressID;

    /**
     * pressAgence
     */
    @Column(name = "press_agence")
    private String pressAgence;

    /**
     * bataillon_rattachement
     */
    @Column(name = "bataillon_rattachement")
    private String bataillonRattachement;

    /**
     * social_denomination
     */
    @Column(name = "social_denomination")
    private String socialDenomination;

    /**
     * location
     */
    @Column(name = "location_building")
    private String locationBuilding;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Accreditation userAccreditation;

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

    public Boolean isIsAccreditated() {
        return isAccreditated;
    }

    public Profile isAccreditated(Boolean isAccreditated) {
        this.isAccreditated = isAccreditated;
        return this;
    }

    public void setIsAccreditated(Boolean isAccreditated) {
        this.isAccreditated = isAccreditated;
    }

    public AccreditationList getAccreditationtype() {
        return accreditationtype;
    }

    public Profile accreditationtype(AccreditationList accreditationtype) {
        this.accreditationtype = accreditationtype;
        return this;
    }

    public void setAccreditationtype(AccreditationList accreditationtype) {
        this.accreditationtype = accreditationtype;
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

    public Integer getHeight() {
        return height;
    }

    public Profile height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public Profile weight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Integer weight) {
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

    public String getFonction() {
        return fonction;
    }

    public Profile fonction(String fonction) {
        this.fonction = fonction;
        return this;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getTitre() {
        return titre;
    }

    public Profile titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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

    public Accreditation getUserAccreditation() {
        return userAccreditation;
    }

    public Profile userAccreditation(Accreditation accreditation) {
        this.userAccreditation = accreditation;
        return this;
    }

    public void setUserAccreditation(Accreditation accreditation) {
        this.userAccreditation = accreditation;
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
            "}";
    }
}
