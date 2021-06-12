package com.atc.backingBeans;

import org.apache.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

@Named
@SessionScoped
public class InternationalizationBean implements Serializable {

    public static Logger log = Logger.getLogger(InternationalizationBean.class);

    public void languageSelection(ActionEvent actionEvent) {
        Locale locale;
        String idComponent = actionEvent.getComponent().getId();

        locale = new Locale(idComponent);

        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
