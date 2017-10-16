package com.mikalai.library.dao.jpa;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.ajax_json.Rule;
import com.mikalai.library.beans.BasicEntity;
import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by mikalai on 24.04.2016.
 */
public abstract class GenericDAOImpl<T extends BasicEntity, ID extends Serializable> implements GenericDAO<T, ID> {
    protected final Class<T> entityClass;

    //@Autowired
    //@Autowired
    @PersistenceContext
    protected EntityManager em;

    protected GenericDAOImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
    public T save(T instance) {
        T res = instance;
        if (instance.getId() != 0){
            res= em.merge(instance);
        } else {
            em.persist(instance);
        }
        return res;
    }


    /**
     * @param filter
     * @return count

     *
     */
    public Long getCount(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> c = cb.createQuery(Long.class);

        Root<T> root = c.from(entityClass);

        c.select(em.getCriteriaBuilder().count(root));

        if (filter != null){
            Predicate[] predicates = buildFilter(filter, cb, root);

            if ("AND".equals(filter.getGroupOp())){
                c.where(cb.and(predicates));
            } else if ("OR".equals(filter.getGroupOp())){
                c.where(cb.or(predicates));
            }
        }

        return em.createQuery(c).getSingleResult();

    }

    protected Predicate[] buildFilter(Filter filter, CriteriaBuilder cb, Root<T> root) {
        Predicate[] predicates = null;
        predicates = new Predicate[filter.getRules().size()];

        for (int i = 0; i < filter.getRules().size();i++){
            Rule r = filter.getRules().get(i);
            String field = r.getField();

            if (field.endsWith("Id")) // search by lookup field
                field = field.substring(0, field.length() - 2);

            Path f = root.get(field);

            Predicate predicate = null;


            if (r.getOp().equals(Constants.EQUALS))
                predicate = cb.equal(f, r.getData());


            if (r.getOp().equals(Constants.NOT_EQUALS))
                predicate = cb.notEqual(f, r.getData());

            if (r.getOp().equals(Constants.BEGIN_WITH))
                predicate = cb.like(f, r.getData() + "%");

            if (r.getOp().equals(Constants.CONTAIN))
                predicate = cb.like(f, "%" + r.getData() + "%");

            if (r.getOp().equals(Constants.LESS))
                predicate = cb.lessThan(f, r.getData());

            if (r.getOp().equals(Constants.LESS_OR_EQUAL))
                predicate = cb.lessThanOrEqualTo(f, r.getData());

            if (r.getOp().equals(Constants.GREATER))
                predicate = cb.greaterThan(f, r.getData());

            if (r.getOp().equals(Constants.GREATER_OR_EQUAL))
                predicate = cb.greaterThanOrEqualTo(f, r.getData());

            predicates[i] = predicate;

        }

        return predicates;
    }


    public List<T> getListForTable(Pagination pagination, Filter filter){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> c = cb.createQuery(entityClass);

        Root<T> root = c.from(entityClass);

        c.select(root);

        if (filter != null){
            Predicate[] predicates = buildFilter(filter, cb, root);
            c.where(cb.and(predicates));
        }

        TypedQuery tq = getTypedQueryWithFilterAndPagination(pagination, cb, c, root);
        return tq.getResultList();
    }

    protected TypedQuery getTypedQueryWithFilterAndPagination(Pagination pagination, CriteriaBuilder cb, CriteriaQuery<T> c, Root<T> root) {
        if ("asc".equals(pagination.getSord())) {
            c.orderBy(cb.asc(root.get(pagination.getSidx())));
        } else {
            c.orderBy(cb.desc(root.get(pagination.getSidx())));
        }

        TypedQuery tq = em.createQuery(c);
        tq.setFirstResult(pagination.getStart() - 1);
        tq.setMaxResults(pagination.getRows());
        return tq;
    }


    /**/
    public T findById(ID id) {
        return findById(id, LockModeType.NONE);
    }
    public T findById(ID id, LockModeType lockModeType) {
        return em.find(entityClass, id, lockModeType);
    }
    public T findReferenceById(ID id) {
        return em.getReference(entityClass, id);
    }
    public List<T> findAll() {
        CriteriaQuery<T> c =
                em.getCriteriaBuilder().createQuery(entityClass);
        c.select(c.from(entityClass));
        return em.createQuery(c).getResultList();
    }
    public Long getCount() {
        CriteriaQuery<Long> c =
                em.getCriteriaBuilder().createQuery(Long.class);
        c.select(em.getCriteriaBuilder().count(c.from(entityClass)));
        return em.createQuery(c).getSingleResult();
    }

    public void makeTransient(T instance) {
        em.remove(instance);
    }



    public void checkVersion(T entity, boolean forceUpdate) {
        em.lock(
                entity,
                forceUpdate
                        ? LockModeType.OPTIMISTIC_FORCE_INCREMENT
                        : LockModeType.OPTIMISTIC
        );
    }
}
