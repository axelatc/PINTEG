package com.atc.backingBeans.roles;

import com.atc.persistence.entities.RoleEntity;
import com.atc.services.RoleService;
import com.atc.persistence.JpaUtils;
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
public class RoleList implements Serializable {

    private final static Logger LOG = Logger.getLogger(RoleList.class);
    private final static String SUCCESS_LOCALE_MESSAGE_NAME = "roleDelete.successMessage";
    private final static String FAILURE_LOCALE_MESSAGE_NAME = "roleDelete.failureMessage";

    @Inject
    private RoleService roleService;
    private List<RoleEntity> roles;

    @PostConstruct
    public void init() {
        EntityManager em = createEntityManager();
        roles = roleService.findAllOrNull(em);
        em.close();
    }

    public String delete(RoleEntity roleToDelete) {
        EntityManager em = createEntityManager();

        EntityTransaction tx = null;
        try {
            RoleEntity foundRole = roleService.findOneByIdOrNull(roleToDelete.getId(), em);
            if(foundRole == null) {
                throw new RuntimeException("Can't delete role in data store: it doesn't exist");
            }

            tx = em.getTransaction();
            tx.begin();
            roleService.delete(foundRole, em);
            tx.commit();
            LOG.info("Role entity is removed.");
            addSuccessMessage(getLocaleMessageAsString(SUCCESS_LOCALE_MESSAGE_NAME));
            return "success";
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOG.info("Deletion of role entity has failed.", ex);
            addErrorMessage(getLocaleMessageAsString(FAILURE_LOCALE_MESSAGE_NAME));
            return "failure";
        } finally {
            em.close();
        }
    }

    public List<RoleEntity> getRoles() {
        roles = roleService.findAllOrNull(JpaUtils.createEntityManager());
        return roles;
    }
    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }
}
