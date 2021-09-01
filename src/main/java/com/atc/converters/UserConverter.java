package com.atc.converters;

import com.atc.persistence.JpaUtils;
import com.atc.persistence.entities.UserEntity;
import com.atc.services.UserService;
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
public class UserConverter implements Converter {

    private final static Logger LOG = Logger.getLogger(UserConverter.class);

    @Inject
    private UserService userService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            LOG.info(String.format("getAsObject(): string value [%s] is null or empty", value));
            return null;
        }

        EntityManager em = JpaUtils.createEntityManager();
        try {
            Integer id = Integer.valueOf(value);
            UserEntity userEntity = userService.findOneByIdOrNull(id, em);
            LOG.info("getAsObject(): user entity found from String value is : " + userEntity.toString());
            return userEntity;
        } catch (NumberFormatException e) {
            LOG.info(String.format("getAsObject(): the string value [%s] is not a valid User ID: ", value));
            throw new ConverterException(String.format("getAsObject() : the value [%s] is not a valid User ID", value), e);
        } catch (Exception e) {
            LOG.info(String.format("getAsObject(): no user entity was found in the data store for User ID [%s]", value), e);
            throw new ConverterException(String.format("getAsObject(): no user entity was found in the data store for User ID [%s]", value), e);
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

        if (value instanceof UserEntity) {
            Integer id = ((UserEntity) value).getId();
            return (id != null) ? String.valueOf(id) : null;
        } else {
            throw new ConverterException("The value is not a valid User instance: " + value);
        }
    }
}
