package com.atc.persistence.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * @author axel
 * Pour EII uniquement
 */
@Entity
@Table(name = "teams", schema = "shapp", catalog = "")
public class TeamEntity {
    private int id;
    private String name;
    private String description;
    private LocalDateTime creationDateTime;
    private Collection<TeamCommentEntity> teamCommentsById;
    private Collection<TeamPostEntity> teamPostsById;
    private UserEntity usersByUserCreatorId;
    private Collection<UsersTeamEntity> usersTeamsById;

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
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = false, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        TeamEntity that = (TeamEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(creationDateTime, that.creationDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, creationDateTime);
    }

    @OneToMany(mappedBy = "teamsByTeamId")
    public Collection<TeamCommentEntity> getTeamCommentsById() {
        return teamCommentsById;
    }

    public void setTeamCommentsById(Collection<TeamCommentEntity> teamCommentsById) {
        this.teamCommentsById = teamCommentsById;
    }

    @OneToMany(mappedBy = "teamsByTeamId")
    public Collection<TeamPostEntity> getTeamPostsById() {
        return teamPostsById;
    }

    public void setTeamPostsById(Collection<TeamPostEntity> teamPostsById) {
        this.teamPostsById = teamPostsById;
    }

    @ManyToOne
    @JoinColumn(name = "user_creator_id", referencedColumnName = "id")
    public UserEntity getUsersByUserCreatorId() {
        return usersByUserCreatorId;
    }

    public void setUsersByUserCreatorId(UserEntity usersByUserCreatorId) {
        this.usersByUserCreatorId = usersByUserCreatorId;
    }

    @OneToMany(mappedBy = "teamsByTeamId")
    public Collection<UsersTeamEntity> getUsersTeamsById() {
        return usersTeamsById;
    }

    public void setUsersTeamsById(Collection<UsersTeamEntity> usersTeamsById) {
        this.usersTeamsById = usersTeamsById;
    }
}
