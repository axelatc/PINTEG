package com.atc.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class for internalization in class and bean...
 */
public class JsfUtils {

    public static String returnMessage(Locale locale, String message) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                "i18n.messages", locale);
        return resourceBundle.getString(message);
    }
}
