package com.atc.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "exercises", schema = "shapp", catalog = "")
public class ExerciseEntity {
    private int id;
    private String name;
    private String description;
    private LevelEntity levelsByLevelId;
    private Collection<ExerciseMuscleLinkEntity> exercisesMusclesLinksById;
    private Collection<RequirementLinkEntity> requirementsLinksById;
    private Collection<TrainingExerciseLinkEntity> trainingsExercisesLinksById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseEntity that = (ExerciseEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @ManyToOne
    @JoinColumn(name = "level_id", referencedColumnName = "id")
    public LevelEntity getLevelsByLevelId() {
        return levelsByLevelId;
    }

    public void setLevelsByLevelId(LevelEntity levelsByLevelId) {
        this.levelsByLevelId = levelsByLevelId;
    }

    @OneToMany(mappedBy = "exercisesByExerciseId")
    public Collection<ExerciseMuscleLinkEntity> getExercisesMusclesLinksById() {
        return exercisesMusclesLinksById;
    }

    public void setExercisesMusclesLinksById(Collection<ExerciseMuscleLinkEntity> exercisesMusclesLinksById) {
        this.exercisesMusclesLinksById = exercisesMusclesLinksById;
    }

    @OneToMany(mappedBy = "exercisesByExerciseId")
    public Collection<RequirementLinkEntity> getRequirementsLinksById() {
        return requirementsLinksById;
    }

    public void setRequirementsLinksById(Collection<RequirementLinkEntity> requirementsLinksById) {
        this.requirementsLinksById = requirementsLinksById;
    }

    @OneToMany(mappedBy = "exercisesByExerciseId")
    public Collection<TrainingExerciseLinkEntity> getTrainingsExercisesLinksById() {
        return trainingsExercisesLinksById;
    }

    public void setTrainingsExercisesLinksById(Collection<TrainingExerciseLinkEntity> trainingsExercisesLinksById) {
        this.trainingsExercisesLinksById = trainingsExercisesLinksById;
    }
}
