package com.mikalai.library.service.user;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.beans.User;
import com.mikalai.library.dao.data.UserRepository;
import com.mikalai.library.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mikalai on 05.05.2016.
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    public boolean add(User user){
        if(userRepository.getByLogin(user.getLogin()) != null){
            return false;
        } else {
            userRepository.save(user);
            return true;
        }


    }


    public User getUser(String login, String password){
        return userRepository.getByLoginAndPassword(login, password);
    }



    /**
     * List of active users for table with searching
     * @return list of users
     *
     */
    public List<User> getActiveUsersForTable(Pagination pagination, Filter filter){
        return userRepository.getActiveUsersForTable(pagination, filter);
    }

    /**
     * count of users
     * @return count
     *
     */
    public int getCountOfActiveUsers(){
        return userRepository.getCountOfActiveUsers();
    }

    @Override
    public boolean deleteUser(long id) {
        throw new RuntimeException("not migrated");
    }


    @Override
    public Long getCount(Filter filter) {
        return userRepository.getCount(filter);
    }

    @Override
    public List<User> getListForTable(Pagination pagination, Filter f) {
        return userRepository.getListForTable(pagination, f);
    }

    @Override
    public User save(User entity) {
        return userRepository.save(entity);
    }
}
