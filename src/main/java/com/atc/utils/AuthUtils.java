package com.atc.utils;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.env.BasicIniEnvironment;
import org.apache.shiro.env.Environment;
import org.apache.shiro.mgt.SecurityManager;

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

    /**
     * @return the Security Manager configured by the shiro.ini
     */
    public static SecurityManager getSecurityManager() {
        //source: https://stackoverflow.com/a/60139962
        Environment env = new BasicIniEnvironment("classpath:shiro.ini");
        SecurityManager securityManager = env.getSecurityManager();
        //SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }
}
