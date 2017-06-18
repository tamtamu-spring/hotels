package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Dish;
import com.mnazarenka.dao.mysql.BaseDao;
import com.mnazarenka.dao.mysql.MySqlDishDao;
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

        MySqlDishDao mySqlDishDao = new MySqlDishDao();

        Dish firstDish = saveDish("FirstDish", mySqlDishDao);
        Dish secondDish = saveDish("SecondDish", mySqlDishDao);


        List<Dish> hotels = mySqlDishDao.findAll();

        List<String> dishNames = hotels.stream().map(Dish::getName)
                .collect(toList());

        assertThat(hotels, hasSize(2));
        assertThat(dishNames, containsInAnyOrder("FirstDish", "SecondDish"));

        mySqlDishDao.delete(firstDish);
        mySqlDishDao.delete(secondDish);

    }

    @Override
    public Dish getEntity() {
        return new Dish();
    }

    @Override
    public BaseDao<Dish> getCurrentDao() {
        return new MySqlDishDao();
    }

    @Override
    public void testUpdate() {
        MySqlDishDao mySqlDishDao = new MySqlDishDao();
        Dish dish = saveDish("Dish", mySqlDishDao);

        Long id = dish.getId();
        dish.setName("New name");
        mySqlDishDao.update(dish);

        dish = mySqlDishDao.find(id);

        assertEquals("New name", dish.getName());

        mySqlDishDao.delete(dish);
    }

    private Dish saveDish(String name, MySqlDishDao mySqlDishDao) {
        Dish dish = new Dish();
        dish.setName(name);
        mySqlDishDao.create(dish);
        return dish;
    }
}
