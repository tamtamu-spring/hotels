package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Dish;
import com.mnazarenka.dao.entity.DishOrder;
import com.mnazarenka.dao.entity.DishOrderDetails;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class DishOrderDetailsDaoTest extends BaseDaoTest<DishOrderDetails, DishOrderDetailsDao> {
    @Autowired
    private DishOrderDetailsDao dishOrderDetailsDao;
    @Autowired
    private DishOrderDao dishOrderDao;
    @Autowired
    private DishDao dishDao;

    @Test
    public void testFindAll() {

        Dish dish = new Dish();
        dishDao.create(dish);

        DishOrder dishOrder = new DishOrder();
        dishOrderDao.create(dishOrder);

        DishOrderDetails firstDetail = saveDishOrderDetail(dish, dishOrder, 1, dishOrderDetailsDao);
        DishOrderDetails secondDetail = saveDishOrderDetail(dish, dishOrder, 2, dishOrderDetailsDao);


        List<DishOrderDetails> details = dishOrderDetailsDao.findAll();
        List<DishOrder> dishOrders = details.stream().map(DishOrderDetails::getOrder)
                .collect(toList());
        List<Dish> dishes = details.stream().map(DishOrderDetails::getDish).collect(toList());
        List<Integer> counts = details.stream().map(DishOrderDetails::getCount).collect(toList());

        assertThat(details, hasSize(2));
        dishes.forEach(d -> assertNotNull(d));
        dishOrders.forEach(o -> assertNotNull(o));
        assertThat(counts, containsInAnyOrder(1, 2));

        dishOrderDetailsDao.delete(firstDetail);
        dishOrderDetailsDao.delete(secondDetail);
        dishOrderDao.delete(dishOrder);
        dishDao.delete(dish);
    }


   /* @Override
    public DishOrderDetails getEntity() {
        return new DishOrderDetails();
    }

    @Override
    public BaseDaoImpl<DishOrderDetails> getCurrentDao() {
        return new MySqlDishOrderDetailsDaoImpl();
    }*/

    @Override
    public void testUpdate() {
        DishOrderDetails dishOrderDetails = saveDishOrderDetail(null, null, 3, dishOrderDetailsDao);

        Long id = dishOrderDetails.getId();
        dishOrderDetails.setCount(5);
        dishOrderDetailsDao.update(dishOrderDetails);

        dishOrderDetails = dishOrderDetailsDao.find(id);

        assertEquals(5, (long) dishOrderDetails.getCount());

        dishOrderDetailsDao.delete(dishOrderDetails);

    }

    private DishOrderDetails saveDishOrderDetail(Dish dish, DishOrder order, int count, DishOrderDetailsDao dishOrderDetailsDao) {
        DishOrderDetails dishOrderDetails = new DishOrderDetails();
        dishOrderDetails.setDish(dish);
        dishOrderDetails.setOrder(order);
        dishOrderDetails.setCount(count);
        dishOrderDetailsDao.create(dishOrderDetails);
        return dishOrderDetails;
    }
}

