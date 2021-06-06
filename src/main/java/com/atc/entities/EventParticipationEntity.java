package com.atc.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "event_participations", schema = "shapp", catalog = "")
public class EventParticipationEntity {
    private int id;
    private String status;
    private LocalDateTime creationDateTime;
    private UserEntity usersByInviterUserId;
    private UserEntity usersByInviteeUserId;
    private EventEntity eventsByEventId;

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
    @Column(name = "status", nullable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "creation_date_time", nullable = false)
    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventParticipationEntity that = (EventParticipationEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(status, that.status) && Objects.equals(creationDateTime, that.creationDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, creationDateTime);
    }

    @ManyToOne
    @JoinColumn(name = "inviter_user_id", referencedColumnName = "id")
    public UserEntity getUsersByInviterUserId() {
        return usersByInviterUserId;
    }

    public void setUsersByInviterUserId(UserEntity usersByInviterUserId) {
        this.usersByInviterUserId = usersByInviterUserId;
    }

    @ManyToOne
    @JoinColumn(name = "invitee_user_id", referencedColumnName = "id")
    public UserEntity getUsersByInviteeUserId() {
        return usersByInviteeUserId;
    }

    public void setUsersByInviteeUserId(UserEntity usersByInviteeUserId) {
        this.usersByInviteeUserId = usersByInviteeUserId;
    }

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    public EventEntity getEventsByEventId() {
        return eventsByEventId;
    }

    public void setEventsByEventId(EventEntity eventsByEventId) {
        this.eventsByEventId = eventsByEventId;
    }
}
