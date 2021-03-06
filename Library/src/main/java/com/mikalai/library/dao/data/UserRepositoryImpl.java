package com.mikalai.library.dao.data;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.beans.User;
import com.mikalai.library.utils.Pagination;

import org.apache.log4j.Logger;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mikalai on 24.04.2016.
 */
public class UserRepositoryImpl extends GenericDaoImpl<User, Long> implements UserDao {
    private static final Logger LOG = Logger.getLogger(UserRepositoryImpl.class);

    public UserRepositoryImpl() {
        super(User.class);
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
