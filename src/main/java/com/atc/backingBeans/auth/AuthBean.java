package com.atc.backingBeans.auth;

import com.atc.persistence.entities.UserEntity;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.subject.Subject;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Provide utilities related to authentification
 * @author axel
 */
@SessionScoped
@Named
public class AuthBean implements Serializable {
    private final static Logger LOG = Logger.getLogger(AuthBean.class);

    /**
     * @param password the password in clear text
     * @return the encrypted password
     */
    public static String hashAndSalt(String password) {
        PasswordService passwordService = new DefaultPasswordService();
        return passwordService.encryptPassword(password);
    }

    private UserEntity currentUser;

    /**
     * @return Apache Shiro's subject. In this context, the subject is a real user.
     */
    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * @return The username of the authenticated user.
     */
    public String getUsername() {
        return this.currentUser.getUsername();
    }

    /**
     * @return the currently authenticated user
     */
    public UserEntity getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser the currently authenticated user
     */
    public void setCurrentUser(UserEntity currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Log out the Shiro's Subject and set the current authenticated user to null
     */
    public void logout() {
        SecurityUtils.getSubject().logout();
        this.setCurrentUser(null);
    }
}
