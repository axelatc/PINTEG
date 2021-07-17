package com.atc.backingBeans.permissions;

import com.atc.entities.PermissionEntity;
import com.atc.services.PermissionService;
import com.atc.utils.JpaUtils;
import org.apache.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

import static com.atc.utils.JsfUtils.*;

@Named
@SessionScoped
public class PermissionEdit implements Serializable {

    private final static Logger LOG = Logger.getLogger(PermissionEdit.class);
    private final static String SUCCESS_LOCALE_MESSAGE_NAME = "permissionEdit.successMessage";
    private final static String FAILURE_LOCALE_MESSAGE_NAME = "permissionEdit.failureMessage";

    @Inject
    private PermissionEntity permission;

    private final PermissionService permissionService = new PermissionService();

    public String save() {

        EntityManager em = JpaUtils.createEntityManager();
        EntityTransaction tx = null;
        try {

            PermissionEntity foundPermission = permissionService.findOneByIdOrNull(this.permission.getId(), em);
            if(foundPermission == null) {
                throw new RuntimeException("Can't update permission in data store: it doesn't exists");
            }
            foundPermission.setLabel(permission.getLabel());
            foundPermission.setDescription(permission.getDescription());

            tx = em.getTransaction();
            tx.begin();
            permissionService.update(foundPermission, em);
            tx.commit();
            LOG.info("Permission entity is updated.");

            addSuccessMessage(getLocaleMessageAsString(SUCCESS_LOCALE_MESSAGE_NAME));

            return "success";
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOG.info("Updating of permission entity has failed.", ex);
            addErrorMessage(getLocaleMessageAsString(FAILURE_LOCALE_MESSAGE_NAME));
            return "failure";
        } finally {
            em.clear();
            em.close();
        }


    }

    public PermissionEntity getPermission() {
        return permission;
    }

    public void setPermission(PermissionEntity permission) {
        this.permission = permission;
    }
}
