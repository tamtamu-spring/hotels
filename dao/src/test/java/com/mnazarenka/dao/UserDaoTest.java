package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.mysql.MySqlRoleDaoImpl;
import com.mnazarenka.dao.mysql.MySqlUserDaoImpl;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UserDaoTest extends BaseDaoTest<User> {
    private Role adminRole;
    private Role userRole;
    private User admin;
    private User user;


    @Test
    public void testFindAll() {

        createTestData();

        MySqlUserDaoImpl mySqlUserDaoImpl = new MySqlUserDaoImpl();
        List<User> users = mySqlUserDaoImpl.findAll();

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
        MySqlUserDaoImpl mySqlUserDaoImpl = new MySqlUserDaoImpl();
        MySqlRoleDaoImpl mySqlRoleDaoImpl = new MySqlRoleDaoImpl();
        mySqlUserDaoImpl.delete(user);
        mySqlUserDaoImpl.delete(admin);
        mySqlRoleDaoImpl.delete(userRole);
        mySqlRoleDaoImpl.delete(adminRole);
    }

    private void createTestData() {
        MySqlUserDaoImpl mySqlUserDaoImpl = new MySqlUserDaoImpl();
        MySqlRoleDaoImpl mySqlRoleDaoImpl = new MySqlRoleDaoImpl();

        userRole = saveRole("User", mySqlRoleDaoImpl);
        adminRole = saveRole("Admin", mySqlRoleDaoImpl);

        admin = saveUser(false, adminRole, "AdminLogin", "AdminPassword", mySqlUserDaoImpl);
        user = saveUser(true, userRole, "UserLogin", "UserPassword", mySqlUserDaoImpl);

    }

    private Role saveRole(String name, MySqlRoleDaoImpl mySqlRoleDaoImpl) {
        Role role = new Role();
        role.setName(name);
        mySqlRoleDaoImpl.create(role);
        return role;
    }

    private User saveUser(boolean blockStatus, Role role, String login, String password, MySqlUserDaoImpl mySqlUserDaoImpl) {
        User user = new User();
        user.setBlockStatus(blockStatus);
        user.setRole(role);
        user.setLogin(login);
        user.setPassword(password);
        mySqlUserDaoImpl.create(user);
        return user;
    }

    @Override
    public User getEntity() {
        return new User();
    }

    @Override
    public BaseDaoImpl<User> getCurrentDao() {
        return new MySqlUserDaoImpl();
    }

    @Override
    public void testUpdate() {

        MySqlUserDaoImpl mySqlUserDaoImpl = new MySqlUserDaoImpl();
        User user = saveUser(false, null, "AdminLogin", "AdminPassword", mySqlUserDaoImpl);
        Long id = user.getId();

        user.setLogin("New login");
        mySqlUserDaoImpl.update(user);

        user = mySqlUserDaoImpl.find(id);

        assertEquals("New login", user.getLogin());

        mySqlUserDaoImpl.delete(user);
    }
}
