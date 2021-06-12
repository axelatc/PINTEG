package com.atc.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Locale;
import java.util.ResourceBundle;

public class JsfUtils {
    private static final String BUNDLE_BASE_NAME = "com.atc.i18n.messages";

    public static String getLocaleMessageAsString(String message) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                BUNDLE_BASE_NAME, locale);
        return resourceBundle.getString(message);
    }

    public static void addSuccessMessage(String errorMessage) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, errorMessage, null));
    }

    public static void addErrorMessage(String errorMessage) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, errorMessage, null));
    }
}
