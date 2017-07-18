package com.mnazarenka.service.impl;

import com.mnazarenka.dao.HotelDao;
import com.mnazarenka.dao.RestaurantDao;
import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.Restaurant;
import com.mnazarenka.service.RestaurantService;
import com.mnazarenka.service.common.BaseServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl extends BaseServiceImpl<Restaurant> implements RestaurantService {
    @Getter
    private RestaurantDao dao;
    private HotelDao hotelDao;

    @Autowired
    public RestaurantServiceImpl(RestaurantDao dao, HotelDao hotelDao) {
        this.dao = dao;
        this.hotelDao = hotelDao;
    }

    @Override
    public Restaurant createRestaurantWithHotelId(Restaurant restaurant, long hotelId) {
        Hotel hotel = hotelDao.find(hotelId);
        restaurant.setHotel(hotel);

        return dao.create(restaurant);
    }

    @Override
    public void UpdateWithHotelId(Restaurant restaurant, long hotelId) {
        Hotel hotel = hotelDao.find(hotelId);
        restaurant.setHotel(hotel);

        dao.update(restaurant);
    }
}
