package com.atc.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author william
 * temporaire: fonctionnalités à redéfinir
 */
@Entity
@Table(name = "exercises_muscles_link", schema = "shapp", catalog = "")
public class ExerciseMuscleLinkEntity {
    private int id;
    private String details;
    private ExerciseEntity exercisesByExerciseId;
    private MuscleEntity musclesByMuscleId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "details", nullable = true, length = -1)
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseMuscleLinkEntity that = (ExerciseMuscleLinkEntity) o;
        return id == that.id && Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, details);
    }

    @ManyToOne
    @JoinColumn(name = "exercise_id", referencedColumnName = "id", nullable = false)
    public ExerciseEntity getExercisesByExerciseId() {
        return exercisesByExerciseId;
    }

    public void setExercisesByExerciseId(ExerciseEntity exercisesByExerciseId) {
        this.exercisesByExerciseId = exercisesByExerciseId;
    }

    @ManyToOne
    @JoinColumn(name = "muscle_id", referencedColumnName = "id", nullable = false)
    public MuscleEntity getMusclesByMuscleId() {
        return musclesByMuscleId;
    }

    public void setMusclesByMuscleId(MuscleEntity musclesByMuscleId) {
        this.musclesByMuscleId = musclesByMuscleId;
    }
}
