package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Dish;
import com.mnazarenka.dao.entity.DishOrder;
import com.mnazarenka.dao.entity.DishOrderDetails;
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
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class DishOrderDetailsDaoTest {
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

            List<DishOrderDetails> details = session.createQuery("select d from DishOrderDetails d", DishOrderDetails.class)
                    .getResultList();
            List<LocalDateTime> dishOrderTimes = details.stream().map(DishOrderDetails::getOrder).map(DishOrder::getOrderTime)
                    .collect(toList());
            List<String> dishesNames = details.stream().map(DishOrderDetails::getDish).map(Dish::getName).collect(toList());
            List<Integer> counts = details.stream().map(DishOrderDetails::getCount).collect(toList());

            assertThat(details, hasSize(2));
            assertThat(dishOrderTimes, containsInRelativeOrder(LocalDateTime.of(LocalDate.now(), LocalTime.MAX)));
            assertThat(dishesNames, containsInAnyOrder("FirstDish", "SecondDish"));
            assertThat(counts, containsInAnyOrder(1, 2));
            session.getTransaction().commit();
        }
    }

    @After
    public void destroy() {
        sessionFactory.close();
    }
}

