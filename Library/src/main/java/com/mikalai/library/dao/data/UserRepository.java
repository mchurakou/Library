package com.mikalai.library.dao.data;

import com.mikalai.library.beans.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>, UserDao {
    User getByLoginAndPassword(String login, String password);
    User getByLogin(String login);
}
