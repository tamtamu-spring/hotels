package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class UserDaoTest {
    private static SessionFactory sessionFactory;

    @Before
    public void initDb() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        TestDataImporter.getInstance().importTestData(sessionFactory);
    }

    @Test
    public void findAllUsersTest() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<User> users = session.createQuery("select u from User u", User.class)
                    .getResultList();

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


            session.getTransaction().commit();
        }

    }

    @After
    public void destroy() {
        sessionFactory.close();
    }
}
