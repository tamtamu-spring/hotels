package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Dish;
import com.mnazarenka.dao.entity.DishOrder;
import com.mnazarenka.dao.entity.DishOrderDetails;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.mysql.MySqlDishDao;
import com.mnazarenka.dao.mysql.MySqlDishOrderDao;
import com.mnazarenka.dao.mysql.MySqlDishOrderDetailsDao;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class DishOrderDetailsDaoTest extends BaseDaoTest<DishOrderDetails> {


    @Test
    public void testFindAll() {
        MySqlDishOrderDetailsDao mySqlDishOrderDetailsDao = new MySqlDishOrderDetailsDao();
        MySqlDishOrderDao mySqlDishOrderDao = new MySqlDishOrderDao();
        MySqlDishDao mySqlDishDao = new MySqlDishDao();

        Dish dish = new Dish();
        mySqlDishDao.create(dish);

        DishOrder dishOrder = new DishOrder();
        mySqlDishOrderDao.create(dishOrder);

        DishOrderDetails firstDetail = saveDishOrderDetail(dish, dishOrder, 1, mySqlDishOrderDetailsDao);
        DishOrderDetails secondDetail = saveDishOrderDetail(dish, dishOrder, 2, mySqlDishOrderDetailsDao);


        List<DishOrderDetails> details = mySqlDishOrderDetailsDao.findAll();
        List<DishOrder> dishOrders = details.stream().map(DishOrderDetails::getOrder)
                .collect(toList());
        List<Dish> dishes = details.stream().map(DishOrderDetails::getDish).collect(toList());
        List<Integer> counts = details.stream().map(DishOrderDetails::getCount).collect(toList());

        assertThat(details, hasSize(2));
        dishes.forEach(d -> assertNotNull(d));
        dishOrders.forEach(o -> assertNotNull(o));
        assertThat(counts, containsInAnyOrder(1, 2));

        mySqlDishOrderDetailsDao.delete(firstDetail);
        mySqlDishOrderDetailsDao.delete(secondDetail);
        mySqlDishOrderDao.delete(dishOrder);
        mySqlDishDao.delete(dish);
    }


    @Override
    public DishOrderDetails getEntity() {
        return new DishOrderDetails();
    }

    @Override
    public BaseDaoImpl<DishOrderDetails> getCurrentDao() {
        return new MySqlDishOrderDetailsDao();
    }

    @Override
    public void testUpdate() {
        MySqlDishOrderDetailsDao mySqlDishOrderDetailsDao = new MySqlDishOrderDetailsDao();
        DishOrderDetails dishOrderDetails = saveDishOrderDetail(null, null, 3, mySqlDishOrderDetailsDao);

        Long id = dishOrderDetails.getId();
        dishOrderDetails.setCount(5);
        mySqlDishOrderDetailsDao.update(dishOrderDetails);

        dishOrderDetails = mySqlDishOrderDetailsDao.find(id);

        assertEquals(5, (long) dishOrderDetails.getCount());

        mySqlDishOrderDetailsDao.delete(dishOrderDetails);

    }

    private DishOrderDetails saveDishOrderDetail(Dish dish, DishOrder order, int count, MySqlDishOrderDetailsDao mySqlDishOrderDetailsDao) {
        DishOrderDetails dishOrderDetails = new DishOrderDetails();
        dishOrderDetails.setDish(dish);
        dishOrderDetails.setOrder(order);
        dishOrderDetails.setCount(count);
        mySqlDishOrderDetailsDao.create(dishOrderDetails);
        return dishOrderDetails;
    }
}

