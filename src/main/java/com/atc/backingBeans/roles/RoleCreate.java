package com.atc.backingBeans.roles;

import com.atc.persistence.entities.RoleEntity;
import com.atc.services.RoleService;
import com.atc.persistence.JpaUtils;
import org.apache.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

import static com.atc.utils.JsfUtils.*;

/**
 * @author axel
 */
@SessionScoped
@Named
public class RoleCreate implements Serializable {

    private final static Logger LOG = Logger.getLogger(RoleCreate.class);
    private final static String SUCCESS_LOCALE_MESSAGE_NAME = "roleCreate.successMessage";
    private final static String FAILURE_LOCALE_MESSAGE_NAME = "roleCreate.failureMessage";

    @Inject
    private RoleEntity role;
    @Inject
    private RoleService roleService;

    public String save() {

        LOG.info("Attempting to persist new role...");
        EntityManager em = JpaUtils.createEntityManager();
        EntityTransaction tx = null;
        try {

            boolean alreadyExist = roleService.exist(role, em);
            if(alreadyExist) {
                throw new RuntimeException("Can't create role in data storage: it already exists");
            }

            tx = em.getTransaction();
            LOG.info("Begin transaction to persist new role...");
            tx.begin();
            RoleEntity roleToInsert = new RoleEntity(role.getLabel(), role.getDescription());
            LOG.info("Attempting to insert new role in data storage...");
            roleService.insert(roleToInsert, em);
            tx.commit();
            LOG.info("Transaction committed. New role is persisted successfully.");

            addSuccessMessage(getLocaleMessageAsString(SUCCESS_LOCALE_MESSAGE_NAME));

            return "success";
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOG.info("Creation of role entity has failed.", ex);
            addErrorMessage(getLocaleMessageAsString(FAILURE_LOCALE_MESSAGE_NAME));
            return "failure";
        } finally {
            em.close();
        }


    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }
}

