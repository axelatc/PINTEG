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
@Named
@SessionScoped
public class MeasurandEdit implements Serializable {

    private final static Logger LOG = Logger.getLogger(MeasurandEdit.class);
    private final static String SUCCESS_LOCALE_MESSAGE_NAME = "measurandEdit.successMessage";
    private final static String FAILURE_LOCALE_MESSAGE_NAME = "measurandEdit.failureMessage";

    private MeasurandEntity measurand;
    @Inject
    private MeasurandService measurandService;

    @Inject
    private UnitService unitService;
    private List<UnitEntity> units;

    @PostConstruct
    public void init() {
        measurand = new MeasurandEntity();
        EntityManager em = createEntityManager();
        units = unitService.findAllOrNull(em);
        em.close();
    }

    public String save() {

        EntityManager em = JpaUtils.createEntityManager();
        EntityTransaction tx = null;
        try {

            MeasurandEntity foundMeasurand = measurandService.findOneByIdOrNull(this.measurand.getId(), em);
            if(foundMeasurand == null) {
                throw new RuntimeException("Can't update measurand in data store: it doesn't exist");
            }
            foundMeasurand.setName(measurand.getName());
            foundMeasurand.setDescription(measurand.getDescription());
            foundMeasurand.setUnitsByUnitId(measurand.getUnitsByUnitId());

            tx = em.getTransaction();
            tx.begin();
            measurandService.update(foundMeasurand, em);
            tx.commit();
            LOG.info("Measurand entity is updated.");

            addSuccessMessage(getLocaleMessageAsString(SUCCESS_LOCALE_MESSAGE_NAME));

            return "success";
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            LOG.info("Updating of measurand entity has failed.", ex);
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
        return units;
    }

    public void setUnits(List<UnitEntity> units) {
        this.units = units;
    }
}
