package com.atc.converters;

import com.atc.persistence.JpaUtils;
import com.atc.persistence.entities.SubscriptionEntity;
import com.atc.services.SubscriptionService;
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
public class SubscriptionConverter implements Converter {

    private final static Logger LOG = Logger.getLogger(SubscriptionConverter.class);

    @Inject
    private SubscriptionService subscriptionService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            LOG.info(String.format("getAsObject(): string value [%s] is null or empty", value));
            return null;
        }

        EntityManager em = JpaUtils.createEntityManager();
        try {
            Integer id = Integer.valueOf(value);
            SubscriptionEntity subscriptionEntity = subscriptionService.findOneByIdOrNull(id, em);
            LOG.info("getAsObject(): subscription entity found from String value is : " + subscriptionEntity.toString());
            return subscriptionEntity;
        } catch (NumberFormatException e) {
            LOG.info(String.format("getAsObject(): the string value [%s] is not a valid Subscription ID: ", value));
            throw new ConverterException(String.format("getAsObject() : the value [%s] is not a valid Subscription ID", value), e);
        } catch (Exception e) {
            LOG.info(String.format("getAsObject(): no subscription entity was found in the data store for Subscription ID [%s]", value), e);
            throw new ConverterException(String.format("getAsObject(): no subscription entity was found in the data store for Subscription ID [%s]", value), e);
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

        if (value instanceof SubscriptionEntity) {
            Integer id = ((SubscriptionEntity) value).getId();
            return (id != null) ? String.valueOf(id) : null;
        } else {
            throw new ConverterException("The value is not a valid Subscription instance: " + value);
        }
    }
}
