package com.atc.converters;

import com.atc.persistence.JpaUtils;
import com.atc.persistence.entities.UnitEntity;
import com.atc.services.UnitService;

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
public class UnitConverter implements Converter {


    @Inject
    private UnitService unitService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        EntityManager em = JpaUtils.createEntityManager();
        try {
            Integer id = Integer.valueOf(value);
            UnitEntity unitEntity = unitService.findOneByIdOrNull(id, em);
            return unitEntity;
        } catch (NumberFormatException e) {
            throw new ConverterException("The value is not a valid Unit ID: " + value, e);
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

        if (value instanceof UnitEntity) {
            Integer id = ((UnitEntity) value).getId();
            return (id != null) ? String.valueOf(id) : null;
        } else {
            throw new ConverterException("The value is not a valid Unit instance: " + value);
        }
    }
}
