package com.atc.backingBeans.permissions;

import com.atc.entities.PermissionEntity;
import com.atc.services.PermissionService;
import com.atc.utils.JpaUtils;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;

import static com.atc.utils.JpaUtils.createEntityManager;
import static com.atc.utils.JsfUtils.*;

@Named
@SessionScoped
public class PermissionList implements Serializable {

    private final static Logger LOG = Logger.getLogger(PermissionService.class);
    private final static String SUCCESS_LOCALE_MESSAGE_NAME = "permissionDelete.successMessage";
    private final static String FAILURE_LOCALE_MESSAGE_NAME = "permissionDelete.failureMessage";

    private final PermissionService permissionService = new PermissionService();
    private List<PermissionEntity> permissions;

    @PostConstruct
    public void init() {
        EntityManager em = createEntityManager();
        permissions = permissionService.findAllOrNull(em);
        em.close();
    }

    public String delete(PermissionEntity permissionToDelete) {
        EntityManager em = createEntityManager();

        EntityTransaction tx = null;
        try {
            PermissionEntity foundPermission = permissionService.findOneByIdOrNull(permissionToDelete.getId(), em);
            if(foundPermission == null) {
                throw new RuntimeException("Can't delete permission in data store: it doesn't exists");
            }

            tx = em.getTransaction();
            tx.begin();
            permissionService.delete(foundPermission, em);
            tx.commit();
            LOG.info("Permission entity is removed.");
            addSuccessMessage(getLocaleMessageAsString(SUCCESS_LOCALE_MESSAGE_NAME));
            return "success";
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOG.info("Deletion of permission entity has failed.", ex);
            addErrorMessage(getLocaleMessageAsString(FAILURE_LOCALE_MESSAGE_NAME));
            return "failure";
        } finally {
            em.clear();
            em.close();
        }
    }

    public List<PermissionEntity> getPermissions() {
        permissions = permissionService.findAllOrNull(JpaUtils.createEntityManager());
        return permissions;
    }
    public void setPermissions(List<PermissionEntity> permissions) {
        this.permissions = permissions;
    }
}
