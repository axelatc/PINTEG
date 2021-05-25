package com.atc.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "equipment_items_link", schema = "shapp", catalog = "")
public class EquipmentItemLinkEntity {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private String referalBuyUrl;
    private EquipmentGenericEntity equipmentGenericsByGenericId;
    private BrandEntity brandsByBrandId;

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

    @Basic
    @Column(name = "price", nullable = true, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "referal_buy_URL", nullable = true, length = 2083)
    public String getReferalBuyUrl() {
        return referalBuyUrl;
    }

    public void setReferalBuyUrl(String referalBuyUrl) {
        this.referalBuyUrl = referalBuyUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentItemLinkEntity that = (EquipmentItemLinkEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(referalBuyUrl, that.referalBuyUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, referalBuyUrl);
    }

    @ManyToOne
    @JoinColumn(name = "generic_id", referencedColumnName = "id", nullable = false)
    public EquipmentGenericEntity getEquipmentGenericsByGenericId() {
        return equipmentGenericsByGenericId;
    }

    public void setEquipmentGenericsByGenericId(EquipmentGenericEntity equipmentGenericsByGenericId) {
        this.equipmentGenericsByGenericId = equipmentGenericsByGenericId;
    }

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id", nullable = false)
    public BrandEntity getBrandsByBrandId() {
        return brandsByBrandId;
    }

    public void setBrandsByBrandId(BrandEntity brandsByBrandId) {
        this.brandsByBrandId = brandsByBrandId;
    }
}
