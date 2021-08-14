package com.atc.services;

import com.atc.persistence.JpaUtils;
import com.atc.persistence.entities.MeasurandEntity;
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
public class MeasurandService extends ServiceImpl<MeasurandEntity> {

    private final static Logger LOG = Logger.getLogger(MeasurandService.class);

    public boolean exist(MeasurandEntity p, EntityManager em) {
        return (findMeasurandByNameOrNull(p.getName()) != null);
    }


    public MeasurandEntity findOneByIdOrNull(int id, EntityManager em) {
        LOG.info("Select a measurand by the id : " + id);
        return em.find(MeasurandEntity.class, id);
    }

    public List<MeasurandEntity> findAllOrNull(EntityManager em) {
        try {
            LOG.info("Create named query Measurand.findAll");
            TypedQuery<MeasurandEntity> query = em.createNamedQuery("Measurand.findAll", MeasurandEntity.class);
            List<MeasurandEntity> measurandList = query.getResultList();
            LOG.info("List " + MeasurandEntity.class.getSimpleName() + " size: " + measurandList.size());
            LOG.info("Selected all measurands from database ");
            return measurandList;
        } catch (Exception e) {
            LOG.info("The query found no measurand to return", e);
            return null;
        }
    }

    public MeasurandEntity findMeasurandByNameOrNull(String name) throws IllegalArgumentException {
        LOG.info("Finding measurand of name " + name);

        EntityManager em = JpaUtils.createEntityManager();
        if (ValidationUtils.hasContent(name)) {
            try {
                return em.createNamedQuery("Measurand.findOneByName", MeasurandEntity.class)
                        .setParameter("name", name)
                        .getSingleResult();
            } catch (NoResultException e) {
                LOG.info("The query found no measurand to return", e);
                return null;
            } finally {
                em.close();
            }
        } else {
            em.clear();
            em.close();
            throw new IllegalArgumentException("Can't query measurand in database: the name is null or empty");
        }
    }
}

