package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Adress;
import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.mysql.MySqlHotelDaoImpl;
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
        MySqlHotelDaoImpl mySqlHotelDaoImpl = new MySqlHotelDaoImpl();

        Adress firstHotelAdress = new Adress();
        firstHotelAdress.setCity("FirstCity");
        firstHotelAdress.setStreet("FirstStreet");
        Hotel hotel = saveHotel("FirstHotel", firstHotelAdress, mySqlHotelDaoImpl);

        Adress adress = mySqlHotelDaoImpl.getAdressByHotelId(hotel.getId());

        assertEquals("FirstCity FirstStreet", adress.getFullAdress());

        mySqlHotelDaoImpl.delete(hotel);
    }

    @Test
    public void testFindAll() {

        MySqlHotelDaoImpl mySqlHotelDaoImpl = new MySqlHotelDaoImpl();

        Adress firstHotelAdress = new Adress();
        firstHotelAdress.setCity("FirstCity");
        firstHotelAdress.setStreet("FirstStreet");
        Hotel firstHotel = saveHotel("FirstHotel", firstHotelAdress, mySqlHotelDaoImpl);

        Adress secondAdress = new Adress();
        secondAdress.setCity("SecondCity");
        secondAdress.setStreet("SecondStreet");
        Hotel secondHotel = saveHotel("SecondHotel", secondAdress, mySqlHotelDaoImpl);

        Adress thirdAdress = new Adress();
        thirdAdress.setCity("ThirdCity");
        thirdAdress.setStreet("ThirdStreet");
        Hotel thirdHotel = saveHotel("ThirdHotel", thirdAdress, mySqlHotelDaoImpl);

        List<Hotel> hotels = new MySqlHotelDaoImpl().findAll();

        List<String> hotelNames = hotels.stream().map(Hotel::getName)
                .collect(toList());
        List<String> hotelAdresses = hotels.stream().map(Hotel::getAdress).map(Adress::getFullAdress).collect(toList());

        assertThat(hotels, hasSize(3));
        assertThat(hotelNames, containsInAnyOrder("FirstHotel", "SecondHotel", "ThirdHotel"));
        assertThat(hotelAdresses, containsInAnyOrder("FirstCity FirstStreet", "SecondCity SecondStreet", "ThirdCity ThirdStreet"));

        mySqlHotelDaoImpl.delete(firstHotel);
        mySqlHotelDaoImpl.delete(secondHotel);
        mySqlHotelDaoImpl.delete(thirdHotel);

    }


    private Hotel saveHotel(String name, Adress adress, MySqlHotelDaoImpl mySqlHotelDaoImpl) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setAdress(adress);
        mySqlHotelDaoImpl.create(hotel);
        return hotel;
    }

    @Override
    public Hotel getEntity() {
        Hotel hotel = new Hotel();
        return hotel;
    }

    @Override
    public BaseDaoImpl<Hotel> getCurrentDao() {
        return new MySqlHotelDaoImpl();
    }

    @Override
    public void testUpdate() {
        MySqlHotelDaoImpl mySqlHotelDaoImpl = new MySqlHotelDaoImpl();
        Hotel hotel = saveHotel("FirstHotel", null, mySqlHotelDaoImpl);
        Long id = hotel.getId();

        hotel.setName("New name");
        mySqlHotelDaoImpl.update(hotel);

        hotel = mySqlHotelDaoImpl.find(id);

        assertEquals("New name", hotel.getName());

        mySqlHotelDaoImpl.delete(hotel);

    }
}
