package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.Restaurant;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.mysql.MySqlHotelDao;
import com.mnazarenka.dao.mysql.MySqlRestaurantDao;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class RestaurantDaoTest extends BaseDaoTest<Restaurant> {


    @Test
    public void testFindAll() {
        MySqlRestaurantDao mySqlRestaurantDao = new MySqlRestaurantDao();

        MySqlHotelDao mySqlHotelDao = new MySqlHotelDao();
        Hotel hotel = new Hotel();
        hotel = mySqlHotelDao.create(hotel);

        Restaurant firstRestaurant = saveRestaurant("FirstRestaurant", hotel, mySqlRestaurantDao);
        Restaurant secondRestaurant = saveRestaurant("SecondRestaurant", hotel, mySqlRestaurantDao);

        List<Restaurant> restaurants = mySqlRestaurantDao.findAll();

        List<String> restaurantsNames = restaurants.stream().map(Restaurant::getName)
                .collect(toList());
        List<Hotel> hotels = restaurants.stream().map(Restaurant::getHotel).collect(toList());

        assertThat(restaurants, hasSize(2));
        assertThat(hotels, hasSize(2));
        assertThat(restaurantsNames, containsInAnyOrder("FirstRestaurant", "SecondRestaurant"));

        mySqlRestaurantDao.delete(firstRestaurant);
        mySqlRestaurantDao.delete(secondRestaurant);

        mySqlHotelDao.delete(hotel);

    }

    @Override
    public Restaurant getEntity() {
        return new Restaurant();
    }

    @Override
    public BaseDaoImpl<Restaurant> getCurrentDao() {
        return new MySqlRestaurantDao();
    }

    @Override
    public void testUpdate() {
        MySqlRestaurantDao mySqlRestaurantDao = new MySqlRestaurantDao();
        Restaurant restaurant = saveRestaurant("Restaurant", null, mySqlRestaurantDao);

        Long id = restaurant.getId();
        restaurant.setName("New name");
        mySqlRestaurantDao.update(restaurant);

        restaurant = mySqlRestaurantDao.find(id);

        assertEquals("New name", restaurant.getName());

        mySqlRestaurantDao.delete(restaurant);

    }

    private Restaurant saveRestaurant(String name, Hotel hotel, MySqlRestaurantDao mySqlRestaurantDao) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setHotel(hotel);
        mySqlRestaurantDao.create(restaurant);
        return restaurant;
    }
}
