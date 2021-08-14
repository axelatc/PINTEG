package com.atc.persistence.entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * @author axel
 */
@NamedQueries(
        value = {
                @NamedQuery(name = "Unit.findAll",
                        query = "SELECT p from UnitEntity p"),
                @NamedQuery(name = "Unit.findOneByName",
                        query = "SELECT p from UnitEntity p where p.name= :name")
        }

)
@Entity
@Table(name = "units", schema = "shapp", catalog = "")
@SessionScoped
@Named
public class UnitEntity implements Serializable {

    @Positive
    private int id;

    @NotBlank
    @NotNull
    @Size(min=1, max=100)
    private String name;

    @NotBlank
    @NotNull
    @Size(min=1, max=100)
    private String symbol;

    @NotBlank
    @NotNull
    @Size(min=1, max=2000)
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
