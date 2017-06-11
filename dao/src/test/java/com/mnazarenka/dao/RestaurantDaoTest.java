package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Adress;
import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.Restaurant;
import com.mnazarenka.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class RestaurantDaoTest {
    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void init() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @Before
    public void initDb() {
        TestDataImporter.getInstance().importTestData(sessionFactory);
    }

    @Test
    public void findAllHotelsTest() {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            List<Restaurant> restaurants = session.createQuery("select r from Restaurant r", Restaurant.class)
                    .getResultList();

            List<String> restaurantsNames = restaurants.stream().map(Restaurant::getName)
                    .collect(toList());
            List<String> hotelAdresses = restaurants.stream().map(Restaurant::getName).collect(toList());

            assertThat(restaurants, hasSize(2));
            assertThat(restaurantsNames, containsInAnyOrder("FirstRestaurant", "SecondRestaurant"));

            session.getTransaction().commit();
        }

    }

    @AfterClass
    public static void destroy() {
        sessionFactory.close();
    }
}
