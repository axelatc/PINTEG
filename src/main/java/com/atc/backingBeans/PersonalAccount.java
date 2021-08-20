package com.atc.backingBeans;

import com.atc.backingBeans.auth.AuthBean;
import com.atc.persistence.JpaUtils;
import com.atc.persistence.entities.Gender;
import com.atc.persistence.entities.UserEntity;
import com.atc.services.UserService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.time.LocalDate;

import static com.atc.utils.JsfUtils.*;

/**
 * @author axel
 * Responsible for managing the user's personal account
 */
@SessionScoped
@Named
public class PersonalAccount implements Serializable {

    private final static Logger LOG = Logger.getLogger(PersonalAccount.class);
    public static final String FAILURE_ENTITY_UNFOUND_MESSAGE_NAME = "account.failure.unfound";
    private final static String EDIT_SUCCESS_LOCALE_MESSAGE_NAME = "accountEdit.successMessage";
    private final static String EDIT_FAILURE_LOCALE_MESSAGE_NAME = "accountEdit.failureMessage";
    private final static String DEACTIVATE_SUCCESS_LOCALE_MESSAGE_NAME = "accountDeactivate.successMessage";
    private final static String DEACTIVATE_FAILURE_LOCALE_MESSAGE_NAME = "accountDeactivate.failureMessage";

    @Inject
    AuthBean authBean;
    @Inject
    private UserService userService;


    private UserEntity currentUser;
    private UserEntity userToEdit;
    private UserEntity userToDeactivate;

    @PostConstruct
    public void init() {
        userToEdit = authBean.getCurrentUser();
        currentUser = authBean.getCurrentUser();
        userToDeactivate = authBean.getCurrentUser();
    }

    public Gender[] getGenders() {
        return Gender.values();
    }

    public LocalDate getToday() {
        return LocalDate.now();
    }

    /**
     * Update the current authenticated user
     * @return the outcome of the operation, "success" or "failure"
     */
    public String saveEdit() {
        EntityManager em = JpaUtils.createEntityManager();
        UserEntity foundUser = userService.findOneByIdOrNull(this.userToEdit.getId(), em);
        if(foundUser == null) {
            LOG.info("Updating of user entity " + this.userToEdit.toString() + " has failed: no user entity has been found.");
            addErrorMessage(getLocaleMessageAsString(FAILURE_ENTITY_UNFOUND_MESSAGE_NAME));
            em.clear();
            em.close();
            return "failure";
        }

        String encryptedPassword = AuthBean.hashAndSalt(this.userToEdit.getPassword());
        this.userToEdit.setPassword(encryptedPassword);

        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            userService.update(this.userToEdit, em);
            tx.commit();
            LOG.info("User entity is updated.");

            authBean.setCurrentUser(this.userToEdit);
            addSuccessMessage(getLocaleMessageAsString(EDIT_SUCCESS_LOCALE_MESSAGE_NAME));
            return "success";
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOG.info("Updating of user entity has failed.", ex);
            addErrorMessage(getLocaleMessageAsString(EDIT_FAILURE_LOCALE_MESSAGE_NAME));
            return "failure";
        } finally {
            em.close();
        }


    }

    /**
     * Deactivate the current authenticated user
     * @return the outcome of the operation, "failure" or "success"
     */
    public String deactivate() {
        this.userToDeactivate = authBean.getCurrentUser();
        EntityManager em = JpaUtils.createEntityManager();
        UserEntity foundUser = userService.findOneByIdOrNull(this.userToDeactivate.getId(), em);
        if(foundUser == null) {
            LOG.info("Deactivation of user entity " + this.userToDeactivate.toString() + " has failed: no user entity has been found.");
            addErrorMessage(getLocaleMessageAsString(FAILURE_ENTITY_UNFOUND_MESSAGE_NAME));
            em.clear();
            em.close();
            return "failure";
        }


        this.userToDeactivate.setActive(false);
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            userService.update(this.userToDeactivate, em);
            tx.commit();
            LOG.info("User entity is deactivated.");

            authBean.logout();
            addSuccessMessage(getLocaleMessageAsString(DEACTIVATE_SUCCESS_LOCALE_MESSAGE_NAME));
            return "success";
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOG.info("Deactivation of user entity has failed.", ex);
            addErrorMessage(getLocaleMessageAsString(DEACTIVATE_FAILURE_LOCALE_MESSAGE_NAME));
            return "failure";
        } finally {
            em.close();
        }
    }

    public UserEntity getCurrentUser() {
        return authBean.getCurrentUser();
    }

    public void setCurrentUser(UserEntity currentUser) {
        this.currentUser = currentUser;
    }

    public UserEntity getUserToEdit() {
        return authBean.getCurrentUser();
    }

    public void setUserToEdit(UserEntity userToEdit) {
        this.userToEdit = userToEdit;
    }

}


