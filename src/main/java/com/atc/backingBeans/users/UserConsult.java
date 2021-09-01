package com.atc.backingBeans.users;

import com.atc.persistence.entities.UserEntity;
import com.atc.services.UserService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author axel
 */
@Named
@SessionScoped
public class UserConsult implements Serializable {

    private final static Logger LOG = Logger.getLogger(UserConsult.class);

    private UserEntity user;
    @Inject
    private UserService userService;

    @PostConstruct
    public void init() {
        user = new UserEntity();
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
