package com.mnazarenka.service;

import com.mnazarenka.dao.entity.Restaurant;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class RestaurantServiceTest extends BaseServiceTest<Restaurant> {
    @Autowired
    @Getter
    private RestaurantService service;

    @Override
    public void testFindAll() {
        List<Restaurant> restaurants = service.findAll();
        assertThat(restaurants, hasSize(2));
    }

}
