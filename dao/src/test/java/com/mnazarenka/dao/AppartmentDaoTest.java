package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.EconomApartment;
import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.mysql.MySqlAppartmentsDaoImpl;
import com.mnazarenka.dao.mysql.MySqlHotelDaoImpl;
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
    public BaseDaoImpl<Appartment> getCurrentDao() {
        return new MySqlAppartmentsDaoImpl();
    }


    @Test
    public void testUpdate() {
        MySqlAppartmentsDaoImpl mySqlAppartmentsDaoImpl = new MySqlAppartmentsDaoImpl();

        Appartment appartment = new Appartment();
        appartment.setName("New Appartment");
        appartment = mySqlAppartmentsDaoImpl.create(appartment);

        appartment.setName("New Name");
        mySqlAppartmentsDaoImpl.update(appartment);

        appartment = mySqlAppartmentsDaoImpl.find(appartment.getId());

        assertEquals("New Name", appartment.getName());

        mySqlAppartmentsDaoImpl.delete(appartment);
    }

    private void destroyData() {
        MySqlAppartmentsDaoImpl mySqlAppartmentsDaoImpl = new MySqlAppartmentsDaoImpl();
        mySqlAppartmentsDaoImpl.delete(economApartment);
        mySqlAppartmentsDaoImpl.delete(standartAppartment);
        mySqlAppartmentsDaoImpl.delete(luxAppartment);

        new MySqlHotelDaoImpl().delete(hotel);

    }

    @Test
    public void testFindByRange(){
        createTestData();

        List<Appartment> appartments = new MySqlAppartmentsDaoImpl().findAppartmentsByRange(0, 2);

        List<String> appartmentNames = appartments.stream().map(Appartment::getName)
                .collect(toList());

        assertThat(appartments, hasSize(2));
        assertThat(appartmentNames, containsInAnyOrder("EconomAppartmentName", "StandartAppartmentName"));

        destroyData();
    }

    @Test
    public void testFindAll() {

        createTestData();

        List<Appartment> appartments = new MySqlAppartmentsDaoImpl().findAll();

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

        List<EconomApartment> appartments = new MySqlAppartmentsDaoImpl().findAllEconomAppartments();

        assertEquals(appartments.get(0).getName(), "EconomAppartmentName");
        assertEquals(appartments.get(0).getDescription(), "EconomAppartmentDescription");
        assertEquals((long) appartments.get(0).getGuestsCounts(), 1);
        assertEquals(appartments.get(0).getWiFi(), true);

        destroyData();
    }

    @Test
    public void findStandatAppartmentTest() {

        createTestData();

        List<StandartAppartment> appartments = new MySqlAppartmentsDaoImpl().findAllStandartAppartments();

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

        List<LuxAppartment> appartments = new MySqlAppartmentsDaoImpl().findAllLuxAppartments();

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
        MySqlAppartmentsDaoImpl mySqlAppartmentsDaoImpl = new MySqlAppartmentsDaoImpl();

        hotel = saveHotel("New Hotel", new MySqlHotelDaoImpl());

        economApartment = saveEconomAppartment(hotel, "EconomAppartmentName", "EconomAppartmentDescription",
                1, true, mySqlAppartmentsDaoImpl);
        standartAppartment = saveStandartAppartment(hotel, "StandartAppartmentName", "StandartAppartmentDescription",
                2, true, true, true, mySqlAppartmentsDaoImpl);
        luxAppartment = saveLuxAppartment(hotel, "LuxAppartmentName", "LuxAppartmentDescription",
                4, true, true, true, true, true, mySqlAppartmentsDaoImpl);
    }


    private EconomApartment saveEconomAppartment(Hotel hotel, String name, String description,
                                                 int count, boolean wiFi, MySqlAppartmentsDaoImpl mySqlAppartmentsDaoImpl) {
        EconomApartment apartment = new EconomApartment();
        apartment.setName(name);
        apartment.setHotel(hotel);
        apartment.setDescription(description);
        apartment.setGuestsCounts(count);
        apartment.setWiFi(wiFi);
        mySqlAppartmentsDaoImpl.create(apartment);
        return apartment;
    }

    private LuxAppartment saveLuxAppartment(Hotel hotel, String name, String description, int guestCount,
                                            boolean wifi, boolean wc, boolean tv, boolean bar, boolean kitchen, MySqlAppartmentsDaoImpl mySqlAppartmentsDaoImpl) {
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
        mySqlAppartmentsDaoImpl.create(luxAppartment);
        return luxAppartment;

    }

    private StandartAppartment saveStandartAppartment(Hotel hotel, String name, String description,
                                                      int count, boolean wiFi, boolean wc, boolean tv, MySqlAppartmentsDaoImpl mySqlAppartmentsDaoImpl) {
        StandartAppartment apartment = new StandartAppartment();
        apartment.setName(name);
        apartment.setHotel(hotel);
        apartment.setDescription(description);
        apartment.setGuestsCounts(count);
        apartment.setWiFi(wiFi);
        apartment.setWc(wc);
        apartment.setTv(tv);
        mySqlAppartmentsDaoImpl.create(apartment);
        return apartment;
    }

    private Hotel saveHotel(String name, MySqlHotelDaoImpl mySqlHotelDaoImpl) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        mySqlHotelDaoImpl.create(hotel);
        return hotel;
    }
}
