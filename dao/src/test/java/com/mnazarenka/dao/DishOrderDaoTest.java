package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.DishOrder;
import com.mnazarenka.dao.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class DishOrderDaoTest extends BaseDaoTest<DishOrder, DishOrderDao> {
    @Autowired
    private DishOrderDao dishOrderDao;

    @Test
    public void testFindAll() {
        List<DishOrder> orders = dishOrderDao.findAll();

        List<LocalDateTime> dishOrderTimes = orders.stream().map(DishOrder::getOrderTime)
                .collect(toList());

        List<User> users = orders.stream().map(DishOrder::getUser).collect(toList());
        List<Appartment> appartments = orders.stream().map(DishOrder::getAppartment).collect(toList());

        assertThat(orders, hasSize(2));
        assertThat(dishOrderTimes, containsInAnyOrder(LocalDateTime.of(LocalDate.now(), LocalTime.MAX),
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN)));
        users.forEach(u -> assertNotNull(u));
        appartments.forEach(a -> assertNotNull(a));
    }

  /*  @Override
    public DishOrder getEntity() {
        return new DishOrder();
    }

    @Override
    public BaseDaoImpl<DishOrder> getCurrentDao() {
        return new MySqlDishOrderDaoImpl();
    }*/

    @Override
    public void testUpdate() {
        DishOrder dishOrder = getTestDataImporter().saveDishOrder(null, null, LocalDateTime.of(2017, 10, 10, 10, 10), dishOrderDao);

        Long id = dishOrder.getId();
        dishOrder.setOrderTime(LocalDateTime.of(2017, 10, 10, 10, 15));
        dishOrderDao.update(dishOrder);

        dishOrder = dishOrderDao.find(id);

        assertEquals(LocalDateTime.of(2017, 10, 10, 10, 15), dishOrder.getOrderTime());
    }
}
