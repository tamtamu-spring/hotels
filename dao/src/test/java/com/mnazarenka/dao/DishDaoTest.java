package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Dish;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class DishDaoTest extends BaseDaoTest<Dish, DishDao> {
    @Autowired
    private DishDao dishDao;

    @Test
    public void testFindAll() {
        List<Dish> hotels = dishDao.findAll();

        List<String> dishNames = hotels.stream().map(Dish::getName)
                .collect(toList());

        assertThat(hotels, hasSize(2));
        assertThat(dishNames, containsInAnyOrder("FirstDish", "SecondDish"));
    }

/*    @Override
    public Dish getEntity() {
        return new Dish();
    }

    @Override
    public BaseDaoImpl<Dish> getCurrentDao() {
        return new MySqlDishDaoImpl();
    }*/

    @Override
    public void testUpdate() {
        Dish dish = getTestDataImporter().saveDish("Dish", dishDao);

        Long id = dish.getId();
        dish.setName("New name");
        dishDao.update(dish);

        dish = dishDao.find(id);

        assertEquals("New name", dish.getName());
    }
}
