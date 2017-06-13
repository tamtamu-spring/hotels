package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.EconomApartment;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;
import com.mnazarenka.dao.mysql.MySqlAppartmentsDao;
import com.mnazarenka.dao.mysql.db.DbSessionFactoryCreater;
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
    public void testFindAll() {

        List<Appartment> appartments = new MySqlAppartmentsDao().findAll();

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
    }

    @Test
    public void testFindEconomAppartments() {

        List<EconomApartment> appartments = new MySqlAppartmentsDao().findAllEconomAppartments();

        assertEquals(appartments.get(0).getName(), "EconomAppartmentName");
        assertEquals(appartments.get(0).getDescription(), "EconomAppartmentDescription");
        assertEquals((long) appartments.get(0).getGuestsCounts(), 1);
        assertEquals(appartments.get(0).getWiFi(), true);

    }

    @Test
    public void findStandatAppartmentTest() {

            List<StandartAppartment> appartments = new MySqlAppartmentsDao().findAllStandartAppartments();

            assertEquals(appartments.get(0).getName(), "StandartAppartmentName");
            assertEquals(appartments.get(0).getDescription(), "StandartAppartmentDescription");
            assertEquals((long) appartments.get(0).getGuestsCounts(), 2);
            assertEquals(appartments.get(0).getWiFi(), true);
            assertEquals(appartments.get(0).getWc(), true);
            assertEquals(appartments.get(0).getTv(), true);
    }

    @Test
    public void findLuxAppartmentTest() {

            List<LuxAppartment> appartments = new MySqlAppartmentsDao().findAllLuxAppartments();

            assertEquals(appartments.get(0).getName(), "LuxAppartmentName");
            assertEquals(appartments.get(0).getDescription(), "LuxAppartmentDescription");
            assertEquals((long) appartments.get(0).getGuestsCounts(), 4);
            assertEquals(appartments.get(0).getWiFi(), true);
            assertEquals(appartments.get(0).getWc(), true);
            assertEquals(appartments.get(0).getTv(), true);
            assertEquals(appartments.get(0).getBar(), true);
            assertEquals(appartments.get(0).getKichen(), true);
    }

    @After
    public void destroy() {
        sessionFactory.close();
    }

}
