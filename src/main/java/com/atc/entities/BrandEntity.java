package com.atc.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * @author william
 * temporaire: fonctionnalités à redéfinir
 */
@Entity
@Table(name = "brands", schema = "shapp", catalog = "")
public class BrandEntity {
    private int id;
    private String name;
    private String description;
    private Collection<EquipmentItemLinkEntity> equipmentItemsLinksById;

    @Id
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
        BrandEntity that = (BrandEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @OneToMany(mappedBy = "brandsByBrandId")
    public Collection<EquipmentItemLinkEntity> getEquipmentItemsLinksById() {
        return equipmentItemsLinksById;
    }

    public void setEquipmentItemsLinksById(Collection<EquipmentItemLinkEntity> equipmentItemsLinksById) {
        this.equipmentItemsLinksById = equipmentItemsLinksById;
    }
}
