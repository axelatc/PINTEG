package com.atc.persistence.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;


/**
 * @author axel
 */
@Entity
@Table(name = "measurands", schema = "shapp", catalog = "")
public class MeasurandEntity {
    private int id;
    private String name;
    private String description;
    private UnitEntity unitsByUnitId;
    private Collection<MeasureEntity> measuresById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "description", nullable = false, length = -1)
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
        MeasurandEntity that = (MeasurandEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    public UnitEntity getUnitsByUnitId() {
        return unitsByUnitId;
    }

    public void setUnitsByUnitId(UnitEntity unitsByUnitId) {
        this.unitsByUnitId = unitsByUnitId;
    }

    @OneToMany(mappedBy = "measurandsByMeasurandId")
    public Collection<MeasureEntity> getMeasuresById() {
        return measuresById;
    }

    public void setMeasuresById(Collection<MeasureEntity> measuresById) {
        this.measuresById = measuresById;
    }
}
