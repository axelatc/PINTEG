package com.atc.backingBeans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

/**
 * @author BalusC
 * @link https://stackoverflow.com/questions/4830588/localization-in-jsf-how-to-remember-selected-locale-per-session-instead-of-per
 * Backing bean for the Locale selection
 */
@Named
@SessionScoped
public class LocaleBean implements Serializable {

    private Locale locale;

    @PostConstruct
    public void init() {
        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

}
