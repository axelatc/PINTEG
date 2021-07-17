package com.atc.converters;

import com.atc.entities.PermissionEntity;
import com.atc.services.PermissionService;
import com.atc.utils.JpaUtils;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@RequestScoped
public class PermissionConverter implements Converter {


    private PermissionService permissionService = new PermissionService();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        EntityManager em = JpaUtils.createEntityManager();
        try {
            Integer id = Integer.valueOf(value);
            PermissionEntity permissionEntity = permissionService.findOneByIdOrNull(id, em);
            return permissionEntity;
        } catch (NumberFormatException e) {
            throw new ConverterException("The value is not a valid Permission ID: " + value, e);
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

        if (value instanceof PermissionEntity) {
            Integer id = ((PermissionEntity) value).getId();
            return (id != null) ? String.valueOf(id) : null;
        } else {
            throw new ConverterException("The value is not a valid Permission instance: " + value);
        }
    }
}
