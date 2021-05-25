package com.atc.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "countries", schema = "shapp", catalog = "")
public class CountryEntity {
    private int id;
    private String nameFr;
    private String nameEn;
    private String isoNum;
    private String isoAlpha;
    private Collection<CityEntity> citiesById;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Basic
    @Column(name = "name_fr", nullable = false, length = 100)
    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    @Basic
    @Column(name = "name_en", nullable = false, length = 100)
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    @Basic
    @Column(name = "iso_num", nullable = false, length = 5)
    public String getIsoNum() {
        return isoNum;
    }

    public void setIsoNum(String isoNum) {
        this.isoNum = isoNum;
    }

    @Basic
    @Column(name = "iso_alpha", nullable = false, length = 5)
    public String getIsoAlpha() {
        return isoAlpha;
    }

    public void setIsoAlpha(String isoAlpha) {
        this.isoAlpha = isoAlpha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryEntity that = (CountryEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(nameFr, that.nameFr) && Objects.equals(nameEn, that.nameEn) && Objects.equals(isoNum, that.isoNum) && Objects.equals(isoAlpha, that.isoAlpha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameFr, nameEn, isoNum, isoAlpha);
    }

    @OneToMany(mappedBy = "countriesByCountryId")
    public Collection<CityEntity> getCitiesById() {
        return citiesById;
    }

    public void setCitiesById(Collection<CityEntity> citiesById) {
        this.citiesById = citiesById;
    }
}
