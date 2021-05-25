package com.atc.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "workout_places", schema = "shapp", catalog = "")
public class WorkoutPlaceEntity {
    private int id;
    private String name;
    private String range;
    private Collection<EventEntity> eventsById;
    private AddressEntity addressesByAddressId;
    private UserEntity usersByCreatorUserId;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "range", nullable = false)
    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutPlaceEntity that = (WorkoutPlaceEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(range, that.range);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, range);
    }

    @OneToMany(mappedBy = "workoutPlacesByWorkoutPlaceId")
    public Collection<EventEntity> getEventsById() {
        return eventsById;
    }

    public void setEventsById(Collection<EventEntity> eventsById) {
        this.eventsById = eventsById;
    }

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    public AddressEntity getAddressesByAddressId() {
        return addressesByAddressId;
    }

    public void setAddressesByAddressId(AddressEntity addressesByAddressId) {
        this.addressesByAddressId = addressesByAddressId;
    }

    @ManyToOne
    @JoinColumn(name = "creator_user_id", referencedColumnName = "id")
    public UserEntity getUsersByCreatorUserId() {
        return usersByCreatorUserId;
    }

    public void setUsersByCreatorUserId(UserEntity usersByCreatorUserId) {
        this.usersByCreatorUserId = usersByCreatorUserId;
    }
}
