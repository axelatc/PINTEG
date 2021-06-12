package com.atc.backingBeans.auth;

import com.atc.entities.UserEntity;
import com.atc.services.UserService;
import org.apache.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

import static com.atc.utils.AuthUtils.hashAndSalt;
import static com.atc.utils.JpaUtils.createEntityManager;

@Named
@SessionScoped
public class AuthenticationBean implements Serializable {
    private final static Logger LOG = Logger.getLogger(AuthenticationBean.class);

    private UserEntity loggedUser = null;
    private final UserService userService = new UserService();

    public String validateUserCredentials(LoginBean userCredentials) {
        LOG.info("Login attempt...");

        LOG.info("Trying to validate username :" + userCredentials.getUsername());
        EntityManager em = createEntityManager();
        UserEntity foundUser = userService.findUserByUsernameOrNull(userCredentials.getUsername(), em);
        em.close();
        if (foundUser == null || !foundUser.isActive()) {
            LOG.info("Login failure: username [" + userCredentials.getUsername() + "] returns no user or user is deactivated.");
            return "failure";
        }

        LOG.info("Trying to validate password :" + userCredentials.getPassword());
        String hashedAndSaltedInputPassword = hashAndSalt(userCredentials.getPassword());
        boolean inputPasswordIsValid = foundUser.getPassword().equals(hashedAndSaltedInputPassword);
        if(!inputPasswordIsValid) {
            LOG.info("Login failure. Passwords don't match." + "Expected: " + foundUser.getPassword() + " Received: " + hashedAndSaltedInputPassword);
            return "failure";
        }

        LOG.info("Setting logged user as a Session attribute.");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session =
                (HttpSession)context.getExternalContext().getSession(true);
        session.setAttribute("loggedUser", foundUser);

        LOG.info("Login successful!");
        return "success";

    }

    public boolean isAuthenticated() {
        return this.getLoggedUser() != null;
    }

    public UserEntity getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(UserEntity loggedUser) {
        this.loggedUser = loggedUser;

    }

}
