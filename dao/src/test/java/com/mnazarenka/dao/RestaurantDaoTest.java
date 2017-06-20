package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.Restaurant;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.mysql.MySqlHotelDaoImpl;
import com.mnazarenka.dao.mysql.MySqlRestaurantDaoImpl;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class RestaurantDaoTest extends BaseDaoTest<Restaurant> {


    @Test
    public void testFindAll() {
        MySqlRestaurantDaoImpl mySqlRestaurantDaoImpl = new MySqlRestaurantDaoImpl();

        MySqlHotelDaoImpl mySqlHotelDaoImpl = new MySqlHotelDaoImpl();
        Hotel hotel = new Hotel();
        hotel = mySqlHotelDaoImpl.create(hotel);

        Restaurant firstRestaurant = saveRestaurant("FirstRestaurant", hotel, mySqlRestaurantDaoImpl);
        Restaurant secondRestaurant = saveRestaurant("SecondRestaurant", hotel, mySqlRestaurantDaoImpl);

        List<Restaurant> restaurants = mySqlRestaurantDaoImpl.findAll();

        List<String> restaurantsNames = restaurants.stream().map(Restaurant::getName)
                .collect(toList());
        List<Hotel> hotels = restaurants.stream().map(Restaurant::getHotel).collect(toList());

        assertThat(restaurants, hasSize(2));
        assertThat(hotels, hasSize(2));
        assertThat(restaurantsNames, containsInAnyOrder("FirstRestaurant", "SecondRestaurant"));

        mySqlRestaurantDaoImpl.delete(firstRestaurant);
        mySqlRestaurantDaoImpl.delete(secondRestaurant);

        mySqlHotelDaoImpl.delete(hotel);

    }

    @Override
    public Restaurant getEntity() {
        return new Restaurant();
    }

    @Override
    public BaseDaoImpl<Restaurant> getCurrentDao() {
        return new MySqlRestaurantDaoImpl();
    }

    @Override
    public void testUpdate() {
        MySqlRestaurantDaoImpl mySqlRestaurantDaoImpl = new MySqlRestaurantDaoImpl();
        Restaurant restaurant = saveRestaurant("Restaurant", null, mySqlRestaurantDaoImpl);

        Long id = restaurant.getId();
        restaurant.setName("New name");
        mySqlRestaurantDaoImpl.update(restaurant);

        restaurant = mySqlRestaurantDaoImpl.find(id);

        assertEquals("New name", restaurant.getName());

        mySqlRestaurantDaoImpl.delete(restaurant);

    }

    private Restaurant saveRestaurant(String name, Hotel hotel, MySqlRestaurantDaoImpl mySqlRestaurantDaoImpl) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setHotel(hotel);
        mySqlRestaurantDaoImpl.create(restaurant);
        return restaurant;
    }
}
