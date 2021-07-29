package com.atc.backingBeans.auth;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

import static com.atc.utils.JsfUtils.*;

@Named
@SessionScoped
public class LoginBean implements Serializable {
    private final static Logger LOG = Logger.getLogger(LoginBean.class);
    private final static String SUCCESS_LOCALE_MESSAGE_NAME = "login.success";
    private final static String GENERIC_FAILURE_LOCALE_MESSAGE_NAME = "login.genericFailure";
    private final static String INCORRECT_CREDENTIALS_LOCALE_MESSAGE_NAME = "login.incorrectCredentials";
    private final static String UNKNOWN_ACCOUNT_LOCALE_MESSAGE_NAME = "login.unknownAccount";

    private String username;
    private String password;

    @PostConstruct
    public void init() {
        this.username = "";
        this.password = "";
    }

    public String submit() throws IOException {

        try {
            LOG.info("Attempting to log in client...");
            UsernamePasswordToken token = new UsernamePasswordToken(getUsername(), getPassword());
            SecurityUtils.getSubject().login(token);

            //TODO set current user on Session
            addSuccessMessage(getLocaleMessageAsString(SUCCESS_LOCALE_MESSAGE_NAME));
            return "success";

        } catch (UnknownAccountException ex) {
            LOG.info("Login attempt has failed. Unknown account", ex);
            addErrorMessage(getLocaleMessageAsString(UNKNOWN_ACCOUNT_LOCALE_MESSAGE_NAME));
            return "failure";

        } catch (IncorrectCredentialsException ex) {
            LOG.info("Login attempt has failed. Incorrect credentials", ex);
            addErrorMessage(getLocaleMessageAsString(INCORRECT_CREDENTIALS_LOCALE_MESSAGE_NAME));
            return "failure";

        } catch (Exception ex) {
            LOG.info("Login attempt has failed.", ex);
            addErrorMessage(getLocaleMessageAsString(GENERIC_FAILURE_LOCALE_MESSAGE_NAME));
            return "failure";
        } finally {

        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
