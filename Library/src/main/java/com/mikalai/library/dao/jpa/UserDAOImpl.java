package com.mikalai.library.dao.jpa;

import com.mikalai.library.beans.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Created by mikalai on 24.04.2016.
 */
public class UserDAOImpl extends GenericDAOImpl<User, Long> implements UserDAOI {
    private static final Logger LOG = LogManager.getLogger();

    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public User getUser(String login, String password) {
        TypedQuery<User> query = em.createNamedQuery("User.login", User.class);
        query.setParameter("login", login);
        query.setParameter("password", password);

        User result = null;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            LOG.warn("Duplicate user", e);
        }
        return result;

    }

    @Override
    public User getUser(String login) {
        TypedQuery<User> query = em.createNamedQuery("User.byLogin", User.class);
        query.setParameter("login", login);

        User result = null;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            LOG.warn("no user");
        }
        return result;
    }

}
