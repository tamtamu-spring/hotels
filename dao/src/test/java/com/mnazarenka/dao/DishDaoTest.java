package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Dish;
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

public class DishDaoTest {
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

            List<Dish> hotels = session.createQuery("select d from Dish d", Dish.class)
                    .getResultList();

            List<String> dishNames = hotels.stream().map(Dish::getName)
                    .collect(toList());

            assertThat(hotels, hasSize(2));
            assertThat(dishNames, containsInAnyOrder("FirstDish", "SecondDish"));

            session.getTransaction().commit();
        }

    }

    @After
    public void destroy() {
        sessionFactory.close();
    }
}
