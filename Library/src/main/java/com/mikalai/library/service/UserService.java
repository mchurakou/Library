package com.mikalai.library.service;

import com.mikalai.library.beans.User;
import com.mikalai.library.dao.jpa.UserDAOI;

import javax.ejb.Stateless;

/**
 * Created by mikalai on 05.05.2016.
 */

@Stateless
public class UserService extends BasicService<User, UserDAOI> {

    public boolean add(User user){
        if(dao.getUser(user.getLogin()) != null){
            return false;
        } else {
            dao.save(user);
            return true;
        }


    }


    public User getUser(String login, String password){
        return dao.getUser(login, password);
    }


}
