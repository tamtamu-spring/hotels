package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.DishOrder;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.mysql.MySqlAppartmentDaoImpl;
import com.mnazarenka.dao.mysql.MySqlDishOrderDaoImpl;
import com.mnazarenka.dao.mysql.MySqlUserDaoImpl;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class DishOrderDaoTest extends BaseDaoTest<DishOrder> {


    @Test
    public void testFindAll() {
        MySqlDishOrderDaoImpl mySqlDishOrderDaoImpl = new MySqlDishOrderDaoImpl();
        MySqlUserDaoImpl mySqlUserDaoImpl = new MySqlUserDaoImpl();
        MySqlAppartmentDaoImpl mySqlAppartmentDaoImpl = new MySqlAppartmentDaoImpl();

        User user = new User();
        Appartment appartment = new Appartment();
        user = mySqlUserDaoImpl.create(user);
        appartment = mySqlAppartmentDaoImpl.create(appartment);

        DishOrder firstDishOrder = saveDishOrder(user, appartment, LocalDateTime.of(LocalDate.now(), LocalTime.MAX), mySqlDishOrderDaoImpl);
        DishOrder secondDishOrder = saveDishOrder(user, appartment, LocalDateTime.of(LocalDate.now(), LocalTime.MIN), mySqlDishOrderDaoImpl);


        List<DishOrder> orders = mySqlDishOrderDaoImpl.findAll();

        List<LocalDateTime> dishOrderTimes = orders.stream().map(DishOrder::getOrderTime)
                .collect(toList());

        List<User> users = orders.stream().map(DishOrder::getUser).collect(toList());
        List<Appartment> appartments = orders.stream().map(DishOrder::getAppartment).collect(toList());

        assertThat(orders, hasSize(2));
        assertThat(dishOrderTimes, containsInAnyOrder(LocalDateTime.of(LocalDate.now(), LocalTime.MAX),
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN)));
        users.forEach(u -> assertNotNull(u));
        appartments.forEach(a -> assertNotNull(a));

        mySqlDishOrderDaoImpl.delete(firstDishOrder);
        mySqlDishOrderDaoImpl.delete(secondDishOrder);
        mySqlAppartmentDaoImpl.delete(appartment);
        mySqlUserDaoImpl.delete(user);
    }


    @Override
    public DishOrder getEntity() {
        return new DishOrder();
    }

    @Override
    public BaseDaoImpl<DishOrder> getCurrentDao() {
        return new MySqlDishOrderDaoImpl();
    }

    @Override
    public void testUpdate() {
        MySqlDishOrderDaoImpl mySqlDishOrderDaoImpl = new MySqlDishOrderDaoImpl();
        DishOrder dishOrder = saveDishOrder(null, null, LocalDateTime.of(2017, 10, 10, 10, 10), mySqlDishOrderDaoImpl);

        Long id = dishOrder.getId();
        dishOrder.setOrderTime(LocalDateTime.of(2017, 10, 10, 10, 15));
        mySqlDishOrderDaoImpl.update(dishOrder);

        dishOrder = mySqlDishOrderDaoImpl.find(id);

        assertEquals(LocalDateTime.of(2017, 10, 10, 10, 15), dishOrder.getOrderTime());

        mySqlDishOrderDaoImpl.delete(dishOrder);

    }

    private DishOrder saveDishOrder(User user, Appartment apartment, LocalDateTime time, MySqlDishOrderDaoImpl mySqlDishOrderDaoImpl) {
        DishOrder dishOrder = new DishOrder();
        dishOrder.setUser(user);
        dishOrder.setAppartment(apartment);
        dishOrder.setOrderTime(time);
        mySqlDishOrderDaoImpl.create(dishOrder);
        return dishOrder;
    }
}
