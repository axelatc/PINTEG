package com.atc.services;

import com.atc.persistence.JpaUtils;
import com.atc.persistence.entities.RoleEntity;
import com.atc.persistence.entities.SubscriptionEntity;
import com.atc.persistence.entities.UserEntity;
import com.atc.persistence.entities.UserSubscriptionEntity;
import com.atc.utils.ValidationUtils;
import org.apache.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author axel
 * Handle the business logic and peristence of the subscription feature
 */
@ApplicationScoped
public class SubscriptionService extends ServiceImpl<SubscriptionEntity> {

    private final static String FREE_BASIC_SUBSCRIPTION_NAME = "Bronze";
    private final static String GENERIC_PREFIX_OF_ROLE_NAME = "user_";
    public final static int DURATION_IN_DAYS_OF_ALL_SUBSCRIPTIONS = 30;
    private final static Logger LOG = Logger.getLogger(SubscriptionService.class);

    @Inject
    UserSubscriptionService userSubscriptionService;
    @Inject
    RoleService roleService;

    public boolean exist(SubscriptionEntity p, EntityManager em) {
        return (findSubscriptionByNameOrNull(p.getName()) != null);
    }


    public SubscriptionEntity findOneByIdOrNull(int id, EntityManager em) {
        LOG.info("Select a subscription by the id : " + id);
        return em.find(SubscriptionEntity.class, id);
    }

    public List<SubscriptionEntity> findAllOrNull(EntityManager em) {
        try {
            LOG.info("Create named query Subscription.findAll");
            TypedQuery<SubscriptionEntity> query = em.createNamedQuery("Subscription.findAll", SubscriptionEntity.class);
            List<SubscriptionEntity> subscriptionList = query.getResultList();
            LOG.info("List " + SubscriptionEntity.class.getSimpleName() + " size: " + subscriptionList.size());
            LOG.info("Selected all subscriptions from database ");
            return subscriptionList;
        } catch (Exception e) {
            LOG.info("The query found no subscription to return", e);
            return null;
        }
    }

    public SubscriptionEntity findSubscriptionByNameOrNull(String name) throws IllegalArgumentException {
        LOG.info("Finding subscription of name " + name);

        EntityManager em = JpaUtils.createEntityManager();
        if (ValidationUtils.hasContent(name)) {
            try {
                return em.createNamedQuery("Subscription.findOneByName", SubscriptionEntity.class)
                        .setParameter("name", name)
                        .getSingleResult();
            } catch (NoResultException e) {
                LOG.info("The query found no subscription to return", e);
                return null;
            } finally {
                em.close();
            }
        } else {
            em.clear();
            em.close();
            throw new IllegalArgumentException("Can't query subscription in database: the name is null or empty");
        }
    }

    public SubscriptionEntity findBasicFreeSubscription() {
        return findSubscriptionByNameOrNull(FREE_BASIC_SUBSCRIPTION_NAME);
    }

    /**
     * Compute the prorated price when the user wants to switch to a new subscription from its active subscription
     * The computation is as follows:
     * price of new subscription - ( (price of active subscription * (number of remaining days on the active subscription / total number of days of the active subscription) )
     * @param expirationMomentOfActiveSubscription expiration moment of active subscription
     * @param pricePerMonthOfActiveSubscription price per month of active subscription
     * @param pricePerMonthOfNewSubscription price per month of new subscription
     * @return the prorated price from the switch, can be positive or negative
     */
    public BigDecimal computeSwitchingPriceFromOldToNew(LocalDateTime expirationMomentOfActiveSubscription,
                                                        BigDecimal pricePerMonthOfActiveSubscription,
                                                        BigDecimal pricePerMonthOfNewSubscription) {
        long remainingDaysOfActiveSubscriptionAsLong = Duration.between(expirationMomentOfActiveSubscription, LocalDateTime.now()).toDays();
        BigDecimal remainingDaysOfActiveSubscription = new BigDecimal(remainingDaysOfActiveSubscriptionAsLong);
        BigDecimal durationInDaysOfAllSubscriptions = new BigDecimal(DURATION_IN_DAYS_OF_ALL_SUBSCRIPTIONS);
        BigDecimal priceValueOfActiveSubscriptionRemainingDays = pricePerMonthOfActiveSubscription.multiply(remainingDaysOfActiveSubscription.divide(durationInDaysOfAllSubscriptions, 2, RoundingMode.FLOOR));
        BigDecimal proratedPrice = pricePerMonthOfNewSubscription.subtract(priceValueOfActiveSubscriptionRemainingDays);
        return proratedPrice;
    }

    /**
     * @param user the user who asked to switch subscription
     * @param newSubscription the new subscription
     * @param em the entity manager to use to do insert, update and delete operations
     */
    public void switchUserToNewSubscription(UserEntity user, SubscriptionEntity newSubscription, EntityManager em) {
        UserSubscriptionEntity activeUserSubscription = userSubscriptionService.findActiveUserSubscriptionForUserOrNull(user);
        if(activeUserSubscription == null) {
            throw new RuntimeException("Can't find active userSubscription of user: " + user.toString());
        }

        SubscriptionEntity activeSubscription = activeUserSubscription.getSubscriptionsBySubscriptionId();

        try {
            LOG.info("Expire active userSubscription " + activeUserSubscription.toString());
            activeUserSubscription.setEndDateTime(LocalDateTime.now());
            userSubscriptionService.update(activeUserSubscription, em);
            UserSubscriptionEntity newUserSubscriptionToSave = new UserSubscriptionEntity(LocalDateTime.now(), LocalDateTime.now().plusDays(30L), user, newSubscription);
            LOG.info("Insert new active userSubscription " + newUserSubscriptionToSave.toString());
        } catch (Exception e) {
            LOG.info("The updating of the active userSubscription or the insert of a new one failed", e);
        }

    }

    /**
     * @param subscription the subscription
     * @return the role corresponding to the subscription
     */
    public RoleEntity findRoleCorrespondingToSubscriptionOrNull(SubscriptionEntity subscription) {
        LOG.info("Attempting to find role corresponding to subscription " + subscription.toString());
        String lowercaseSubscriptionName = subscription.getName();
        String correspondingRoleName = GENERIC_PREFIX_OF_ROLE_NAME + lowercaseSubscriptionName;
        return roleService.findRoleByLabelOrNull(correspondingRoleName);
    }
}

