package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.entity.User;
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
    @Autowired
    private RoleDao roleDao;

    private Role adminRole;
    private Role userRole;
    private User admin;
    private User user;

    @Test
    public void testFindAll() {

        createTestData();

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

        destroyTestData();

    }

    private void destroyTestData() {
        userDao.delete(user);
        userDao.delete(admin);
        roleDao.delete(userRole);
        roleDao.delete(adminRole);
    }

    private void createTestData() {

        userRole = saveRole("User", roleDao);
        adminRole = saveRole("Admin", roleDao);

        admin = saveUser(false, adminRole, "AdminLogin", "AdminPassword", userDao);
        user = saveUser(true, userRole, "UserLogin", "UserPassword", userDao);

    }

    private Role saveRole(String name, RoleDao roleDao) {
        Role role = new Role();
        role.setName(name);
        roleDao.create(role);
        return role;
    }

    private User saveUser(boolean blockStatus, Role role, String login, String password, UserDao userDao) {
        User user = new User();
        user.setBlockStatus(blockStatus);
        user.setRole(role);
        user.setLogin(login);
        user.setPassword(password);
        userDao.create(user);
        return user;
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

        User user = saveUser(false, null, "AdminLogin", "AdminPassword", userDao);
        Long id = user.getId();

        user.setLogin("New login");
        userDao.update(user);

        user = userDao.find(id);

        assertEquals("New login", user.getLogin());

        userDao.delete(user);
    }
}
