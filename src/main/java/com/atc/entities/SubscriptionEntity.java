package com.atc.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

/**
 * @author axel
 */
@Entity
@Table(name = "subscriptions", schema = "shapp", catalog = "")
public class SubscriptionEntity {
    private int id;
    private String name;
    private BigDecimal pricePerMonth;
    private String description;
    private int rank;
    private Collection<UserSubscriptionEntity> usersSubscriptionsById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        SubscriptionEntity that = (SubscriptionEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
    @Column(name = "price_per_month", nullable = false, precision = 2)
    public BigDecimal getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(BigDecimal pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    @Basic
    @Column(name = "description", nullable = false, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "rank", nullable = false)
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @OneToMany(mappedBy = "subscriptionsBySubscriptionId")
    public Collection<UserSubscriptionEntity> getUsersSubscriptionsById() {
        return usersSubscriptionsById;
    }

    public void setUsersSubscriptionsById(Collection<UserSubscriptionEntity> usersSubscriptionsById) {
        this.usersSubscriptionsById = usersSubscriptionsById;
    }
}
