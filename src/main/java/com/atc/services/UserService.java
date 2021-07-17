package com.atc.services;

import com.atc.entities.UserEntity;
import com.atc.utils.ValidationUtils;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserService extends ServiceImpl<UserEntity> {

    private final static Logger LOG = Logger.getLogger(UserService.class);

    public boolean exist(UserEntity u, EntityManager em) {
        return (findUserByUsernameOrNull(u.getUsername(), em) != null);
    }

    public UserEntity findOneByIdOrNull(int id, EntityManager em) {
        LOG.info("Select a user by the id : " + id);
        return em.find(UserEntity.class, id);
    }

    public List<UserEntity> findAllOrNull(EntityManager em) {
        try {
            LOG.info("Create named query User.findAll");
            TypedQuery<UserEntity> query = em.createNamedQuery("User.findAll", UserEntity.class);
            List<UserEntity> userList = query.getResultList();
            LOG.info("List " + UserEntity.class.getSimpleName() + " size: " + userList.size());
            LOG.info("Selected all users from database ");
            return userList;
        } catch (Exception e) {
            LOG.info("The query found no user to return", e);
            return null;
        }
    }

    public UserEntity findUserByUsernameOrNull(String username, EntityManager em) throws IllegalArgumentException {
        LOG.info("Finding user of username " + username);

        if (ValidationUtils.hasContent(username)) {
            try {
                return em.createNamedQuery("User.findOneByUsername", UserEntity.class)
                        .setParameter("username", username)
                        .getSingleResult();
            } catch (NoResultException e) {
                LOG.info("The query found no user to return", e);
                return null;
            }
        } else {
            throw new IllegalArgumentException("Can't query user in database: the username is null or empty");
        }
    }
}
