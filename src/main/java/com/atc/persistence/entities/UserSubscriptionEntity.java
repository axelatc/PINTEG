package com.atc.persistence.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author axel
 */
@Entity
@Table(name = "users_subscriptions", schema = "shapp", catalog = "")
public class UserSubscriptionEntity {
    private int id;
    private LocalDateTime beginDateTime;
    private LocalDateTime endDateTime;
    private UserEntity usersByUserId;
    private SubscriptionEntity subscriptionsBySubscriptionId;

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
