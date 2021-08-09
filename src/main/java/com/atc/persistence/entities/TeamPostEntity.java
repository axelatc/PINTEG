package com.atc.persistence.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author axel
 * Pour EII uniquement
 */
@Entity
@Table(name = "team_posts", schema = "shapp", catalog = "")
public class TeamPostEntity {
    private int id;
    private LocalDateTime creationDateTime;
    private String message;
    private TeamEntity teamsByTeamId;
    private UserEntity usersByUserId;

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
    @Column(name = "creation_date_time", nullable = false)
    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @Basic
    @Column(name = "message", nullable = false, length = -1)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamPostEntity that = (TeamPostEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(creationDateTime, that.creationDateTime) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDateTime, message);
    }

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    public TeamEntity getTeamsByTeamId() {
        return teamsByTeamId;
    }

    public void setTeamsByTeamId(TeamEntity teamsByTeamId) {
        this.teamsByTeamId = teamsByTeamId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UserEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UserEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
}
