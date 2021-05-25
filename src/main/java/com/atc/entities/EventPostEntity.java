package com.atc.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "event_posts", schema = "shapp", catalog = "")
public class EventPostEntity {
    private int id;
    private String name;
    private LocalDateTime creationDateTime;
    private Collection<EventCommentEntity> eventCommentsById;
    private EventEntity eventsByEventId;
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
        EventPostEntity that = (EventPostEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(creationDateTime, that.creationDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDateTime);
    }

    @OneToMany(mappedBy = "eventPostsByEventPostId")
    public Collection<EventCommentEntity> getEventCommentsById() {
        return eventCommentsById;
    }

    public void setEventCommentsById(Collection<EventCommentEntity> eventCommentsById) {
        this.eventCommentsById = eventCommentsById;
    }

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    public EventEntity getEventsByEventId() {
        return eventsByEventId;
    }

    public void setEventsByEventId(EventEntity eventsByEventId) {
        this.eventsByEventId = eventsByEventId;
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
