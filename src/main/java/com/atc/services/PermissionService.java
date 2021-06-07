package com.atc.services;

import com.atc.entities.PermissionEntity;
import com.atc.utils.ValidationUtils;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

public class PermissionService extends ServiceImpl<PermissionEntity> {

    private final static Logger LOG = Logger.getLogger(PermissionService.class);

    public PermissionService(EntityManager em) {
        super(em);
    }


    @Override
    public boolean alreadyExist(PermissionEntity p) {
        return (findPermissionByLabelOrNull(p.getLabel()) != null);
    }

    @Override
    public PermissionEntity findOneByIdOrNull(int id) {
        LOG.debug("Select a permission by the id : " + id);
        return em.find(PermissionEntity.class, id);
    }

    @Override
    public Collection<PermissionEntity> findAllOrNull() {
        try {
            TypedQuery<PermissionEntity> query = em.createNamedQuery("Permission.findAll", PermissionEntity.class);
            List<PermissionEntity> permissionList = query.getResultList();
            LOG.debug("List " + PermissionEntity.class.getSimpleName() + " size: " + permissionList.size());
            LOG.debug("Selected all permissions from database ");
            return permissionList;
        } catch (NoResultException e) {
            LOG.debug("The query found no permission to return", e);
            return null;
        }
    }

    public PermissionEntity findPermissionByLabelOrNull(String label) throws IllegalArgumentException {
        LOG.debug("Finding permission of label " + label);

        if (ValidationUtils.hasContent(label)) {
            try {
                return em.createNamedQuery("Permission.findOneByLabel", PermissionEntity.class)
                        .setParameter("label", label)
                        .getSingleResult();
            } catch (NoResultException e) {
                LOG.debug("The query found no permission to return", e);
                return null;
            }
        } else {
            throw new IllegalArgumentException("Can't query permission in database: the label is null or empty");
        }
    }
}
