package com.atc.persistence.entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author axel
 */
@Entity
@Table(name = "users_subscriptions", schema = "shapp", catalog = "")
@NamedQueries(
        value = {
                @NamedQuery(name = "UserSubscription.findAll",
                        query = "SELECT p from UserSubscriptionEntity p"),
                @NamedQuery(name = "UserSubscription.findMostRecentUserSubscriptionForUser",
                        query = "SELECT u_s from UserSubscriptionEntity u_s where u_s.usersByUserId = :userId ORDER BY u_s.beginDateTime desc")
        }

)
@SessionScoped
@Named
public class UserSubscriptionEntity implements Serializable {
    @Positive
    private int id;

    @NotNull
    private LocalDateTime beginDateTime;

    @NotNull
    private LocalDateTime endDateTime;

    @Positive
    private UserEntity usersByUserId;
    @Positive
    private SubscriptionEntity subscriptionsBySubscriptionId;

    public UserSubscriptionEntity(@NotNull LocalDateTime beginDateTime, @NotNull LocalDateTime endDateTime, @Positive UserEntity usersByUserId, @Positive SubscriptionEntity subscriptionsBySubscriptionId) {
        this.beginDateTime = beginDateTime;
        this.endDateTime = endDateTime;
        this.usersByUserId = usersByUserId;
        this.subscriptionsBySubscriptionId = subscriptionsBySubscriptionId;
    }

    public UserSubscriptionEntity() {

    }

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
    @Column(name = "begin_date_time", nullable = false)
    public LocalDateTime getBeginDateTime() {
        return beginDateTime;
    }

    public void setBeginDateTime(LocalDateTime beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    @Basic
    @Column(name = "end_date_time", nullable = false)
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSubscriptionEntity that = (UserSubscriptionEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(beginDateTime, that.beginDateTime) && Objects.equals(endDateTime, that.endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, beginDateTime, endDateTime);
    }

    @Override
    public String toString() {
        return "UserSubscriptionEntity{" +
                "id=" + id +
                ", beginDateTime=" + beginDateTime +
                ", endDateTime=" + endDateTime +
                ", usersByUserId=" + usersByUserId +
                ", subscriptionsBySubscriptionId=" + subscriptionsBySubscriptionId +
                '}';
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UserEntity getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(UserEntity usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    public SubscriptionEntity getSubscriptionsBySubscriptionId() {
        return subscriptionsBySubscriptionId;
    }

    public void setSubscriptionsBySubscriptionId(SubscriptionEntity subscriptionsBySubscriptionId) {
        this.subscriptionsBySubscriptionId = subscriptionsBySubscriptionId;
    }
}
