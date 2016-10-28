package com.mikalai.library.service.user;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.beans.User;
import com.mikalai.library.service.base.BasicService;
import com.mikalai.library.utils.Pagination;

import java.util.List;

/**
 * Created by mikalai on 28.10.2016.
 */
public interface UserService extends BasicService<User> {
    boolean add(User user);

    User getUser(String login, String password);

    List<User> getActiveUsersForTable(Pagination pagination, Filter filter);

    int getCountOfActiveUsers();

    boolean deleteUser(int id);
}
