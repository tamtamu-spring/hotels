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


        Dish firstDish = saveDish("FirstDish", dishDao);
        Dish secondDish = saveDish("SecondDish", dishDao);


        List<Dish> hotels = dishDao.findAll();

        List<String> dishNames = hotels.stream().map(Dish::getName)
                .collect(toList());

        assertThat(hotels, hasSize(2));
        assertThat(dishNames, containsInAnyOrder("FirstDish", "SecondDish"));

        dishDao.delete(firstDish);
        dishDao.delete(secondDish);

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
        Dish dish = saveDish("Dish", dishDao);

        Long id = dish.getId();
        dish.setName("New name");
        dishDao.update(dish);

        dish = dishDao.find(id);

        assertEquals("New name", dish.getName());

        dishDao.delete(dish);
    }

    private Dish saveDish(String name, DishDao dishDao) {
        Dish dish = new Dish();
        dish.setName(name);
        dishDao.create(dish);
        return dish;
    }
}
