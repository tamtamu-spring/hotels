package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Adress;
import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.mysql.MySqlHotelDao;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class HotelDaoTest extends BaseDaoTest<Hotel> {


    @Test
    public void findAdressByHotelIdTest() {
        MySqlHotelDao mySqlHotelDao = new MySqlHotelDao();

        Adress firstHotelAdress = new Adress();
        firstHotelAdress.setCity("FirstCity");
        firstHotelAdress.setStreet("FirstStreet");
        Hotel hotel = saveHotel("FirstHotel", firstHotelAdress, mySqlHotelDao);

        Adress adress = mySqlHotelDao.getAdressByHotelId(hotel.getId());

        assertEquals("FirstCity FirstStreet", adress.getFullAdress());

        mySqlHotelDao.delete(hotel);
    }

    @Test
    public void testFindAll() {

        MySqlHotelDao mySqlHotelDao = new MySqlHotelDao();

        Adress firstHotelAdress = new Adress();
        firstHotelAdress.setCity("FirstCity");
        firstHotelAdress.setStreet("FirstStreet");
        Hotel firstHotel = saveHotel("FirstHotel", firstHotelAdress, mySqlHotelDao);

        Adress secondAdress = new Adress();
        secondAdress.setCity("SecondCity");
        secondAdress.setStreet("SecondStreet");
        Hotel secondHotel = saveHotel("SecondHotel", secondAdress, mySqlHotelDao);

        Adress thirdAdress = new Adress();
        thirdAdress.setCity("ThirdCity");
        thirdAdress.setStreet("ThirdStreet");
        Hotel thirdHotel = saveHotel("ThirdHotel", thirdAdress, mySqlHotelDao);

        List<Hotel> hotels = new MySqlHotelDao().findAll();

        List<String> hotelNames = hotels.stream().map(Hotel::getName)
                .collect(toList());
        List<String> hotelAdresses = hotels.stream().map(Hotel::getAdress).map(Adress::getFullAdress).collect(toList());

        assertThat(hotels, hasSize(3));
        assertThat(hotelNames, containsInAnyOrder("FirstHotel", "SecondHotel", "ThirdHotel"));
        assertThat(hotelAdresses, containsInAnyOrder("FirstCity FirstStreet", "SecondCity SecondStreet", "ThirdCity ThirdStreet"));

        mySqlHotelDao.delete(firstHotel);
        mySqlHotelDao.delete(secondHotel);
        mySqlHotelDao.delete(thirdHotel);

    }


    private Hotel saveHotel(String name, Adress adress, MySqlHotelDao mySqlHotelDao) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setAdress(adress);
        mySqlHotelDao.create(hotel);
        return hotel;
    }

    @Override
    public Hotel getEntity() {
        Hotel hotel = new Hotel();
        return hotel;
    }

    @Override
    public BaseDaoImpl<Hotel> getCurrentDao() {
        return new MySqlHotelDao();
    }

    @Override
    public void testUpdate() {
        MySqlHotelDao mySqlHotelDao = new MySqlHotelDao();
        Hotel hotel = saveHotel("FirstHotel", null, mySqlHotelDao);
        Long id = hotel.getId();

        hotel.setName("New name");
        mySqlHotelDao.update(hotel);

        hotel = mySqlHotelDao.find(id);

        assertEquals("New name", hotel.getName());

        mySqlHotelDao.delete(hotel);

    }
}
