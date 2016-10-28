package com.mikalai.library.service.base;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.dao.jpa.base.GenericDAO;
import com.mikalai.library.utils.Pagination;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by mikalai on 06.05.2016.
 */


public abstract class BasicServiceImpl<T, S extends GenericDAO> implements BasicService<T> {
    @Inject
    protected S dao;

    @Override
    public Long getCount(Filter filter) {
        return dao.getCount(filter);
    }

    @Override
    public List<T> getListForTable(Pagination pagination, Filter f) {
        return dao.getListForTable(pagination, f);
    }

    @Override
    public T save(T entity){
        return (T) dao.save(entity);
    }
}
