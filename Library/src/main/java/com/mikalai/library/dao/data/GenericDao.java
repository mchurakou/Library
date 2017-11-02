package com.mikalai.library.dao.data;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.utils.Pagination;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.io.Serializable;
import java.util.List;

/**
 * Created by mikalai on 24.04.2016.
 */
public interface GenericDao<T, ID extends Serializable> {
    Long getCount(Filter filter);
    List<T> getListForTable(Pagination pagination, Filter filter);
    Long getCount();
}
