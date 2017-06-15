package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.EconomApartment;
import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;
import com.mnazarenka.dao.mysql.BaseDao;
import com.mnazarenka.dao.mysql.MySqlAppartmentsDao;
import com.mnazarenka.dao.mysql.MySqlHotelDao;
import com.mnazarenka.util.TestDataImporter;
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

public class AppartmentDaoTest extends BaseDaoTest<Appartment>{
    private static SessionFactory sessionFactory;

    @Before
    public void initDb() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        TestDataImporter.getInstance().importTestData(sessionFactory);
    }

    @Override
    public Appartment getEntity() {
        Hotel hotel = new MySqlHotelDao().find(1L);
        Appartment appartment = new Appartment();
        appartment.setName("New Appartment");
        appartment.setHotel(hotel);
        return appartment;
    }

    @Override
    public BaseDao<Appartment> getCurrentDao() {
        return new MySqlAppartmentsDao();
    }

   /* @Test
    public void testFind() {
        Appartment appartment = new MySqlAppartmentsDao().find(1L);

        assertEquals("EconomAppartmentName", appartment.getName());
    }*/

    @Test
    public void testCreate() {
        Hotel hotel = new MySqlHotelDao().find(1L);

        Appartment appartment = new Appartment();
        appartment.setName("New Appartment");
        appartment.setHotel(hotel);

        Appartment newAppartment = new MySqlAppartmentsDao().create(appartment);

        assertEquals("New Appartment", newAppartment.getName());
    }

    @Test
    public void testUpdate() {
        MySqlAppartmentsDao mySqlAppartmentsDao = new MySqlAppartmentsDao();

        Appartment appartment = mySqlAppartmentsDao.find(1L);
        appartment.setName("New Name");

        mySqlAppartmentsDao.update(appartment);

        appartment = mySqlAppartmentsDao.find(1L);

        assertEquals("New Name", appartment.getName());
    }

    /*@Test
    public void testDelete() {
        MySqlAppartmentsDao mySqlAppartmentsDao = new MySqlAppartmentsDao();


        Hotel hotel = new MySqlHotelDao().find(1L);

        Appartment appartment = new Appartment();
        appartment.setName("New Appartment");
        appartment.setHotel(hotel);

        Appartment newAppartment = mySqlAppartmentsDao.create(appartment);

        long id = newAppartment.getId();

        mySqlAppartmentsDao.delete(appartment);

        Appartment deletedAppartment = mySqlAppartmentsDao.find(id);

        assertNull(deletedAppartment);
    }*/

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
