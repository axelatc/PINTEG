package com.atc.backingBeans.auth;

import com.atc.persistence.entities.*;
import com.atc.services.RoleService;
import com.atc.services.SubscriptionService;
import com.atc.services.UserService;
import com.atc.persistence.JpaUtils;
import com.atc.services.UserSubscriptionService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.atc.utils.JsfUtils.*;

/**
 * @author axel
 */
@Named
@SessionScoped
public class RegisterBean implements Serializable {
    private final static Logger LOG = Logger.getLogger(RegisterBean.class);
    private final static String SUCCESS_LOCALE_MESSAGE_NAME = "register.successMessage";
    private final static String FAILURE_LOCALE_MESSAGE_NAME = "register.failureMessage";

    private UserEntity user;

    @Inject
    private UserService userService;
    @Inject
    private RoleService roleService;
    @Inject
    private UserSubscriptionService userSubscriptionService;
    @Inject
    private SubscriptionService subscriptionService;

    @PostConstruct
    public void init() {
        user = new UserEntity();
    }

    public String submit() {

        LOG.info("Attempting to persist newly registered user...");
        EntityManager em = JpaUtils.createEntityManager();
        EntityTransaction tx = null;
        try {

            boolean alreadyExist = userService.exist(user, em);
            if(alreadyExist) {
                throw new RuntimeException("Can't persist user in data store: it already exists");
            }

            tx = em.getTransaction();
            LOG.info("Begin transaction to persist newly registered user...");
            tx.begin();
            UserEntity userToInsert = this.user;
            String encryptedPassword = AuthBean.hashAndSalt(this.user.getPassword());
            userToInsert.setPassword(encryptedPassword);
            LOG.info("Finding the role appropriate for a newly registered user");
            RoleEntity roleForNewlyRegisteredUser = roleService.findRoleForNewlyRegisteredUserOrNull();
            userToInsert.setRolesByRoleId(roleForNewlyRegisteredUser);
            LOG.info("Finding the role appropriate for a newly registered user");
            userToInsert.setCreationDateTime(LocalDateTime.now());
            userToInsert.setActive(true);
            LOG.info("Inserting new user");
            userService.insert(userToInsert, em);

            SubscriptionEntity freeBasicSubscription = subscriptionService.findBasicFreeSubscription();
            UserSubscriptionEntity userSubscriptionEntity = new UserSubscriptionEntity(
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(SubscriptionService.DURATION_IN_DAYS_OF_ALL_SUBSCRIPTIONS),
                    userToInsert,
                    freeBasicSubscription
            );
            LOG.info("Inserting basic free userSubscription " + userSubscriptionEntity.toString());
            userSubscriptionService.insert(userSubscriptionEntity, em);

            tx.commit();
            LOG.info("Transaction committed. New user is persisted successfully.");

            addSuccessMessage(getLocaleMessageAsString(SUCCESS_LOCALE_MESSAGE_NAME));
            return "success";
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOG.info("Creation of user entity has failed.", ex);
            addErrorMessage(getLocaleMessageAsString(FAILURE_LOCALE_MESSAGE_NAME));
            return "failure";
        } finally {
            em.close();
        }
    }

    public Gender[] getGenders() {
        return Gender.values();
    }

    public LocalDate getToday() {
        return LocalDate.now();
    }

    public UserEntity getUser() {
        return user;
    }
}
