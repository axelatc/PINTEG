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
@Named
@SessionScoped
public class RoleEdit implements Serializable {

    private final static Logger LOG = Logger.getLogger(RoleEdit.class);
    private final static String SUCCESS_LOCALE_MESSAGE_NAME = "roleEdit.successMessage";
    private final static String FAILURE_LOCALE_MESSAGE_NAME = "roleEdit.failureMessage";

    @Inject
    private RoleEntity role;
    @Inject
    private RoleService roleService;

    public String save() {

        EntityManager em = JpaUtils.createEntityManager();
        EntityTransaction tx = null;
        try {

            RoleEntity foundRole = roleService.findOneByIdOrNull(this.role.getId(), em);
            if(foundRole == null) {
                throw new RuntimeException("Can't update role in data store: it doesn't exists");
            }
            foundRole.setLabel(role.getLabel());
            foundRole.setDescription(role.getDescription());

            tx = em.getTransaction();
            tx.begin();
            roleService.update(foundRole, em);
            tx.commit();
            LOG.info("Role entity is updated.");

            addSuccessMessage(getLocaleMessageAsString(SUCCESS_LOCALE_MESSAGE_NAME));

            return "success";
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOG.info("Updating of role entity has failed.", ex);
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
