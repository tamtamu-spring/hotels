package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.Restaurant;
import com.mnazarenka.util.TestDataImporter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class RestaurantDaoTest extends BaseDaoTest<Restaurant, RestaurantDao> {
    @Autowired
    private RestaurantDao restaurantDao;

    @Test
    public void testFindAll() {
        List<Restaurant> restaurants = restaurantDao.findAll();

        List<String> restaurantsNames = restaurants.stream().map(Restaurant::getName)
                .collect(toList());
        List<Hotel> hotels = restaurants.stream().map(Restaurant::getHotel).collect(toList());

        assertThat(restaurants, hasSize(2));
        assertThat(hotels, hasSize(2));
        assertThat(restaurantsNames, containsInAnyOrder("FirstRestaurant", "SecondRestaurant"));
    }

  /*  @Override
    public Restaurant getEntity() {
        return new Restaurant();
    }

    @Override
    public BaseDaoImpl<Restaurant> getCurrentDao() {
        return new MySqlRestaurantDaoImpl();
    }*/

    @Override
    public void testUpdate() {
        Restaurant restaurant = getTestDataImporter().saveRestaurant("Restaurant", null, restaurantDao);

        Long id = restaurant.getId();
        restaurant.setName("New name");
        restaurantDao.update(restaurant);

        restaurant = restaurantDao.find(id);

        assertEquals("New name", restaurant.getName());
    }
}
