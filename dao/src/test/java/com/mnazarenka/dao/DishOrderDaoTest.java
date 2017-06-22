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
    @Autowired
    private UserDao userDao;
    @Autowired
    private AppartmentDao appartmentDao;

    @Test
    public void testFindAll() {

        User user = new User();
        Appartment appartment = new Appartment();
        user = userDao.create(user);
        appartment = appartmentDao.create(appartment);

        DishOrder firstDishOrder = saveDishOrder(user, appartment, LocalDateTime.of(LocalDate.now(), LocalTime.MAX), dishOrderDao);
        DishOrder secondDishOrder = saveDishOrder(user, appartment, LocalDateTime.of(LocalDate.now(), LocalTime.MIN), dishOrderDao);


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

        dishOrderDao.delete(firstDishOrder);
        dishOrderDao.delete(secondDishOrder);
        appartmentDao.delete(appartment);
        userDao.delete(user);
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
        DishOrder dishOrder = saveDishOrder(null, null, LocalDateTime.of(2017, 10, 10, 10, 10), dishOrderDao);

        Long id = dishOrder.getId();
        dishOrder.setOrderTime(LocalDateTime.of(2017, 10, 10, 10, 15));
        dishOrderDao.update(dishOrder);

        dishOrder = dishOrderDao.find(id);

        assertEquals(LocalDateTime.of(2017, 10, 10, 10, 15), dishOrder.getOrderTime());

        dishOrderDao.delete(dishOrder);

    }

    private DishOrder saveDishOrder(User user, Appartment apartment, LocalDateTime time, DishOrderDao dishOrderDao) {
        DishOrder dishOrder = new DishOrder();
        dishOrder.setUser(user);
        dishOrder.setAppartment(apartment);
        dishOrder.setOrderTime(time);
        dishOrderDao.create(dishOrder);
        return dishOrder;
    }
}
