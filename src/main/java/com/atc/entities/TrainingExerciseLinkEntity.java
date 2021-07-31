package com.atc.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author william
 * temporaire: fonctionnalités à redéfinir
 */
@Entity
@Table(name = "trainings_exercises_link", schema = "shapp", catalog = "")
public class TrainingExerciseLinkEntity {
    private int id;
    private int sequentialNumberExercise;
    private TrainingPlanEntity trainingPlansByTrainingPlanId;
    private ExerciseEntity exercisesByExerciseId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "sequential_number_exercise", nullable = false)
    public int getSequentialNumberExercise() {
        return sequentialNumberExercise;
    }

    public void setSequentialNumberExercise(int sequentialNumberExercise) {
        this.sequentialNumberExercise = sequentialNumberExercise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingExerciseLinkEntity that = (TrainingExerciseLinkEntity) o;
        return id == that.id && sequentialNumberExercise == that.sequentialNumberExercise;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sequentialNumberExercise);
    }

    @ManyToOne
    @JoinColumn(name = "training_plan_id", referencedColumnName = "id", nullable = false)
    public TrainingPlanEntity getTrainingPlansByTrainingPlanId() {
        return trainingPlansByTrainingPlanId;
    }

    public void setTrainingPlansByTrainingPlanId(TrainingPlanEntity trainingPlansByTrainingPlanId) {
        this.trainingPlansByTrainingPlanId = trainingPlansByTrainingPlanId;
    }

    @ManyToOne
    @JoinColumn(name = "exercise_id", referencedColumnName = "id", nullable = false)
    public ExerciseEntity getExercisesByExerciseId() {
        return exercisesByExerciseId;
    }

    public void setExercisesByExerciseId(ExerciseEntity exercisesByExerciseId) {
        this.exercisesByExerciseId = exercisesByExerciseId;
    }
}
