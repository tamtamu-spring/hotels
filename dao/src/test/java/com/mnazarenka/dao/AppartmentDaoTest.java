package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.EconomApartment;
import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;
import com.mnazarenka.dao.mysql.BaseDao;
import com.mnazarenka.dao.mysql.MySqlAppartmentsDao;
import com.mnazarenka.dao.mysql.MySqlHotelDao;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class AppartmentDaoTest extends BaseDaoTest<Appartment> {

    private Hotel hotel;

    private StandartAppartment standartAppartment;
    private LuxAppartment luxAppartment;
    private EconomApartment economApartment;


    @Override
    public Appartment getEntity() {
        Appartment appartment = new Appartment();
        appartment.setName("New Appartment");
        return appartment;
    }

    @Override
    public BaseDao<Appartment> getCurrentDao() {
        return new MySqlAppartmentsDao();
    }


    @Test
    public void testUpdate() {
        MySqlAppartmentsDao mySqlAppartmentsDao = new MySqlAppartmentsDao();

        Appartment appartment = new Appartment();
        appartment.setName("New Appartment");
        appartment = mySqlAppartmentsDao.create(appartment);

        appartment.setName("New Name");
        mySqlAppartmentsDao.update(appartment);

        appartment = mySqlAppartmentsDao.find(appartment.getId());

        assertEquals("New Name", appartment.getName());

        mySqlAppartmentsDao.delete(appartment);
    }

    private void destroyData() {
        MySqlAppartmentsDao mySqlAppartmentsDao = new MySqlAppartmentsDao();
        mySqlAppartmentsDao.delete(economApartment);
        mySqlAppartmentsDao.delete(standartAppartment);
        mySqlAppartmentsDao.delete(luxAppartment);

        new MySqlHotelDao().delete(hotel);

    }

    @Test
    public void testFindByRange(){
        createTestData();

        List<Appartment> appartments = new MySqlAppartmentsDao().findAppartmentsByRange(0, 2);

        List<String> appartmentNames = appartments.stream().map(Appartment::getName)
                .collect(toList());

        assertThat(appartments, hasSize(2));
        assertThat(appartmentNames, containsInAnyOrder("EconomAppartmentName", "StandartAppartmentName"));

        destroyData();
    }

    @Test
    public void testFindAll() {

        createTestData();

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

        destroyData();
    }


    @Test
    public void testFindEconomAppartments() {

        createTestData();

        List<EconomApartment> appartments = new MySqlAppartmentsDao().findAllEconomAppartments();

        assertEquals(appartments.get(0).getName(), "EconomAppartmentName");
        assertEquals(appartments.get(0).getDescription(), "EconomAppartmentDescription");
        assertEquals((long) appartments.get(0).getGuestsCounts(), 1);
        assertEquals(appartments.get(0).getWiFi(), true);

        destroyData();
    }

    @Test
    public void findStandatAppartmentTest() {

        createTestData();

        List<StandartAppartment> appartments = new MySqlAppartmentsDao().findAllStandartAppartments();

        assertEquals(appartments.get(0).getName(), "StandartAppartmentName");
        assertEquals(appartments.get(0).getDescription(), "StandartAppartmentDescription");
        assertEquals((long) appartments.get(0).getGuestsCounts(), 2);
        assertEquals(appartments.get(0).getWiFi(), true);
        assertEquals(appartments.get(0).getWc(), true);
        assertEquals(appartments.get(0).getTv(), true);

        destroyData();
    }

    @Test
    public void findLuxAppartmentTest() {

        createTestData();

        List<LuxAppartment> appartments = new MySqlAppartmentsDao().findAllLuxAppartments();

        assertEquals(appartments.get(0).getName(), "LuxAppartmentName");
        assertEquals(appartments.get(0).getDescription(), "LuxAppartmentDescription");
        assertEquals((long) appartments.get(0).getGuestsCounts(), 4);
        assertEquals(appartments.get(0).getWiFi(), true);
        assertEquals(appartments.get(0).getWc(), true);
        assertEquals(appartments.get(0).getTv(), true);
        assertEquals(appartments.get(0).getBar(), true);
        assertEquals(appartments.get(0).getKichen(), true);

        destroyData();
    }

    private void createTestData() {
        MySqlAppartmentsDao mySqlAppartmentsDao = new MySqlAppartmentsDao();

        hotel = saveHotel("New Hotel", new MySqlHotelDao());

        economApartment = saveEconomAppartment(hotel, "EconomAppartmentName", "EconomAppartmentDescription",
                1, true, mySqlAppartmentsDao);
        standartAppartment = saveStandartAppartment(hotel, "StandartAppartmentName", "StandartAppartmentDescription",
                2, true, true, true, mySqlAppartmentsDao);
        luxAppartment = saveLuxAppartment(hotel, "LuxAppartmentName", "LuxAppartmentDescription",
                4, true, true, true, true, true, mySqlAppartmentsDao);
    }


    private EconomApartment saveEconomAppartment(Hotel hotel, String name, String description,
                                                 int count, boolean wiFi, MySqlAppartmentsDao mySqlAppartmentsDao) {
        EconomApartment apartment = new EconomApartment();
        apartment.setName(name);
        apartment.setHotel(hotel);
        apartment.setDescription(description);
        apartment.setGuestsCounts(count);
        apartment.setWiFi(wiFi);
        mySqlAppartmentsDao.create(apartment);
        return apartment;
    }

    private LuxAppartment saveLuxAppartment(Hotel hotel, String name, String description, int guestCount,
                                            boolean wifi, boolean wc, boolean tv, boolean bar, boolean kitchen, MySqlAppartmentsDao mySqlAppartmentsDao) {
        LuxAppartment luxAppartment = new LuxAppartment();
        luxAppartment.setHotel(hotel);
        luxAppartment.setName(name);
        luxAppartment.setDescription(description);
        luxAppartment.setGuestsCounts(guestCount);
        luxAppartment.setWiFi(wifi);
        luxAppartment.setWc(wc);
        luxAppartment.setTv(tv);
        luxAppartment.setBar(bar);
        luxAppartment.setKichen(kitchen);
        mySqlAppartmentsDao.create(luxAppartment);
        return luxAppartment;

    }

    private StandartAppartment saveStandartAppartment(Hotel hotel, String name, String description,
                                                      int count, boolean wiFi, boolean wc, boolean tv, MySqlAppartmentsDao mySqlAppartmentsDao) {
        StandartAppartment apartment = new StandartAppartment();
        apartment.setName(name);
        apartment.setHotel(hotel);
        apartment.setDescription(description);
        apartment.setGuestsCounts(count);
        apartment.setWiFi(wiFi);
        apartment.setWc(wc);
        apartment.setTv(tv);
        mySqlAppartmentsDao.create(apartment);
        return apartment;
    }

    private Hotel saveHotel(String name, MySqlHotelDao mySqlHotelDao) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        mySqlHotelDao.create(hotel);
        return hotel;
    }
}
