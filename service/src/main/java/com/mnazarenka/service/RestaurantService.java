package com.mnazarenka.service;

import com.mnazarenka.dao.entity.Restaurant;
import com.mnazarenka.service.common.BaseService;

public interface RestaurantService extends BaseService<Restaurant> {

    Restaurant createRestaurantWithHotelId(Restaurant restaurant, long hotelId);

    void UpdateWithHotelId(Restaurant restaurant, long hotelId);
}
