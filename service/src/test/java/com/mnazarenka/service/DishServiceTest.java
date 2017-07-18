package com.mnazarenka.service;

import com.mnazarenka.dao.entity.Dish;
import lombok.Getter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Created by Николай on 18.07.2017.
 */
public class DishServiceTest extends BaseServiceTest<Dish> {
    @Getter
    @Autowired
    private DishServise service;

    @Override
    public void testFindAll() {
        List<Dish> dishes = service.findAll();
        assertThat(dishes, hasSize(2));
    }

}
