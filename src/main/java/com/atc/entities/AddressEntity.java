package com.atc.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "addresses", schema = "shapp", catalog = "")
public class AddressEntity {
    private int id;
    private String streetNameFr;
    private String streetNameEn;
    private String streetNumber;
    private String streetNumberBox;
    private CityEntity citiesByCityId;
    private Collection<OrganizationEntity> organizationsById;
    private Collection<UserAddressEntity> usersAddressesById;
    private Collection<WorkoutPlaceEntity> workoutPlacesById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Basic
    @Column(name = "street_name_fr", nullable = false, length = 100)
    public String getStreetNameFr() {
        return streetNameFr;
    }

    public void setStreetNameFr(String streetNameFr) {
        this.streetNameFr = streetNameFr;
    }

    @Basic
    @Column(name = "street_name_en", nullable = false, length = 100)
    public String getStreetNameEn() {
        return streetNameEn;
    }

    public void setStreetNameEn(String streetNameEn) {
        this.streetNameEn = streetNameEn;
    }

    @Basic
    @Column(name = "street_number", nullable = false, length = 10)
    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    @Basic
    @Column(name = "street_number_box", nullable = false, length = 10)
    public String getStreetNumberBox() {
        return streetNumberBox;
    }

    public void setStreetNumberBox(String streetNumberBox) {
        this.streetNumberBox = streetNumberBox;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(streetNameFr, that.streetNameFr) && Objects.equals(streetNameEn, that.streetNameEn) && Objects.equals(streetNumber, that.streetNumber) && Objects.equals(streetNumberBox, that.streetNumberBox);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, streetNameFr, streetNameEn, streetNumber, streetNumberBox);
    }

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    public CityEntity getCitiesByCityId() {
        return citiesByCityId;
    }

    public void setCitiesByCityId(CityEntity citiesByCityId) {
        this.citiesByCityId = citiesByCityId;
    }

    @OneToMany(mappedBy = "addressesByAddressId")
    public Collection<OrganizationEntity> getOrganizationsById() {
        return organizationsById;
    }

    public void setOrganizationsById(Collection<OrganizationEntity> organizationsById) {
        this.organizationsById = organizationsById;
    }

    @OneToMany(mappedBy = "addressesByAddressId")
    public Collection<UserAddressEntity> getUsersAddressesById() {
        return usersAddressesById;
    }

    public void setUsersAddressesById(Collection<UserAddressEntity> usersAddressesById) {
        this.usersAddressesById = usersAddressesById;
    }

    @OneToMany(mappedBy = "addressesByAddressId")
    public Collection<WorkoutPlaceEntity> getWorkoutPlacesById() {
        return workoutPlacesById;
    }

    public void setWorkoutPlacesById(Collection<WorkoutPlaceEntity> workoutPlacesById) {
        this.workoutPlacesById = workoutPlacesById;
    }
}
