package com.atc.backingBeans.users;

import com.atc.persistence.JpaUtils;
import com.atc.persistence.entities.Gender;
import com.atc.persistence.entities.RoleEntity;
import com.atc.persistence.entities.UserEntity;
import com.atc.services.RoleService;
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
import java.util.List;

import static com.atc.persistence.JpaUtils.createEntityManager;
import static com.atc.utils.JsfUtils.*;

/**
 * @author axel
 * Handle the editing and updating of a user
 */
@Named
@SessionScoped
public class UserEdit implements Serializable {

    private final static Logger LOG = Logger.getLogger(UserEdit.class);
    private final static String SUCCESS_LOCALE_MESSAGE_NAME = "userEdit.successMessage";
    private final static String FAILURE_LOCALE_MESSAGE_NAME = "userEdit.failureMessage";
    public static final String FAILURE_ENTITY_UNFOUND_MESSAGE_NAME = "userEdit.failure.unfound";

    private UserEntity user;
    @Inject
    private UserService userService;

    private List<RoleEntity> roles;
    @Inject
    private RoleService roleService;

    @PostConstruct
    public void init() {
        user = new UserEntity();
        EntityManager em = createEntityManager();
        roles = roleService.findAllOrNull(em);
        em.close();
    }

    /**
     * Persist the modifications made to the user
     * @return the outcome for navigation rules
     */
    public String save() {

        EntityManager em = JpaUtils.createEntityManager();
        UserEntity foundUser = userService.findOneByIdOrNull(this.user.getId(), em);
        if(foundUser == null) {
            LOG.info("Updating of user entity " + user.toString() + " has failed: no user entity has been found.");
            addErrorMessage(getLocaleMessageAsString(FAILURE_ENTITY_UNFOUND_MESSAGE_NAME));
            em.clear();
            em.close();
            return "failure";
        }

        EntityTransaction tx = null;
        try {

            user.setId(foundUser.getId());
            tx = em.getTransaction();
            tx.begin();
            userService.update(user, em);
            tx.commit();
            LOG.info("User entity is updated.");

            addSuccessMessage(getLocaleMessageAsString(SUCCESS_LOCALE_MESSAGE_NAME));

            return "success";
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOG.info("Updating of user entity has failed.", ex);
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

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }
}
