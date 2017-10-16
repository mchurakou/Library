package com.mikalai.library.dao.data;

import com.mikalai.library.beans.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
    User getByLoginAndPassword( String login, String password);
}
