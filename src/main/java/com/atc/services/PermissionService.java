package com.atc.services;

import com.atc.entities.PermissionEntity;
import com.atc.utils.ValidationUtils;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class PermissionService implements Service<PermissionEntity> {

    private final static Logger LOG = Logger.getLogger(PermissionService.class);


    public boolean exist(PermissionEntity p, EntityManager em) {
        return (findPermissionByLabelOrNull(p.getLabel(), em) != null);
    }


    public PermissionEntity findOneByIdOrNull(int id, EntityManager em) {
        LOG.info("Select a permission by the id : " + id);
        return em.find(PermissionEntity.class, id);
    }

    public List<PermissionEntity> findAllOrNull(EntityManager em) {
        try {
            LOG.info("Create named query Permission.findAll");
            TypedQuery<PermissionEntity> query = em.createNamedQuery("Permission.findAll", PermissionEntity.class);
            List<PermissionEntity> permissionList = query.getResultList();
            LOG.info("List " + PermissionEntity.class.getSimpleName() + " size: " + permissionList.size());
            LOG.info("Selected all permissions from database ");
            return permissionList;
        } catch (Exception e) {
            LOG.info("The query found no permission to return", e);
            return null;
        }
    }

    public PermissionEntity findPermissionByLabelOrNull(String label, EntityManager em) throws IllegalArgumentException {
        LOG.info("Finding permission of label " + label);

        if (ValidationUtils.hasContent(label)) {
            try {
                return em.createNamedQuery("Permission.findOneByLabel", PermissionEntity.class)
                        .setParameter("label", label)
                        .getSingleResult();
            } catch (NoResultException e) {
                LOG.info("The query found no permission to return", e);
                return null;
            }
        } else {
            throw new IllegalArgumentException("Can't query permission in database: the label is null or empty");
        }
    }
}
