package com.mikalai.library.dao.jpa;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.beans.User;
import com.mikalai.library.utils.Pagination;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mikalai on 24.04.2016.
 */
@Repository
public class UserDAOImpl extends GenericDAOImpl<User, Long> implements UserDAOI {
    private static final Logger LOG = Logger.getLogger(UserDAOImpl.class);

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

    @Override
    public int getCountOfActiveUsers() {
        Query query = em.createNamedQuery("User.getActiveUsers");
        int result = ((Long)query.getSingleResult()).intValue();
        return result;
    }

    /**
     * List of active users for table with searching
     * @return list of users
     * @throws Exception
     *
     */
    public List<User> getActiveUsersForTable(Pagination pagination, Filter filter){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> c = cb.createQuery(entityClass);

        Root<User> root = c.from(entityClass);

        c.select(root);



        Path f = root.get("role");

        Predicate[] predicates = new Predicate[1];
        Predicate predicate = cb.not(f.in(com.mikalai.library.beans.dictionary.Role.NEW, com.mikalai.library.beans.dictionary.Role.ADMINISTRATOR));

        if (filter != null){
            Predicate[] filterPedicates = buildFilter(filter, cb, root);
            predicates =  Arrays.copyOf(filterPedicates, filterPedicates.length + 1);

        }
        predicates[predicates.length - 1] = predicate;




        c.where(cb.and(predicates));

        TypedQuery tq = getTypedQueryWithFilterAndPagination(pagination, cb, c, root);
        return tq.getResultList();
    }
}
