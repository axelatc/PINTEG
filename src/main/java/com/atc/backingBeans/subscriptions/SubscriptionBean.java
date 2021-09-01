package com.atc.backingBeans.subscriptions;

import com.atc.backingBeans.auth.AuthBean;
import com.atc.persistence.JpaUtils;
import com.atc.persistence.entities.RoleEntity;
import com.atc.persistence.entities.SubscriptionEntity;
import com.atc.persistence.entities.UserEntity;
import com.atc.persistence.entities.UserSubscriptionEntity;
import com.atc.services.SubscriptionService;
import com.atc.services.UserService;
import com.atc.services.UserSubscriptionService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.atc.persistence.JpaUtils.createEntityManager;
import static com.atc.utils.JsfUtils.*;

/**
 * @author axel
 * Responsible for managing the user's subscription
 */
@SessionScoped
@Named
public class SubscriptionBean implements Serializable {

    private final static Logger LOG = Logger.getLogger(SubscriptionBean.class);
    public static final String FAILURE_CURRENT_USER_UNFOUND_MESSAGE_NAME = "subscription.failure.currentUserUnfound";
    private final static String SWITCH_SUCCESS_LOCALE_MESSAGE_NAME = "subscriptionSwitch.successMessage";
    private final static String SWITCH_FAILURE_LOCALE_MESSAGE_NAME = "subscriptionSwitch.failureMessage";

    @Inject
    private AuthBean authBean;

    @Inject
    private UserService userService;
    private UserEntity currentUser;

    @Inject
    private SubscriptionService subscriptionService;
    private List<SubscriptionEntity> allSubscriptions;
    private SubscriptionEntity activeSubscription;
    private SubscriptionEntity nextSubscription;
    private BigDecimal switchingCost;

    @Inject
    private UserSubscriptionService userSubscriptionService;
    private UserSubscriptionEntity activeUserSubscription;

    @PostConstruct
    public void init() {
        currentUser = authBean.getCurrentUser();
        EntityManager em = createEntityManager();
        allSubscriptions = subscriptionService.findAllOrNull(em);
        activeUserSubscription = userSubscriptionService.findActiveUserSubscriptionForUserOrNull(currentUser);
        activeSubscription = activeUserSubscription.getSubscriptionsBySubscriptionId();
        em.close();
    }


    /**
     * Switch the subscription of the current user
     * @return the outcome of the operation, "success" or "failure"
     */
    public String submit() {
        EntityManager em = JpaUtils.createEntityManager();
        UserEntity foundUser = userService.findOneByIdOrNull(this.currentUser.getId(), em);
        if(foundUser == null) {
            LOG.info("Switching to subscription " + this.nextSubscription.toString() + " has failed: no user entity has been found.");
            addErrorMessage(getLocaleMessageAsString(FAILURE_CURRENT_USER_UNFOUND_MESSAGE_NAME));
            em.clear();
            em.close();
            return "failure";
        }

        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            LOG.info("Switching current user to new subscription" + this.nextSubscription.toString());
            subscriptionService.switchUserToNewSubscription(foundUser, this.nextSubscription, em);

            RoleEntity nextRole = subscriptionService.findRoleCorrespondingToSubscriptionOrNull(this.nextSubscription);
            foundUser.setRolesByRoleId(nextRole);
            LOG.info("Updating user to new role" + nextRole.toString());
            userService.update(foundUser, em);

            tx.commit();
            LOG.info("Subscription is updated.");

            authBean.setCurrentUser(foundUser);
            activeUserSubscription = userSubscriptionService.findActiveUserSubscriptionForUserOrNull(currentUser);
            activeSubscription = activeUserSubscription.getSubscriptionsBySubscriptionId();
            addSuccessMessage(getLocaleMessageAsString(SWITCH_SUCCESS_LOCALE_MESSAGE_NAME));
            return "success";
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOG.info("Updating of subscription has failed.", ex);
            addErrorMessage(getLocaleMessageAsString(SWITCH_FAILURE_LOCALE_MESSAGE_NAME));
            return "failure";
        } finally {
            em.close();
        }


    }

    public List<SubscriptionEntity> getAllSubscriptions() {
        return allSubscriptions;
    }

    public SubscriptionEntity getActiveSubscription() {
        activeSubscription = activeUserSubscription.getSubscriptionsBySubscriptionId();
        return activeSubscription;
    }

    public void setActiveSubscription(SubscriptionEntity activeSubscription) {
        this.activeSubscription = activeSubscription;
    }

    public SubscriptionEntity getNextSubscription() {
        return nextSubscription;
    }

    public void setNextSubscription(SubscriptionEntity nextSubscription) {
        this.nextSubscription = nextSubscription;
    }

    public UserSubscriptionEntity getActiveUserSubscription() {
        activeUserSubscription = userSubscriptionService.findActiveUserSubscriptionForUserOrNull(currentUser);
        return activeUserSubscription;
    }

    public void setActiveUserSubscription(UserSubscriptionEntity activeUserSubscription) {
        this.activeUserSubscription = activeUserSubscription;
    }

    public BigDecimal getSwitchingCost() {
        return subscriptionService.computeSwitchingPriceFromOldToNew(
                this.activeUserSubscription.getEndDateTime(),
                this.activeSubscription.getPricePerMonth(),
                this.nextSubscription.getPricePerMonth()
        );
    }

    public long getRemainingDaysOfActiveSubscription() {
        return Duration.between(LocalDateTime.now(), activeUserSubscription.getEndDateTime()).toDays();
    }
}


