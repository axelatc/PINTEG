package com.atc.utils;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.env.BasicIniEnvironment;
import org.apache.shiro.env.Environment;
import org.apache.shiro.mgt.SecurityManager;

public class AuthUtils {
    private final static Logger LOG = Logger.getLogger(AuthUtils.class);

    public final static String hashAndSalt(String password) {
        PasswordService passwordService = new DefaultPasswordService();
        return passwordService.encryptPassword(password);
    }
}
