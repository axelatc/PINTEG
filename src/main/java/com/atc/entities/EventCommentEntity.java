package com.atc.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "event_comments", schema = "shapp", catalog = "")
public class EventCommentEntity {
    private int id;
    private String content;
    private LocalDateTime creationDateTime;
    private EventPostEntity eventPostsByEventPostId;
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
    @Column(name = "content", nullable = false, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        EventCommentEntity that = (EventCommentEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(content, that.content) && Objects.equals(creationDateTime, that.creationDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, creationDateTime);
    }

    @ManyToOne
    @JoinColumn(name = "event_post_id", referencedColumnName = "id")
    public EventPostEntity getEventPostsByEventPostId() {
        return eventPostsByEventPostId;
    }

    public void setEventPostsByEventPostId(EventPostEntity eventPostsByEventPostId) {
        this.eventPostsByEventPostId = eventPostsByEventPostId;
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
