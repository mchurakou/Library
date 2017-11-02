package com.mikalai.library.service.base;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.utils.Pagination;

import java.util.List;

/**
 * Created by mikalai on 28.10.2016.
 */
public interface BasicService<T> {

    Long getCount(Filter filter);

    List<T> getListForTable(Pagination pagination, Filter f);

    T save(T entity);
}
