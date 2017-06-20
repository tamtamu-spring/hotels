package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Dish;
import com.mnazarenka.dao.entity.DishOrder;
import com.mnazarenka.dao.entity.DishOrderDetails;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.mysql.MySqlDishDaoImpl;
import com.mnazarenka.dao.mysql.MySqlDishOrderDaoImpl;
import com.mnazarenka.dao.mysql.MySqlDishOrderDetailsDaoImpl;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class DishOrderDetailsDaoTest extends BaseDaoTest<DishOrderDetails> {


    @Test
    public void testFindAll() {
        MySqlDishOrderDetailsDaoImpl mySqlDishOrderDetailsDaoImpl = new MySqlDishOrderDetailsDaoImpl();
        MySqlDishOrderDaoImpl mySqlDishOrderDaoImpl = new MySqlDishOrderDaoImpl();
        MySqlDishDaoImpl mySqlDishDaoImpl = new MySqlDishDaoImpl();

        Dish dish = new Dish();
        mySqlDishDaoImpl.create(dish);

        DishOrder dishOrder = new DishOrder();
        mySqlDishOrderDaoImpl.create(dishOrder);

        DishOrderDetails firstDetail = saveDishOrderDetail(dish, dishOrder, 1, mySqlDishOrderDetailsDaoImpl);
        DishOrderDetails secondDetail = saveDishOrderDetail(dish, dishOrder, 2, mySqlDishOrderDetailsDaoImpl);


        List<DishOrderDetails> details = mySqlDishOrderDetailsDaoImpl.findAll();
        List<DishOrder> dishOrders = details.stream().map(DishOrderDetails::getOrder)
                .collect(toList());
        List<Dish> dishes = details.stream().map(DishOrderDetails::getDish).collect(toList());
        List<Integer> counts = details.stream().map(DishOrderDetails::getCount).collect(toList());

        assertThat(details, hasSize(2));
        dishes.forEach(d -> assertNotNull(d));
        dishOrders.forEach(o -> assertNotNull(o));
        assertThat(counts, containsInAnyOrder(1, 2));

        mySqlDishOrderDetailsDaoImpl.delete(firstDetail);
        mySqlDishOrderDetailsDaoImpl.delete(secondDetail);
        mySqlDishOrderDaoImpl.delete(dishOrder);
        mySqlDishDaoImpl.delete(dish);
    }


    @Override
    public DishOrderDetails getEntity() {
        return new DishOrderDetails();
    }

    @Override
    public BaseDaoImpl<DishOrderDetails> getCurrentDao() {
        return new MySqlDishOrderDetailsDaoImpl();
    }

    @Override
    public void testUpdate() {
        MySqlDishOrderDetailsDaoImpl mySqlDishOrderDetailsDaoImpl = new MySqlDishOrderDetailsDaoImpl();
        DishOrderDetails dishOrderDetails = saveDishOrderDetail(null, null, 3, mySqlDishOrderDetailsDaoImpl);

        Long id = dishOrderDetails.getId();
        dishOrderDetails.setCount(5);
        mySqlDishOrderDetailsDaoImpl.update(dishOrderDetails);

        dishOrderDetails = mySqlDishOrderDetailsDaoImpl.find(id);

        assertEquals(5, (long) dishOrderDetails.getCount());

        mySqlDishOrderDetailsDaoImpl.delete(dishOrderDetails);

    }

    private DishOrderDetails saveDishOrderDetail(Dish dish, DishOrder order, int count, MySqlDishOrderDetailsDaoImpl mySqlDishOrderDetailsDaoImpl) {
        DishOrderDetails dishOrderDetails = new DishOrderDetails();
        dishOrderDetails.setDish(dish);
        dishOrderDetails.setOrder(order);
        dishOrderDetails.setCount(count);
        mySqlDishOrderDetailsDaoImpl.create(dishOrderDetails);
        return dishOrderDetails;
    }
}

