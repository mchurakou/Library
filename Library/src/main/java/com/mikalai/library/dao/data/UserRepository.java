package com.mikalai.library.dao.data;

import com.mikalai.library.beans.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, UserDao, JpaSpecificationExecutor {
    User getByLoginAndPassword(String login, String password);
    User getByLogin(String login);

    @Query("select count(u) from User u where u.role not in ('NEW', 'ADMINISTRATOR')")
    int getCountOfActiveUsers();
}
