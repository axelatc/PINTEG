package com.atc.utils;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.subject.Subject;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


/**
 * @author axel
 */
@RequestScoped
@Named
public class AuthUtils {
    private final static Logger LOG = Logger.getLogger(AuthUtils.class);

    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }
    public String getUsername() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * @param password the password in clear text
     * @return the encrypted password
     */
    public final static String hashAndSalt(String password) {
        PasswordService passwordService = new DefaultPasswordService();
        return passwordService.encryptPassword(password);
    }
}
