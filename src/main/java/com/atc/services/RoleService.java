package com.atc.services;

import com.atc.persistence.entities.RoleEntity;
import com.atc.persistence.JpaUtils;
import com.atc.utils.ValidationUtils;
import org.apache.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author axel
 */
@ApplicationScoped
public class RoleService extends ServiceImpl<RoleEntity> {

    private final static Logger LOG = Logger.getLogger(RoleService.class);
    private final static String NEWLY_REGISTERED_USER_ROLE_LABEL = "user_bronze";

    public boolean exist(RoleEntity p, EntityManager em) {
        return (findRoleByLabelOrNull(p.getLabel()) != null);
    }


    public RoleEntity findOneByIdOrNull(int id, EntityManager em) {
        LOG.info("Select a role by the id : " + id);
        return em.find(RoleEntity.class, id);
    }

    public List<RoleEntity> findAllOrNull(EntityManager em) {
        try {
            LOG.info("Create named query Role.findAll");
            TypedQuery<RoleEntity> query = em.createNamedQuery("Role.findAll", RoleEntity.class);
            List<RoleEntity> roleList = query.getResultList();
            LOG.info("List " + RoleEntity.class.getSimpleName() + " size: " + roleList.size());
            LOG.info("Selected all roles from database ");
            return roleList;
        } catch (Exception e) {
            LOG.info("The query found no role to return", e);
            return null;
        }
    }

    public RoleEntity findRoleByLabelOrNull(String label) throws IllegalArgumentException {
        LOG.info("Finding role of label " + label);

        EntityManager em = JpaUtils.createEntityManager();
        if (ValidationUtils.hasContent(label)) {
            try {
                return em.createNamedQuery("Role.findOneByLabel", RoleEntity.class)
                        .setParameter("label", label)
                        .getSingleResult();
            } catch (NoResultException e) {
                LOG.info("The query found no role to return", e);
                return null;
            } finally {
                em.close();
            }
        } else {
            em.clear();
            em.close();
            throw new IllegalArgumentException("Can't query role in database: the label is null or empty");
        }
    }

    public RoleEntity findRoleForNewlyRegisteredUserOrNull() {
        LOG.info("Finding the role appropriate for a newly registered user by its label: " + NEWLY_REGISTERED_USER_ROLE_LABEL);
        return findRoleByLabelOrNull(NEWLY_REGISTERED_USER_ROLE_LABEL);
    }
}
