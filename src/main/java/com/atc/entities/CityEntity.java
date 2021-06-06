package com.atc.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "cities", schema = "shapp", catalog = "")
public class CityEntity {
    private int id;
    private String nameFr;
    private String nameEn;
    private String zipCode;
    private Collection<AddressEntity> addressesById;
    private CountryEntity countriesByCountryId;
    private Collection<ResearchAreaEntity> researchAreasById;

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
    @Column(name = "zip_code", nullable = false, length = 100)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityEntity that = (CityEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(nameFr, that.nameFr) && Objects.equals(nameEn, that.nameEn) && Objects.equals(zipCode, that.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameFr, nameEn, zipCode);
    }

    @OneToMany(mappedBy = "citiesByCityId")
    public Collection<AddressEntity> getAddressesById() {
        return addressesById;
    }

    public void setAddressesById(Collection<AddressEntity> addressesById) {
        this.addressesById = addressesById;
    }

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    public CountryEntity getCountriesByCountryId() {
        return countriesByCountryId;
    }

    public void setCountriesByCountryId(CountryEntity countriesByCountryId) {
        this.countriesByCountryId = countriesByCountryId;
    }

    @OneToMany(mappedBy = "citiesByCityId")
    public Collection<ResearchAreaEntity> getResearchAreasById() {
        return researchAreasById;
    }

    public void setResearchAreasById(Collection<ResearchAreaEntity> researchAreasById) {
        this.researchAreasById = researchAreasById;
    }
}
