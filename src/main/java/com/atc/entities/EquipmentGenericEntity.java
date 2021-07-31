package com.atc.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * @author william
 * temporaire: fonctionnalités à redéfinir
 */
@Entity
@Table(name = "equipment_generics", schema = "shapp", catalog = "")
public class EquipmentGenericEntity {
    private int id;
    private String name;
    private String description;
    private Collection<EquipmentItemLinkEntity> equipmentItemsLinksById;
    private Collection<RequirementLinkEntity> requirementsLinksById;

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
        EquipmentGenericEntity that = (EquipmentGenericEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @OneToMany(mappedBy = "equipmentGenericsByGenericId")
    public Collection<EquipmentItemLinkEntity> getEquipmentItemsLinksById() {
        return equipmentItemsLinksById;
    }

    public void setEquipmentItemsLinksById(Collection<EquipmentItemLinkEntity> equipmentItemsLinksById) {
        this.equipmentItemsLinksById = equipmentItemsLinksById;
    }

    @OneToMany(mappedBy = "equipmentGenericsByGenericId")
    public Collection<RequirementLinkEntity> getRequirementsLinksById() {
        return requirementsLinksById;
    }

    public void setRequirementsLinksById(Collection<RequirementLinkEntity> requirementsLinksById) {
        this.requirementsLinksById = requirementsLinksById;
    }
}
