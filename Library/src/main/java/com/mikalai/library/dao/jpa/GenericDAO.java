package com.mikalai.library.dao.jpa;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.utils.Pagination;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * Created by mikalai on 24.04.2016.
 */
public interface GenericDAO<T, ID> {
    Long getCount(Filter filter);
    List<T> getListForTable(Pagination pagination, Filter filter);
    T save(T instance);



    T findById(ID id);
    T findById(ID id, LockModeType lockModeType);
    T findReferenceById(ID id) ;
    List<T> findAll() ;
    Long getCount();


    void makeTransient(T instance);
    void checkVersion(T entity, boolean forceUpdate);



}
