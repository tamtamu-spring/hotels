package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Dish;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.mysql.MySqlDishDaoImpl;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class DishDaoTest extends BaseDaoTest<Dish> {

    @Test
    public void testFindAll() {

        MySqlDishDaoImpl mySqlDishDaoImpl = new MySqlDishDaoImpl();

        Dish firstDish = saveDish("FirstDish", mySqlDishDaoImpl);
        Dish secondDish = saveDish("SecondDish", mySqlDishDaoImpl);


        List<Dish> hotels = mySqlDishDaoImpl.findAll();

        List<String> dishNames = hotels.stream().map(Dish::getName)
                .collect(toList());

        assertThat(hotels, hasSize(2));
        assertThat(dishNames, containsInAnyOrder("FirstDish", "SecondDish"));

        mySqlDishDaoImpl.delete(firstDish);
        mySqlDishDaoImpl.delete(secondDish);

    }

    @Override
    public Dish getEntity() {
        return new Dish();
    }

    @Override
    public BaseDaoImpl<Dish> getCurrentDao() {
        return new MySqlDishDaoImpl();
    }

    @Override
    public void testUpdate() {
        MySqlDishDaoImpl mySqlDishDaoImpl = new MySqlDishDaoImpl();
        Dish dish = saveDish("Dish", mySqlDishDaoImpl);

        Long id = dish.getId();
        dish.setName("New name");
        mySqlDishDaoImpl.update(dish);

        dish = mySqlDishDaoImpl.find(id);

        assertEquals("New name", dish.getName());

        mySqlDishDaoImpl.delete(dish);
    }

    private Dish saveDish(String name, MySqlDishDaoImpl mySqlDishDaoImpl) {
        Dish dish = new Dish();
        dish.setName(name);
        mySqlDishDaoImpl.create(dish);
        return dish;
    }
}
