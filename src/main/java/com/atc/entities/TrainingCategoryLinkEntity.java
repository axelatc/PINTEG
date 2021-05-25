package com.atc.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "trainings_categories_link", schema = "shapp", catalog = "")
public class TrainingCategoryLinkEntity {
    private int id;
    private TrainingPlanEntity trainingPlansByTrainingPlanId;
    private CategoryEntity categoriesByCategoryId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingCategoryLinkEntity that = (TrainingCategoryLinkEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    public CategoryEntity getCategoriesByCategoryId() {
        return categoriesByCategoryId;
    }

    public void setCategoriesByCategoryId(CategoryEntity categoriesByCategoryId) {
        this.categoriesByCategoryId = categoriesByCategoryId;
    }
}
