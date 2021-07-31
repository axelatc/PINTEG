package com.atc.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author axel
 */
public class JsfUtils {
    private static final String BUNDLE_BASE_NAME = "com.atc.i18n.messages";

    public static String getLocaleMessageAsString(String message) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                BUNDLE_BASE_NAME, locale);
        return resourceBundle.getString(message);
    }

    public static void addSuccessMessage(String successMessage) {
        addMessageWithSeverity(successMessage, FacesMessage.SEVERITY_INFO);
    }

    public static void addErrorMessage(String errorMessage) {
        addMessageWithSeverity(errorMessage, FacesMessage.SEVERITY_ERROR);
    }

    private static void addMessageWithSeverity(String message, FacesMessage.Severity severity) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage successMessageToAdd = new FacesMessage(severity, message, null);
        context.addMessage(null, successMessageToAdd);
        context.getExternalContext().getFlash().setKeepMessages(true);
    }
}
