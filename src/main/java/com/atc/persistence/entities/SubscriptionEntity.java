package com.atc.persistence.entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

/**
 * @author axel
 */
@Entity
@Table(name = "subscriptions", schema = "shapp", catalog = "")
@NamedQueries(
        value = {
                @NamedQuery(name = "Subscription.findAll",
                        query = "SELECT p from SubscriptionEntity p"),
                @NamedQuery(name = "Subscription.findOneByName",
                        query = "SELECT p from SubscriptionEntity p where p.name= :name"),
        }

)
@SessionScoped
@Named
public class SubscriptionEntity implements Serializable {
    @Positive
    private int id;

    @NotBlank
    @NotNull
    @Size(min=1, max=100)
    private String name;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMin(value = "10000000000.0", inclusive = true)
    @Digits(integer=11, fraction=2)
    private BigDecimal pricePerMonth;

    @NotBlank
    @NotNull
    @Size(min=1, max=2000)
    private String description;

    @PositiveOrZero
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

    @Override
    public String toString() {
        return "SubscriptionEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pricePerMonth=" + pricePerMonth +
                ", description='" + description + '\'' +
                ", rank=" + rank +
                ", usersSubscriptionsById=" + usersSubscriptionsById +
                '}';
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
