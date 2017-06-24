package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Adress;
import com.mnazarenka.dao.entity.Hotel;
import lombok.Getter;
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
    @Getter
    private HotelDao dao;

    @Test
    public void findAdressByHotelIdTest() {

        Adress firstHotelAdress = new Adress();
        firstHotelAdress.setCity("FirstCity");
        firstHotelAdress.setStreet("FirstStreet");
        Hotel hotel = getTestDataImporter().saveHotel("FirstHotel", firstHotelAdress, dao);

        Long id = hotel.getId();
        Adress adress = dao.getAdressByHotelId(id);

        assertEquals("FirstCity FirstStreet", adress.getFullAdress());
    }

    @Test
    public void testFindAll() {
        List<Hotel> hotels = dao.findAll();

        List<String> hotelNames = hotels.stream().map(Hotel::getName)
                .collect(toList());
        List<String> hotelAdresses = hotels.stream().map(Hotel::getAdress).map(Adress::getFullAdress).collect(toList());

        assertThat(hotels, hasSize(3));
        assertThat(hotelNames, containsInAnyOrder("FirstHotel", "SecondHotel", "ThirdHotel"));
        assertThat(hotelAdresses, containsInAnyOrder("FirstCity FirstStreet", "SecondCity SecondStreet", "ThirdCity ThirdStreet"));
    }

    @Override
    public void testUpdate() {
        Hotel hotel = getTestDataImporter().saveHotel("FirstHotel", null, dao);
        Long id = hotel.getId();

        hotel.setName("New name");
        dao.update(hotel);

        hotel = dao.find(id);

        assertEquals("New name", hotel.getName());
    }
}
