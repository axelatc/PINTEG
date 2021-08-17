package com.atc.backingBeans.auth;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.subject.Subject;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
public class AuthBean implements Serializable {
    private final static Logger LOG = Logger.getLogger(AuthBean.class);

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static String getUsername() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * @param password the password in clear text
     * @return the encrypted password
     */
    public static String hashAndSalt(String password) {
        PasswordService passwordService = new DefaultPasswordService();
        return passwordService.encryptPassword(password);
    }
}