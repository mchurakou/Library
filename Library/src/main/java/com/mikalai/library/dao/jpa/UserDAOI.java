package com.mikalai.library.dao.jpa;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.beans.User;
import com.mikalai.library.utils.Pagination;

import java.util.List;

/**
 * Created by mikalai on 24.04.2016.
 */
public interface UserDAOI extends GenericDAO<User, Long>{
    User getUser(String login);
    int getCountOfActiveUsers();
    List<User> getActiveUsersForTable(Pagination pagination, Filter filter);
}
