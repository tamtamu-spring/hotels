package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.EconomApartment;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;
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
import static org.junit.Assert.*;

public class AppartmentDaoTest {
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

            List<Appartment> appartments = session.createQuery("select a from Appartment a", Appartment.class)
                    .getResultList();

            List<String> appartmentNames = appartments.stream().map(Appartment::getName)
                    .collect(toList());
            List<String> appartmentDescriptions = appartments.stream().map(Appartment::getDescription)
                    .collect(toList());
            List<Integer> appartmentsCounts = appartments.stream().map(Appartment::getGuestsCounts).collect(toList());
            List<Boolean> appartmentsWifiOptions = appartments.stream().map(Appartment::getWiFi).collect(toList());


            assertThat(appartments, hasSize(3));
            assertThat(appartmentNames, containsInAnyOrder("EconomAppartmentName", "StandartAppartmentName",
                    "LuxAppartmentName"));
            assertThat(appartmentDescriptions, containsInAnyOrder("EconomAppartmentDescription", "StandartAppartmentDescription",
                    "LuxAppartmentDescription"));
            assertThat(appartmentsCounts, containsInAnyOrder(1, 2, 4));
            appartmentsWifiOptions.forEach(a -> assertNotNull(a));

            session.getTransaction().commit();
        }

    }

    @Test
    public void findEconomAppartmentTest() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            EconomApartment appartment = session.createQuery("select e from EconomApartment e", EconomApartment.class)
                    .getSingleResult();


            assertEquals(appartment.getName(), "EconomAppartmentName");
            assertEquals(appartment.getDescription(), "EconomAppartmentDescription");
            assertEquals((long) appartment.getGuestsCounts(), 1);
            assertEquals(appartment.getWiFi(), true);

            session.getTransaction().commit();
        }

    }

    @Test
    public void findStandatAppartmentTest() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            StandartAppartment appartment = session.createQuery("select e from StandartAppartment e", StandartAppartment.class)
                    .getSingleResult();

            assertEquals(appartment.getName(), "StandartAppartmentName");
            assertEquals(appartment.getDescription(), "StandartAppartmentDescription");
            assertEquals((long) appartment.getGuestsCounts(), 2);
            assertEquals(appartment.getWiFi(), true);
            assertEquals(appartment.getWc(), true);
            assertEquals(appartment.getTv(), true);

            session.getTransaction().commit();
        }
    }

    @Test
    public void findLuxAppartmentTest() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            LuxAppartment appartment = session.createQuery("select e from LuxAppartment e", LuxAppartment.class)
                    .getSingleResult();

            assertEquals(appartment.getName(), "LuxAppartmentName");
            assertEquals(appartment.getDescription(), "LuxAppartmentDescription");
            assertEquals((long) appartment.getGuestsCounts(), 4);
            assertEquals(appartment.getWiFi(), true);
            assertEquals(appartment.getWc(), true);
            assertEquals(appartment.getTv(), true);
            assertEquals(appartment.getBar(), true);
            assertEquals(appartment.getKichen(), true);

            session.getTransaction().commit();
        }
    }

    @After
    public void destroy() {
        sessionFactory.close();
    }

}
