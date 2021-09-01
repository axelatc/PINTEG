package com.atc.services;

import com.atc.persistence.JpaUtils;
import com.atc.persistence.entities.UserEntity;
import com.atc.persistence.entities.UserSubscriptionEntity;
import org.apache.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author axel
 */
@ApplicationScoped
public class UserSubscriptionService extends ServiceImpl<UserSubscriptionEntity> {

    private final static Logger LOG = Logger.getLogger(UserSubscriptionService.class);

    public boolean exist(UserSubscriptionEntity p, EntityManager em) {
        return (findOneByIdOrNull(p.getId(), em) != null);
    }


    public UserSubscriptionEntity findOneByIdOrNull(int id, EntityManager em) {
        LOG.info("Select a userSubscription by the id : " + id);
        return em.find(UserSubscriptionEntity.class, id);
    }

    public List<UserSubscriptionEntity> findAllOrNull(EntityManager em) {
        try {
            LOG.info("Create named query UserSubscription.findAll");
            TypedQuery<UserSubscriptionEntity> query = em.createNamedQuery("UserSubscription.findAll", UserSubscriptionEntity.class);
            List<UserSubscriptionEntity> userSubscriptionList = query.getResultList();
            LOG.info("List " + UserSubscriptionEntity.class.getSimpleName() + " size: " + userSubscriptionList.size());
            LOG.info("Selected all userSubscriptions from database ");
            return userSubscriptionList;
        } catch (Exception e) {
            LOG.info("The query found no userSubscription to return", e);
            return null;
        }
    }

    /**
     * @param user
     * @return the active user's subscription or null if no UserSubscription was not found
     */
    public UserSubscriptionEntity findActiveUserSubscriptionForUserOrNull(UserEntity user) {
        LOG.info("Finding active subscription of user: " + user.toString());

        EntityManager em = JpaUtils.createEntityManager();
        if (new UserService().exist(user, em)) {
            try {
                return em.createNamedQuery("UserSubscription.findMostRecentUserSubscriptionForUser", UserSubscriptionEntity.class)
                        .setParameter("userId", user)
                        .setMaxResults(1)
                        .getSingleResult();
            } catch (NoResultException e) {
                LOG.info("The query found no user's subscription to return", e);
                return null;
            } finally {
                em.close();
            }
        } else {
            em.clear();
            em.close();
            throw new IllegalArgumentException("Can't query user's subscription in database: the user does not exist");
        }
    }
}

