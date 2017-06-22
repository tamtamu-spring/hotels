package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.util.TestDataImporter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UserDaoTest extends BaseDaoTest<User, UserDao> {
    @Autowired
    private UserDao userDao;

    @Test
    public void testFindAll() {
        List<User> users = userDao.findAll();

        List<String> usersLogins = users.stream().map(User::getLogin)
                .collect(toList());
        List<String> userPasswords = users.stream().map(User::getPassword)
                .collect(toList());
        List<Boolean> userStatuses = users.stream().map(User::getBlockStatus)
                .collect(toList());
        List<String> roles = users.stream().map(User::getRole)
                .collect(toList()).stream().map(Role::getName).collect(toList());

        assertThat(users, hasSize(2));
        assertThat(usersLogins, containsInAnyOrder("AdminLogin", "UserLogin"));
        assertThat(userPasswords, containsInAnyOrder("AdminPassword", "UserPassword"));
        assertThat(userStatuses, containsInAnyOrder(true, false));
        assertThat(roles, containsInAnyOrder("User", "Admin"));
    }

  /*  @Override
    public User getEntity() {
        return new User();
    }

    @Override
    public BaseDaoImpl<User> getCurrentDao() {
        return new MySqlUserDaoImpl();
    }*/

    @Override
    public void testUpdate() {
        User user = getTestDataImporter().saveUser(false, null, "AdminLogin", "AdminPassword", userDao);
        Long id = user.getId();

        user.setLogin("New login");
        userDao.update(user);

        user = userDao.find(id);

        assertEquals("New login", user.getLogin());
    }
}
