package com.atc.services;

import com.atc.persistence.entities.UnitEntity;
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
public class UnitService extends ServiceImpl<UnitEntity> {

    private final static Logger LOG = Logger.getLogger(UnitService.class);

    public boolean exist(UnitEntity p, EntityManager em) {
        return (findUnitByNameOrNull(p.getName()) != null);
    }


    public UnitEntity findOneByIdOrNull(int id, EntityManager em) {
        LOG.info("Select a unit by the id : " + id);
        return em.find(UnitEntity.class, id);
    }

    public List<UnitEntity> findAllOrNull(EntityManager em) {
        try {
            LOG.info("Create named query Unit.findAll");
            TypedQuery<UnitEntity> query = em.createNamedQuery("Unit.findAll", UnitEntity.class);
            List<UnitEntity> unitList = query.getResultList();
            LOG.info("List " + UnitEntity.class.getSimpleName() + " size: " + unitList.size());
            LOG.info("Selected all units from database ");
            return unitList;
        } catch (Exception e) {
            LOG.info("The query found no unit to return", e);
            return null;
        }
    }

    public UnitEntity findUnitByNameOrNull(String name) throws IllegalArgumentException {
        LOG.info("Finding unit of name " + name);

        EntityManager em = JpaUtils.createEntityManager();
        if (ValidationUtils.hasContent(name)) {
            try {
                return em.createNamedQuery("Unit.findOneByName", UnitEntity.class)
                        .setParameter("name", name)
                        .getSingleResult();
            } catch (NoResultException e) {
                LOG.info("The query found no unit to return", e);
                return null;
            } finally {
                em.close();
            }
        } else {
            em.clear();
            em.close();
            throw new IllegalArgumentException("Can't query unit in database: the name is null or empty");
        }
    }
}

