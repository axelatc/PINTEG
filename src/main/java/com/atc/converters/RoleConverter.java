package com.atc.converters;

import com.atc.entities.RoleEntity;
import com.atc.services.RoleService;
import com.atc.utils.JpaUtils;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * @author axel
 */
@Named
@RequestScoped
public class RoleConverter implements Converter {


    private RoleService roleService = new RoleService();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        EntityManager em = JpaUtils.createEntityManager();
        try {
            Integer id = Integer.valueOf(value);
            RoleEntity roleEntity = roleService.findOneByIdOrNull(id, em);
            return roleEntity;
        } catch (NumberFormatException e) {
            throw new ConverterException("The value is not a valid Role ID: " + value, e);
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

        if (value instanceof RoleEntity) {
            Integer id = ((RoleEntity) value).getId();
            return (id != null) ? String.valueOf(id) : null;
        } else {
            throw new ConverterException("The value is not a valid Role instance: " + value);
        }
    }
}
