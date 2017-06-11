package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Role;
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

public class RoleDaoTest {

    private static SessionFactory sessionFactory;

    @Before
    public void initDb() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        TestDataImporter.getInstance().importTestData(sessionFactory);
    }

    @Test
    public void findAllRolesTest() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Role> roles = session.createQuery("select r from Role r", Role.class)
                    .getResultList();

            List<String> rolesNames = roles.stream().map(Role::getName)
                    .collect(toList());

            assertThat(roles, hasSize(2));
            assertThat(rolesNames, containsInAnyOrder("User", "Admin"));

            session.getTransaction().commit();
        }

    }

    @After
    public void destroy() {
        sessionFactory.close();
    }

}
