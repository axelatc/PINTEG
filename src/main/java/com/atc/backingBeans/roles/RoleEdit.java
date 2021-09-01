package com.atc.backingBeans.roles;

import com.atc.persistence.JpaUtils;
import com.atc.persistence.entities.PermissionEntity;
import com.atc.persistence.entities.RoleEntity;
import com.atc.persistence.entities.RolePermissionEntity;
import com.atc.services.PermissionService;
import com.atc.services.RoleService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.atc.persistence.JpaUtils.createEntityManager;
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

    private RoleEntity role;
    @Inject
    private RoleService roleService;



    private List<Integer> selectedPermissions;
    private List<SelectItem> availablePermissions;
    private List<PermissionEntity> permissions;
    @Inject
    private PermissionService permissionService;

    @PostConstruct
    public void init() {

    }

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

    public List<Integer> getSelectedPermissions() {
        EntityManager em = createEntityManager();
        // Obtain all the permissions assigned to the role and populate selectedPermissions with them
        LOG.info("role ID:" + role.getId());
        RoleEntity foundRole = roleService.findOneByIdOrNull(this.role.getId(), em);
        selectedPermissions = new ArrayList<>();
        LOG.info("Found role :" + foundRole.toString());
        List<RolePermissionEntity> rolePermissionEntities = (List<RolePermissionEntity>) foundRole.getRolesPermissionsById();
        for (RolePermissionEntity rolePermissionEntity : rolePermissionEntities) {
            PermissionEntity permissionEntity = rolePermissionEntity.getPermissionsByPermissionId();
            selectedPermissions.add(permissionEntity.getId());
        }
        em.close();
        return selectedPermissions;
    }

    public void setSelectedPermissions(List<Integer> selectedPermissions) {
        this.selectedPermissions = selectedPermissions;
    }

    public List<SelectItem> getAvailablePermissions() {
        // Obtain all the permissions and populate availablePermissions with them
        EntityManager em = createEntityManager();
        permissions = permissionService.findAllOrNull(em);
        availablePermissions = new ArrayList<>();
        for (PermissionEntity permission : permissions) {
            SelectItem selectItem = new SelectItem(permission.getId(), permission.getLabel(), permission.getDescription(), false);
            availablePermissions.add(selectItem);
        }
        em.close();
        return availablePermissions;
    }
}
