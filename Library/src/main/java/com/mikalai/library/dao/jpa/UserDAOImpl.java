package com.mikalai.library.dao.jpa;

import com.mikalai.library.beans.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery criteria = cb.createQuery();
        Root<User> i = criteria.from(User.class);
        TypedQuery<User> query = em.createQuery(
            criteria.select(i).where(
                    cb.and(cb.equal(
                            i.get("login"),
                            cb.parameter(String.class, "login")),
                           cb.equal(
                                    i.get("password"),
                                    cb.parameter(String.class, "password"))
                    )
            )
        ).setParameter("login", login)
                .setParameter("password", password);

        User result = null;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            LOG.warn("Duplicate user", e);
        }
        return result;

    }
}
