package com.mikalai.library.dao.jpa;

import com.mikalai.library.beans.User;

/**
 * Created by mikalai on 24.04.2016.
 */
public interface UserDAOI extends GenericDAO<User, Long>{
    User getUser(String login, String password);
    User getUser(String login);
}
