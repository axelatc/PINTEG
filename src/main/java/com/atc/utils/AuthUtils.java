package com.atc.utils;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;

/**
 * @author axel
 */
public class AuthUtils {
    private final static Logger LOG = Logger.getLogger(AuthUtils.class);


    /**
     * @param password the password in clear text
     * @return the encrypted password
     */
    public final static String hashAndSalt(String password) {
        PasswordService passwordService = new DefaultPasswordService();
        return passwordService.encryptPassword(password);
    }
}
