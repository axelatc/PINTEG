package com.atc.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * @author axel
 */
@Entity
@Table(name = "units", schema = "shapp", catalog = "")
public class UnitEntity {
    private int id;
    private String name;
    private String symbol;
    private String description;
    private Collection<MeasurandEntity> measurandsById;

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
    @Column(name = "symbol", nullable = false, length = 100)
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
        UnitEntity that = (UnitEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(symbol, that.symbol) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, symbol, description);
    }

    @OneToMany(mappedBy = "unitsByUnitId")
    public Collection<MeasurandEntity> getMeasurandsById() {
        return measurandsById;
    }

    public void setMeasurandsById(Collection<MeasurandEntity> measurandsById) {
        this.measurandsById = measurandsById;
    }
}
