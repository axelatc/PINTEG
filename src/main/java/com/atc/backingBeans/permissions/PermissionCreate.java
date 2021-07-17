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
public class PermissionCreate implements Serializable {

    private final static Logger LOG = Logger.getLogger(PermissionCreate.class);
    private final static String SUCCESS_LOCALE_MESSAGE_NAME = "permissionCreate.successMessage";
    private final static String FAILURE_LOCALE_MESSAGE_NAME = "permissionCreate.failureMessage";

    @Inject
    private PermissionEntity permission;

    private final PermissionService permissionService = new PermissionService();

    public String save() {

        EntityManager em = JpaUtils.createEntityManager();
        EntityTransaction tx = null;
        try {

            boolean alreadyExist = permissionService.exist(permission, em);
            if(alreadyExist) {
                throw new RuntimeException("Can't create permission in data store: it already exists");
            }

            tx = em.getTransaction();
            tx.begin();
            PermissionEntity permissionToInsert = new PermissionEntity(permission.getLabel(), permission.getDescription());
            permissionService.insert(permissionToInsert, em);
            tx.commit();
            LOG.info("Permission entity is created.");

            addSuccessMessage(getLocaleMessageAsString(SUCCESS_LOCALE_MESSAGE_NAME));

            return "success";
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOG.info("Creation of permission entity has failed.", ex);
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

