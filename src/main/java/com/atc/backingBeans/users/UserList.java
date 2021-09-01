package com.atc.backingBeans.users;

import com.atc.persistence.JpaUtils;
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
import java.util.List;

import static com.atc.persistence.JpaUtils.createEntityManager;
import static com.atc.utils.JsfUtils.*;

/**
 * @author axel
 */
@Named
@SessionScoped
public class UserList implements Serializable {

    private final static Logger LOG = Logger.getLogger(UserList.class);
    private final static String SUCCESS_LOCALE_MESSAGE_NAME = "userDelete.successMessage";
    private final static String FAILURE_LOCALE_MESSAGE_NAME = "userDelete.failureMessage";
    public static final String FAILURE_ENTITY_UNFOUND_MESSAGE_NAME = "userDelete.failure.unfound";

    @Inject
    private UserService userService;
    private List<UserEntity> users;

    @PostConstruct
    public void init() {
        EntityManager em = createEntityManager();
        users = userService.findAllOrNull(em);
        em.close();
    }

    public String delete(UserEntity userToDelete) {
        EntityManager em = createEntityManager();

        UserEntity foundUser = userService.findOneByIdOrNull(userToDelete.getId(), em);
        if(foundUser == null) {
            LOG.info("Deletion of user entity " + userToDelete.toString() + " has failed: no user entity has been found.");
            addErrorMessage(getLocaleMessageAsString(FAILURE_ENTITY_UNFOUND_MESSAGE_NAME));
            em.clear();
            em.close();
            return "failure";
        }

        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            userService.delete(foundUser, em);
            tx.commit();
            LOG.info("User entity " + userToDelete.toString() + " is removed.");
            addSuccessMessage(getLocaleMessageAsString(SUCCESS_LOCALE_MESSAGE_NAME));
            return "success";
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOG.info("Deletion of user entity " + userToDelete.toString() + " has failed.", ex);
            addErrorMessage(getLocaleMessageAsString(FAILURE_LOCALE_MESSAGE_NAME));
            return "failure";
        } finally {
            em.close();
        }
    }

    public List<UserEntity> getUsers() {
        users = userService.findAllOrNull(JpaUtils.createEntityManager());
        return users;
    }
    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
