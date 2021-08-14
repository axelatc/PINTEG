package com.atc.backingBeans.measurands;

import com.atc.persistence.JpaUtils;
import com.atc.persistence.entities.MeasurandEntity;
import com.atc.services.MeasurandService;
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
public class MeasurandList implements Serializable {

    private final static Logger LOG = Logger.getLogger(MeasurandList.class);
    private final static String SUCCESS_LOCALE_MESSAGE_NAME = "measurandDelete.successMessage";
    private final static String FAILURE_LOCALE_MESSAGE_NAME = "measurandDelete.failureMessage";

    @Inject
    private MeasurandService measurandService;
    private List<MeasurandEntity> measurands;

    @PostConstruct
    public void init() {
        EntityManager em = createEntityManager();
        measurands = measurandService.findAllOrNull(em);
        em.close();
    }

    public String delete(MeasurandEntity measurandToDelete) {
        EntityManager em = createEntityManager();

        EntityTransaction tx = null;
        try {
            MeasurandEntity foundMeasurand = measurandService.findOneByIdOrNull(measurandToDelete.getId(), em);
            if(foundMeasurand == null) {
                throw new RuntimeException("Can't delete measurand in data store: it doesn't exist");
            }

            tx = em.getTransaction();
            tx.begin();
            measurandService.delete(foundMeasurand, em);
            tx.commit();
            LOG.info("Measurand entity is removed.");
            addSuccessMessage(getLocaleMessageAsString(SUCCESS_LOCALE_MESSAGE_NAME));
            return "success";
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOG.info("Deletion of measurand entity has failed.", ex);
            addErrorMessage(getLocaleMessageAsString(FAILURE_LOCALE_MESSAGE_NAME));
            return "failure";
        } finally {
            em.clear();
            em.close();
        }
    }

    public List<MeasurandEntity> getMeasurands() {
        measurands = measurandService.findAllOrNull(JpaUtils.createEntityManager());
        return measurands;
    }
    public void setMeasurands(List<MeasurandEntity> measurands) {
        this.measurands = measurands;
    }
}
