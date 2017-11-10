package com.mikalai.library.service.user;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.beans.SimpleBean;
import com.mikalai.library.beans.User;
import com.mikalai.library.dao.UserDAO;
import com.mikalai.library.dao.data.UserRepository;
import com.mikalai.library.dao.data.specification.GenericSpecification;
import com.mikalai.library.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private UserDAO userDAO;

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
//        return userRepository.getActiveUsersForTable(pagination, filter);

        PageRequest pageRequest = new PageRequest(pagination.getPage(), pagination.getCount());
        Page<User> page = userRepository.findAll(pageRequest);
        return page.getContent();
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
    public List<SimpleBean> getUserCategories() throws Exception {
        return userDAO.getUserCategories();  }


    @Override
    public Long getCount(Filter filter) {
        return userRepository.getCount(filter);
    }

    @Override
    public Page<User> getListForTable(Pagination pagination, Filter f) {
        Sort sort = new Sort(Sort.Direction.valueOf(pagination.getSord().toUpperCase()), pagination.getSidx());
        PageRequest pageRequest = new PageRequest(pagination.getPage() - 1, pagination.getRows(), sort);

        Page<User> page = null;
        if (f != null) {
            GenericSpecification<User> spec = new GenericSpecification<>(f);
            page = userRepository.findAll(spec, pageRequest);
        } else {
            page = userRepository.findAll(pageRequest);
        }

        return page;
    }

    @Override
    public User save(User entity) {
        return userRepository.save(entity);
    }
}
