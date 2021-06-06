package com.atc.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "events", schema = "shapp", catalog = "")
public class EventEntity {
    private int id;
    private LocalDateTime beginDateTime;
    private LocalDateTime endDateTime;
    private String status;
    private String range;
    private LocalDateTime creationDateTime;
    private String description;
    private String praticalInformation;
    private String name;
    private Collection<EventParticipationEntity> eventParticipationsById;
    private Collection<EventPostEntity> eventPostsById;
    private OrganizationEntity organizationsByOrganizationId;
    private WorkoutPlaceEntity workoutPlacesByWorkoutPlaceId;
    private UserEntity usersByCreatorUserId;

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
    @Column(name = "begin_date_time", nullable = false)
    public LocalDateTime getBeginDateTime() {
        return beginDateTime;
    }

    public void setBeginDateTime(LocalDateTime beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    @Basic
    @Column(name = "end_date_time", nullable = false)
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "range", nullable = false)
    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    @Basic
    @Column(name = "creation_date_time", nullable = false)
    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "pratical_information", nullable = true, length = -1)
    public String getPraticalInformation() {
        return praticalInformation;
    }

    public void setPraticalInformation(String praticalInformation) {
        this.praticalInformation = praticalInformation;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventEntity that = (EventEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(beginDateTime, that.beginDateTime) && Objects.equals(endDateTime, that.endDateTime) && Objects.equals(status, that.status) && Objects.equals(range, that.range) && Objects.equals(creationDateTime, that.creationDateTime) && Objects.equals(description, that.description) && Objects.equals(praticalInformation, that.praticalInformation) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, beginDateTime, endDateTime, status, range, creationDateTime, description, praticalInformation, name);
    }

    @OneToMany(mappedBy = "eventsByEventId")
    public Collection<EventParticipationEntity> getEventParticipationsById() {
        return eventParticipationsById;
    }

    public void setEventParticipationsById(Collection<EventParticipationEntity> eventParticipationsById) {
        this.eventParticipationsById = eventParticipationsById;
    }

    @OneToMany(mappedBy = "eventsByEventId")
    public Collection<EventPostEntity> getEventPostsById() {
        return eventPostsById;
    }

    public void setEventPostsById(Collection<EventPostEntity> eventPostsById) {
        this.eventPostsById = eventPostsById;
    }

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    public OrganizationEntity getOrganizationsByOrganizationId() {
        return organizationsByOrganizationId;
    }

    public void setOrganizationsByOrganizationId(OrganizationEntity organizationsByOrganizationId) {
        this.organizationsByOrganizationId = organizationsByOrganizationId;
    }

    @ManyToOne
    @JoinColumn(name = "workout_place_id", referencedColumnName = "id")
    public WorkoutPlaceEntity getWorkoutPlacesByWorkoutPlaceId() {
        return workoutPlacesByWorkoutPlaceId;
    }

    public void setWorkoutPlacesByWorkoutPlaceId(WorkoutPlaceEntity workoutPlacesByWorkoutPlaceId) {
        this.workoutPlacesByWorkoutPlaceId = workoutPlacesByWorkoutPlaceId;
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
