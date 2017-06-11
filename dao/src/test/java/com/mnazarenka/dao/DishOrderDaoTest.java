package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.DishOrder;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class DishOrderDaoTest {
    private static SessionFactory sessionFactory;

    @Before
    public void initDb() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        TestDataImporter.getInstance().importTestData(sessionFactory);
    }

    @Test
    public void findAllDishesTest() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<DishOrder> orders = session.createQuery("select d from DishOrder d", DishOrder.class)
                    .getResultList();

            List<LocalDateTime> dishOrderTimes = orders.stream().map(DishOrder::getOrderTime)
                    .collect(toList());

            List<String> userLogins = orders.stream().map(DishOrder::getUser).map(User::getLogin).collect(toList());
            List<String> appartmentNames = orders.stream().map(DishOrder::getAppartment).map(Appartment::getName).collect(toList());

            assertThat(orders, hasSize(2));
            assertThat(dishOrderTimes, containsInAnyOrder(LocalDateTime.of(LocalDate.now(), LocalTime.MAX),
                    LocalDateTime.of(LocalDate.now(), LocalTime.MIN)));
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
