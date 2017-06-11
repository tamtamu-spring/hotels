package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;


public class OrderDaoTest {
    private static SessionFactory sessionFactory;

    @Before
    public void initDb() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        TestDataImporter.getInstance().importTestData(sessionFactory);
    }

    @Test
    public void findAllAppartmentsTest() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<AppartmentOrder> orders = session.createQuery("select o from AppartmentOrder o", AppartmentOrder.class)
                    .getResultList();

            List<LocalDate> startDates = orders.stream().map(AppartmentOrder::getStartDate)
                    .collect(toList());
            List<LocalDate> endDates = orders.stream().map(AppartmentOrder::getEndDate)
                    .collect(toList());
            List<String> userLogins = orders.stream().map(AppartmentOrder::getUser).collect(toList())
                    .stream().map(User::getLogin).collect(toList());
            List<String> appartmentNames = orders.stream().map(AppartmentOrder::getAppartment).collect(toList())
                    .stream().map(Appartment::getName).collect(toList());

            assertThat(orders, hasSize(2));
            assertThat(startDates, containsInAnyOrder(LocalDate.of(2017, 10, 10), LocalDate.of(2017, 11, 20)));
            assertThat(endDates, containsInAnyOrder(LocalDate.of(2017, 10, 15), LocalDate.of(2017, 11, 25)));
            assertThat(userLogins, containsInAnyOrder("UserLogin", "AdminLogin"));
            assertThat(appartmentNames, containsInAnyOrder("EconomAppartmentName", "LuxAppartmentName"));

            session.getTransaction().commit();
        }

    }

    @After
    public void destroy() {
        sessionFactory.close();
    }
}
