package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.dao.common.BaseDaoImpl;
import com.mnazarenka.dao.mysql.MySqlAppartmentOderDaoImpl;
import com.mnazarenka.dao.mysql.MySqlAppartmentsDaoImpl;
import com.mnazarenka.dao.mysql.MySqlUserDaoImpl;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;


public class OrderDaoTest extends BaseDaoTest<AppartmentOrder> {


    @Test
    public void testFindAll() {
        MySqlAppartmentOderDaoImpl mySqlAppartmentOderDaoImpl = new MySqlAppartmentOderDaoImpl();
        MySqlUserDaoImpl mySqlUserDaoImpl = new MySqlUserDaoImpl();
        MySqlAppartmentsDaoImpl mySqlAppartmentsDaoImpl = new MySqlAppartmentsDaoImpl();

        User user = new User();
        mySqlUserDaoImpl.create(user);
        Appartment appartment = new Appartment();
        mySqlAppartmentsDaoImpl.create(appartment);

        AppartmentOrder firstOrder = saveOrder(user, appartment,
                LocalDate.of(2017, 10, 10), LocalDate.of(2017, 10, 15), mySqlAppartmentOderDaoImpl);
        AppartmentOrder secondOrder = saveOrder(user, appartment,
                LocalDate.of(2017, 11, 20), LocalDate.of(2017, 11, 25), mySqlAppartmentOderDaoImpl);

        List<AppartmentOrder> orders = mySqlAppartmentOderDaoImpl.findAll();

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

        mySqlAppartmentOderDaoImpl.delete(firstOrder);
        mySqlAppartmentOderDaoImpl.delete(secondOrder);

        mySqlAppartmentsDaoImpl.delete(appartment);
        mySqlUserDaoImpl.delete(user);

    }


    @Override
    public AppartmentOrder getEntity() {
        return new AppartmentOrder();
    }

    @Override
    public BaseDaoImpl<AppartmentOrder> getCurrentDao() {
        return new MySqlAppartmentOderDaoImpl();
    }

    @Override
    public void testUpdate() {
        MySqlAppartmentOderDaoImpl mySqlAppartmentOderDaoImpl = new MySqlAppartmentOderDaoImpl();
        AppartmentOrder order = saveOrder(null, null,
                LocalDate.of(2017, 10, 10), LocalDate.of(2017, 10, 15), mySqlAppartmentOderDaoImpl);

        Long id = order.getId();
        order.setStartDate(LocalDate.of(2017, 10, 1));
        mySqlAppartmentOderDaoImpl.update(order);

        order = mySqlAppartmentOderDaoImpl.find(id);

        assertEquals(LocalDate.of(2017, 10, 1), order.getStartDate());

        mySqlAppartmentOderDaoImpl.delete(order);
    }

    private AppartmentOrder saveOrder(User user, Appartment appartment, LocalDate startDate, LocalDate endDate, MySqlAppartmentOderDaoImpl mySqlAppartmentOderDaoImpl) {
        AppartmentOrder order = new AppartmentOrder();
        order.setUser(user);
        order.setAppartment(appartment);
        order.setStartDate(startDate);
        order.setEndDate(endDate);
        mySqlAppartmentOderDaoImpl.create(order);
        return order;
    }
}
