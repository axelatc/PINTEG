package com.atc.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "schedules_link", schema = "shapp", catalog = "")
public class ScheduleLinkEntity {
    private int id;
    private LocalDateTime executionDateTime;
    private UserEntity usersByUserId;
    private TrainingPlanEntity trainingPlansByTrainingPlanId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "execution_date_time", nullable = false)
    public LocalDateTime getExecutionDateTime() {
        return executionDateTime;
    }

    public void setExecutionDateTime(LocalDateTime executionDateTime) {
        this.executionDateTime = executionDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleLinkEntity that = (ScheduleLinkEntity) o;
        return id == that.id && Objects.equals(executionDateTime, that.executionDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, executionDateTime);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UserEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "training_plan_id", referencedColumnName = "id", nullable = false)
    public TrainingPlanEntity getTrainingPlansByTrainingPlanId() {
        return trainingPlansByTrainingPlanId;
    }

    public void setTrainingPlansByTrainingPlanId(TrainingPlanEntity trainingPlansByTrainingPlanId) {
        this.trainingPlansByTrainingPlanId = trainingPlansByTrainingPlanId;
    }
}
