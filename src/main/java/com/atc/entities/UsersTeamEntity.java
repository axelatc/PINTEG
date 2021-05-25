package com.atc.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users_teams", schema = "shapp", catalog = "")
public class UsersTeamEntity {
    private int id;
    private LocalDateTime joinDateTime;
    private LocalDateTime leaveDateTime;
    private UserEntity usersByUserId;
    private TeamEntity teamsByTeamId;

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
    @Column(name = "join_date_time", nullable = false)
    public LocalDateTime getJoinDateTime() {
        return joinDateTime;
    }

    public void setJoinDateTime(LocalDateTime joinDateTime) {
        this.joinDateTime = joinDateTime;
    }

    @Basic
    @Column(name = "leave_date_time", nullable = true)
    public LocalDateTime getLeaveDateTime() {
        return leaveDateTime;
    }

    public void setLeaveDateTime(LocalDateTime leaveDateTime) {
        this.leaveDateTime = leaveDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersTeamEntity that = (UsersTeamEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(joinDateTime, that.joinDateTime) && Objects.equals(leaveDateTime, that.leaveDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, joinDateTime, leaveDateTime);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UserEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UserEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    public TeamEntity getTeamsByTeamId() {
        return teamsByTeamId;
    }

    public void setTeamsByTeamId(TeamEntity teamsByTeamId) {
        this.teamsByTeamId = teamsByTeamId;
    }
}
