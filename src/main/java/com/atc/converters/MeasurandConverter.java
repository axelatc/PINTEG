package com.atc.converters;

import com.atc.persistence.JpaUtils;
import com.atc.persistence.entities.MeasurandEntity;
import com.atc.services.MeasurandService;
import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * @author axel
 */
@Named
@RequestScoped
public class MeasurandConverter implements Converter {

    private final static Logger LOG = Logger.getLogger(MeasurandConverter.class);

    @Inject
    private MeasurandService measurandService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            LOG.info(String.format("getAsObject(): string value [%s] is null or empty", value));
            return null;
        }

        EntityManager em = JpaUtils.createEntityManager();
        try {
            Integer id = Integer.valueOf(value);
            MeasurandEntity measurandEntity = measurandService.findOneByIdOrNull(id, em);
            LOG.info("getAsObject(): measurand entity found from String value is : " + measurandEntity.toString());
            return measurandEntity;
        } catch (NumberFormatException e) {
            LOG.info(String.format("getAsObject(): the string value [%s] is not a valid Measurand ID: ",value));
            throw new ConverterException(String.format("getAsObject() : the value [%s] is not a valid Measurand ID", value), e);
        } finally {
            em.clear();
            em.close();
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof MeasurandEntity) {
            Integer id = ((MeasurandEntity) value).getId();
            return (id != null) ? String.valueOf(id) : null;
        } else {
            throw new ConverterException("The value is not a valid Measurand instance: " + value);
        }
    }
}
