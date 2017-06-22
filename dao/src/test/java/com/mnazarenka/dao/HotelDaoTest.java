package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Adress;
import com.mnazarenka.dao.entity.Hotel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class HotelDaoTest extends BaseDaoTest<Hotel, HotelDao> {
    @Autowired
    private HotelDao hotelDao;

    @Test
    public void findAdressByHotelIdTest() {

        Adress firstHotelAdress = new Adress();
        firstHotelAdress.setCity("FirstCity");
        firstHotelAdress.setStreet("FirstStreet");
        Hotel hotel = saveHotel("FirstHotel", firstHotelAdress, hotelDao);

        Long id = hotel.getId();
        Adress adress = hotelDao.getAdressByHotelId(id);

        assertEquals("FirstCity FirstStreet", adress.getFullAdress());

        hotelDao.delete(hotel);
    }

    @Test
    public void testFindAll() {

        Adress firstHotelAdress = new Adress();
        firstHotelAdress.setCity("FirstCity");
        firstHotelAdress.setStreet("FirstStreet");
        Hotel firstHotel = saveHotel("FirstHotel", firstHotelAdress, hotelDao);

        Adress secondAdress = new Adress();
        secondAdress.setCity("SecondCity");
        secondAdress.setStreet("SecondStreet");
        Hotel secondHotel = saveHotel("SecondHotel", secondAdress, hotelDao);

        Adress thirdAdress = new Adress();
        thirdAdress.setCity("ThirdCity");
        thirdAdress.setStreet("ThirdStreet");
        Hotel thirdHotel = saveHotel("ThirdHotel", thirdAdress, hotelDao);

        List<Hotel> hotels = hotelDao.findAll();

        List<String> hotelNames = hotels.stream().map(Hotel::getName)
                .collect(toList());
        List<String> hotelAdresses = hotels.stream().map(Hotel::getAdress).map(Adress::getFullAdress).collect(toList());

        assertThat(hotels, hasSize(3));
        assertThat(hotelNames, containsInAnyOrder("FirstHotel", "SecondHotel", "ThirdHotel"));
        assertThat(hotelAdresses, containsInAnyOrder("FirstCity FirstStreet", "SecondCity SecondStreet", "ThirdCity ThirdStreet"));

        hotelDao.delete(firstHotel);
        hotelDao.delete(secondHotel);
        hotelDao.delete(thirdHotel);

    }


    private Hotel saveHotel(String name, Adress adress, HotelDao hotelDao) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setAdress(adress);
        hotelDao.create(hotel);
        return hotel;
    }

    /*@Override
    public Hotel getEntity() {
        Hotel hotel = new Hotel();
        return hotel;
    }

    @Override
    public BaseDaoImpl<Hotel> getCurrentDao() {
        return new MySqlHotelDaoImpl();
    }*/

    @Override
    public void testUpdate() {
        Hotel hotel = saveHotel("FirstHotel", null, hotelDao);
        Long id = hotel.getId();

        hotel.setName("New name");
        hotelDao.update(hotel);

        hotel = hotelDao.find(id);

        assertEquals("New name", hotel.getName());

        hotelDao.delete(hotel);

    }
}
