package com.mikalai.library.service.user;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.beans.User;
import com.mikalai.library.dao.jpa.user.UserDAOI;
import com.mikalai.library.service.base.BasicServiceImpl;
import com.mikalai.library.utils.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mikalai on 05.05.2016.
 */

@Service
@Transactional
public class UserServiceImpl extends BasicServiceImpl<User, UserDAOI> implements UserService {


    @Override
    public boolean add(User user){
        if(dao.getUser(user.getLogin()) != null){
            return false;
        } else {
            dao.save(user);
            return true;
        }


    }

    @Override
    public User getUser(String login, String password){
        return dao.getUser(login, password);
    }



    /**
     * List of active users for table with searching
     * @return list of users
     *
     */
    @Override
    public List<User> getActiveUsersForTable(Pagination pagination, Filter filter){
        return dao.getActiveUsersForTable(pagination, filter);
    }

    /**
     * count of users
     * @return count
     *
     */
    @Override
    public int getCountOfActiveUsers(){
        return dao.getCountOfActiveUsers();
    }

    @Override
    public boolean deleteUser(int id) {
        return dao.deleteUser(id);
    }


}
