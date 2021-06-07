package com.atc.utils;

import org.apache.log4j.Logger;

public class ValidationUtils {

    private static final Logger LOG = Logger.getLogger(ValidationUtils.class);

    public static boolean hasContent(String string) {

        LOG.debug("Checking if the string has content : " + string);
        return (string != null && string.trim().length() > 0);
    }

    public static boolean isEntityId(String id) {
        LOG.debug("Checking if a string can be parsed as a valid DB id integer : " + id);
        if (hasContent(id)){
            try {
                int idAsInt = Integer.parseInt(id);
                return idAsInt > 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        else {
            return false;
        }

    }
}
