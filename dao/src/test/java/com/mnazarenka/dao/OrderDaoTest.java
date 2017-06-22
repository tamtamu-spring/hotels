package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;


public class OrderDaoTest extends BaseDaoTest<AppartmentOrder, AppartmentOrderDao> {
    @Autowired
    private AppartmentOrderDao appartmentOrderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    AppartmentDao appartmentDao;

    @Test
    public void testFindAll() {
        User user = new User();
        userDao.create(user);
        Appartment appartment = new Appartment();
        appartmentDao.create(appartment);

        AppartmentOrder firstOrder = saveOrder(user, appartment,
                LocalDate.of(2017, 10, 10), LocalDate.of(2017, 10, 15), appartmentOrderDao);
        AppartmentOrder secondOrder = saveOrder(user, appartment,
                LocalDate.of(2017, 11, 20), LocalDate.of(2017, 11, 25), appartmentOrderDao);

        List<AppartmentOrder> orders = appartmentOrderDao.findAll();

        List<LocalDate> startDates = orders.stream().map(AppartmentOrder::getStartDate)
                .collect(toList());
        List<LocalDate> endDates = orders.stream().map(AppartmentOrder::getEndDate)
                .collect(toList());
        List<User> users = orders.stream().map(AppartmentOrder::getUser).collect(toList());
        List<Appartment> appartments = orders.stream().map(AppartmentOrder::getAppartment).collect(toList());

        assertThat(orders, hasSize(2));
        assertThat(startDates, containsInAnyOrder(LocalDate.of(2017, 10, 10), LocalDate.of(2017, 11, 20)));
        assertThat(endDates, containsInAnyOrder(LocalDate.of(2017, 10, 15), LocalDate.of(2017, 11, 25)));
        users.forEach(u -> assertNotNull(u));
        appartments.forEach(a -> assertNotNull(a));

        appartmentOrderDao.delete(firstOrder);
        appartmentOrderDao.delete(secondOrder);

        appartmentDao.delete(appartment);
        userDao.delete(user);

    }


   /* @Override
    public AppartmentOrder getEntity() {
        return new AppartmentOrder();
    }

    @Override
    public BaseDaoImpl<AppartmentOrder> getCurrentDao() {
        return new MySqlAppartmentOderDaoImpl();
    }*/

    @Override
    public void testUpdate() {
        AppartmentOrder order = saveOrder(null, null,
                LocalDate.of(2017, 10, 10), LocalDate.of(2017, 10, 15), appartmentOrderDao);

        Long id = order.getId();
        order.setStartDate(LocalDate.of(2017, 10, 1));
        appartmentOrderDao.update(order);

        order = appartmentOrderDao.find(id);

        assertEquals(LocalDate.of(2017, 10, 1), order.getStartDate());

        appartmentOrderDao.delete(order);
    }

    private AppartmentOrder saveOrder(User user, Appartment appartment, LocalDate startDate, LocalDate endDate, AppartmentOrderDao appartmentOrderDao) {
        AppartmentOrder order = new AppartmentOrder();
        order.setUser(user);
        order.setAppartment(appartment);
        order.setStartDate(startDate);
        order.setEndDate(endDate);
        appartmentOrderDao.create(order);
        return order;
    }
}
