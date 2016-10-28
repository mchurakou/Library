package com.mikalai.library.service;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.dao.jpa.GenericDAO;
import com.mikalai.library.utils.Pagination;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by mikalai on 06.05.2016.
 */


public abstract class BasicService<T, S extends GenericDAO> {
    @Inject
    protected S dao;

    public Long getCount(Filter filter) {
        return dao.getCount(filter);
    }

    public List<T> getListForTable(Pagination pagination, Filter f) {
        return dao.getListForTable(pagination, f);
    }

    public T save(T entity){
        return (T) dao.save(entity);
    }
}
