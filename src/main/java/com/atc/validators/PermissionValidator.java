/* That's not the right way to use Jsf Validator, I'll use javax.annotations instead
package com.atc.validators;

import com.atc.persistence.entities.PermissionEntity;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import static com.atc.utils.JsfUtils.getLocaleMessageAsString;
import static com.atc.utils.ValidationUtils.hasContent;

@FacesValidator("permissionValidator")
public class PermissionValidator implements Validator {
    private final static Logger LOG = Logger.getLogger(PermissionValidator.class);
    private final static int LABEL_MIN_LENGTH = 1;
    private final static int LABEL_MAX_LENGTH = 50;
    private final static int DESCRIPTION_MIN_LENGTH = 1;
    private final static int DESCRIPTION_MAX_LENGTH = 2000;
    private final static String BLANK_LABEL_LOCALE_MESSAGE_NAME = "permission.label.requiredMessage";
    private final static String LENGTH_LIMIT_LABEL_LOCALE_MESSAGE_NAME = "permission.label.validationMessage";
    private final static String BLANK_DESCRIPTION_LOCALE_MESSAGE_NAME = "permission.description.requiredMessage";
    private final static String LENGTH_LIMIT_DESCRIPTION_LOCALE_MESSAGE_NAME = "permission.description.validationMessage";

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {

        PermissionEntity permission = (PermissionEntity) value;
        validateLabel(permission);
        validateDescription(permission);
    }

    private void validateLabel(PermissionEntity permission) {
        if(!hasContent(permission.getLabel())) {
            throw new ValidatorException(new FacesMessage(getLocaleMessageAsString(BLANK_LABEL_LOCALE_MESSAGE_NAME)));
        }
        int labelLength = permission.getLabel().length();
        if(labelLength < LABEL_MIN_LENGTH || labelLength > LABEL_MAX_LENGTH) {
            throw new ValidatorException(new FacesMessage(getLocaleMessageAsString(LENGTH_LIMIT_LABEL_LOCALE_MESSAGE_NAME)));
        }
    }
    private void validateDescription(PermissionEntity permission) {
        if(!hasContent(permission.getDescription())) {
            throw new ValidatorException(new FacesMessage(getLocaleMessageAsString(BLANK_DESCRIPTION_LOCALE_MESSAGE_NAME)));
        }
        int descriptionLength = permission.getDescription().length();
        if(descriptionLength < DESCRIPTION_MIN_LENGTH || descriptionLength > DESCRIPTION_MAX_LENGTH) {
            throw new ValidatorException(new FacesMessage(getLocaleMessageAsString(LENGTH_LIMIT_DESCRIPTION_LOCALE_MESSAGE_NAME)));
        }
    }
}

*/
