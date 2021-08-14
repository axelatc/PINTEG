package com.atc.backingBeans.measurands;

import com.atc.persistence.JpaUtils;
import com.atc.persistence.entities.MeasurandEntity;
import com.atc.persistence.entities.UnitEntity;
import com.atc.services.MeasurandService;
import com.atc.services.UnitService;
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
@SessionScoped
@Named
public class MeasurandCreate implements Serializable {

    private final static Logger LOG = Logger.getLogger(MeasurandCreate.class);
    private final static String SUCCESS_LOCALE_MESSAGE_NAME = "measurandCreate.successMessage";
    private final static String FAILURE_LOCALE_MESSAGE_NAME = "measurandCreate.failureMessage";

    @Inject
    private MeasurandEntity measurand;
    @Inject
    private MeasurandService measurandService;
    @Inject
    private UnitService unitService;
    private List<UnitEntity> units;

    @PostConstruct
    public void init() {
        EntityManager em = createEntityManager();
        units = unitService.findAllOrNull(em);
        em.close();
    }

    public String save() {

        LOG.info("Attempting to persist new measurand...");
        EntityManager em = JpaUtils.createEntityManager();
        EntityTransaction tx = null;
        try {

            boolean alreadyExist = measurandService.exist(measurand, em);
            if(alreadyExist) {
                throw new RuntimeException("Can't create measurand in data storage: it already exists");
            }

            tx = em.getTransaction();
            LOG.info("Begin transaction to persist new measurand...");
            tx.begin();
            MeasurandEntity measurandToInsert = new MeasurandEntity(measurand.getName(), measurand.getDescription(), measurand.getUnitsByUnitId());
            LOG.info("Attempting to insert new measurand in data storage...");
            measurandService.insert(measurandToInsert, em);
            tx.commit();
            LOG.info("Transaction committed. New measurand is persisted successfully.");

            addSuccessMessage(getLocaleMessageAsString(SUCCESS_LOCALE_MESSAGE_NAME));

            return "success";
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOG.info("Creation of measurand entity has failed.", ex);
            addErrorMessage(getLocaleMessageAsString(FAILURE_LOCALE_MESSAGE_NAME));
            return "failure";
        } finally {
            em.clear();
            em.close();
        }


    }

    public MeasurandEntity getMeasurand() {
        return measurand;
    }

    public void setMeasurand(MeasurandEntity measurand) {
        this.measurand = measurand;
    }

    public List<UnitEntity> getUnits() {
        units = unitService.findAllOrNull(JpaUtils.createEntityManager());
        return units;
    }

    public void setUnits(List<UnitEntity> units) {
        this.units = units;
    }
}

