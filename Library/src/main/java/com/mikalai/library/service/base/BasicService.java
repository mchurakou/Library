package com.mikalai.library.service.base;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.utils.Pagination;
import org.springframework.data.domain.Page;

/**
 * Created by mikalai on 28.10.2016.
 */
public interface BasicService<T> {

    Long getCount(Filter filter);

    Page<T> getListForTable(Pagination pagination, Filter f);

    T save(T entity);
}
