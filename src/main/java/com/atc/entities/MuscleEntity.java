package com.atc.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * @author william
 * temporaire: fonctionnalités à redéfinir
 */
@Entity
@Table(name = "muscles", schema = "shapp", catalog = "")
public class MuscleEntity {
    private int id;
    private String name;
    private String description;
    private String pictureUri;
    private Collection<ExerciseMuscleLinkEntity> exercisesMusclesLinksById;

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

    @Basic
    @Column(name = "picture_URI", nullable = true, length = 2083)
    public String getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(String pictureUri) {
        this.pictureUri = pictureUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MuscleEntity that = (MuscleEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(pictureUri, that.pictureUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, pictureUri);
    }

    @OneToMany(mappedBy = "musclesByMuscleId")
    public Collection<ExerciseMuscleLinkEntity> getExercisesMusclesLinksById() {
        return exercisesMusclesLinksById;
    }

    public void setExercisesMusclesLinksById(Collection<ExerciseMuscleLinkEntity> exercisesMusclesLinksById) {
        this.exercisesMusclesLinksById = exercisesMusclesLinksById;
    }
}
