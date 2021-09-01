package com.atc.backingBeans.users;

import com.atc.backingBeans.auth.AuthBean;
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
import java.time.LocalDateTime;
import java.util.List;

import static com.atc.persistence.JpaUtils.createEntityManager;
import static com.atc.utils.JsfUtils.*;

/**
 * @author axel
 * Handle the creation of a user
 */
@Named
@SessionScoped
public class UserCreate implements Serializable {

    private final static Logger LOG = Logger.getLogger(UserCreate.class);
    private final static String SUCCESS_LOCALE_MESSAGE_NAME = "userCreate.successMessage";
    private final static String FAILURE_LOCALE_MESSAGE_NAME = "userCreate.failureMessage";

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
     * Persist the new user
     * @return the outcome for navigation rules
     */
    public String save() {

        LOG.info("Attempting to persist new user...");
        EntityManager em = JpaUtils.createEntityManager();
        EntityTransaction tx = null;
        try {

            boolean alreadyExist = userService.exist(user, em);
            if(alreadyExist) {
                throw new RuntimeException("Can't create user " + user.toString() + " in data storage: it already exists");
            }

            UserEntity userToInsert = new UserEntity(user);
            String encryptedPassword = AuthBean.hashAndSalt(this.user.getPassword());
            userToInsert.setPassword(encryptedPassword);
            userToInsert.setCreationDateTime(LocalDateTime.now());
            userToInsert.setActive(true);

            tx = em.getTransaction();
            LOG.info("Begin transaction to persist new user...");
            tx.begin();
            LOG.info("Attempting to insert new user" + userToInsert.toString() + "in data storage...");
            userService.insert(userToInsert, em);
            tx.commit();
            LOG.info("Transaction committed. New user is persisted successfully.");

            addSuccessMessage(getLocaleMessageAsString(SUCCESS_LOCALE_MESSAGE_NAME));
            return "success";
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOG.info("Creation of user entity" + user.toString() + " has failed.", ex);
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
