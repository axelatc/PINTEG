package com.atc.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "requirements_link", schema = "shapp", catalog = "")
public class RequirementLinkEntity {
    private int id;
    private Integer quantity;
    private ExerciseEntity exercisesByExerciseId;
    private EquipmentGenericEntity equipmentGenericsByGenericId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "quantity", nullable = true)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequirementLinkEntity that = (RequirementLinkEntity) o;
        return id == that.id && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity);
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
    @JoinColumn(name = "generic_id", referencedColumnName = "id", nullable = false)
    public EquipmentGenericEntity getEquipmentGenericsByGenericId() {
        return equipmentGenericsByGenericId;
    }

    public void setEquipmentGenericsByGenericId(EquipmentGenericEntity equipmentGenericsByGenericId) {
        this.equipmentGenericsByGenericId = equipmentGenericsByGenericId;
    }
}
