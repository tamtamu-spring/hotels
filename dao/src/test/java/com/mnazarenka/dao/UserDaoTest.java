package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.entity.User;
import lombok.Getter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UserDaoTest extends BaseDaoTest<User> {
    @Autowired
    @Getter
    private UserDao dao;

    @Test
    public void testFindAll() {
        List<User> users = dao.findAll();

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

    @Override
    public void testUpdate() {
        User user = getTestDataImporter().saveUser(false, null, "AdminLogin", "AdminPassword", dao);
        Long id = user.getId();

        user.setLogin("New login");
        dao.update(user);

        user = dao.find(id);

        assertEquals("New login", user.getLogin());
    }

    @Test
    public void testFindUserByLogin(){
        User user = dao.getUserByLogin("AdminLogin");

        assertEquals("AdminLogin", user.getLogin());
    }
}
