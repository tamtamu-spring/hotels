package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.dao.mysql.BaseDao;
import com.mnazarenka.dao.mysql.MySqlHotelDao;
import com.mnazarenka.dao.mysql.MySqlRoleDao;
import com.mnazarenka.dao.mysql.MySqlUserDao;
import org.junit.After;
import org.junit.Before;
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


    @Before
    public void initDb() {
    }

    @Test
    public void findAllUsersTest() {

        createTestData();

        MySqlUserDao mySqlUserDao = new MySqlUserDao();
        List<User> users = mySqlUserDao.findAll();

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
        MySqlUserDao mySqlUserDao = new MySqlUserDao();
        MySqlRoleDao mySqlRoleDao = new MySqlRoleDao();
        mySqlUserDao.delete(user);
        mySqlUserDao.delete(admin);
        mySqlRoleDao.delete(userRole);
        mySqlRoleDao.delete(adminRole);


    }

    private void createTestData() {
        MySqlUserDao mySqlUserDao = new MySqlUserDao();
        MySqlRoleDao mySqlRoleDao = new MySqlRoleDao();

        userRole = saveRole("User", mySqlRoleDao);
        adminRole = saveRole("Admin", mySqlRoleDao);

        admin = saveUser(false, adminRole, "AdminLogin", "AdminPassword", mySqlUserDao);
        user = saveUser(true, userRole, "UserLogin", "UserPassword", mySqlUserDao);

    }

    private Role saveRole(String name, MySqlRoleDao mySqlRoleDao) {
        Role role = new Role();
        role.setName(name);
        mySqlRoleDao.create(role);
        return role;
    }


    @After
    public void destroy() {


    }

    private User saveUser(boolean blockStatus, Role role, String login, String password, MySqlUserDao mySqlUserDao) {
        User user = new User();
        user.setBlockStatus(blockStatus);
        user.setRole(role);
        user.setLogin(login);
        user.setPassword(password);
        mySqlUserDao.create(user);
        return user;
    }

    @Override
    public User getEntity() {
        return new User();
    }

    @Override
    public BaseDao<User> getCurrentDao() {
        return new MySqlUserDao();
    }

    @Override
    public void testUpdate() {

        MySqlUserDao mySqlUserDao = new MySqlUserDao();
        User user = saveUser(false, null, "AdminLogin", "AdminPassword", mySqlUserDao);
        Long id = user.getId();

        user.setLogin("New login");
        mySqlUserDao.update(user);

        user = mySqlUserDao.find(id);

        assertEquals("New login", user.getLogin());

        mySqlUserDao.delete(user);
    }
}
