package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.dao.mysql.BaseDao;
import com.mnazarenka.dao.mysql.MySqlAppartmentOderDao;
import com.mnazarenka.dao.mysql.MySqlAppartmentsDao;
import com.mnazarenka.dao.mysql.MySqlUserDao;
import com.mnazarenka.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


public class OrderDaoTest extends BaseDaoTest<AppartmentOrder> {

    @Before
    public void initDb() {
       }

    @Test
    public void findAllAppartmentsTest() {
        MySqlAppartmentOderDao mySqlAppartmentOderDao = new MySqlAppartmentOderDao();
        MySqlUserDao mySqlUserDao = new MySqlUserDao();
        MySqlAppartmentsDao mySqlAppartmentsDao = new MySqlAppartmentsDao();

        User user = new User();
        mySqlUserDao.create(user);
        Appartment appartment = new Appartment();
        mySqlAppartmentsDao.create(appartment);

        AppartmentOrder firstOrder = saveOrder(user, appartment,
                LocalDate.of(2017, 10, 10), LocalDate.of(2017, 10, 15), mySqlAppartmentOderDao );
        AppartmentOrder secondOrder = saveOrder(user, appartment,
                LocalDate.of(2017, 11, 20), LocalDate.of(2017, 11, 25), mySqlAppartmentOderDao );

        List<AppartmentOrder> orders = mySqlAppartmentOderDao.findAll();

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

            mySqlAppartmentOderDao.delete(firstOrder);
            mySqlAppartmentOderDao.delete(secondOrder);

            mySqlAppartmentsDao.delete(appartment);
            mySqlUserDao.delete(user);

    }

    @After
    public void destroy() {
        //sessionFactory.close();
    }

    @Override
    public AppartmentOrder getEntity() {
        return new AppartmentOrder();
    }

    @Override
    public BaseDao<AppartmentOrder> getCurrentDao() {
        return new MySqlAppartmentOderDao();
    }

    @Override
    public void testUpdate() {
        MySqlAppartmentOderDao mySqlAppartmentOderDao = new MySqlAppartmentOderDao();
        AppartmentOrder order = saveOrder(null, null,
                LocalDate.of(2017, 10, 10), LocalDate.of(2017, 10, 15), mySqlAppartmentOderDao);

        Long id = order.getId();
        order.setStartDate(LocalDate.of(2017, 10, 1));
        mySqlAppartmentOderDao.update(order);

        order = mySqlAppartmentOderDao.find(id);

        assertEquals(LocalDate.of(2017, 10, 1), order.getStartDate());

        mySqlAppartmentOderDao.delete(order);
    }
    private AppartmentOrder saveOrder(User user, Appartment appartment, LocalDate startDate, LocalDate endDate, MySqlAppartmentOderDao mySqlAppartmentOderDao) {
        AppartmentOrder order = new AppartmentOrder();
        order.setUser(user);
        order.setAppartment(appartment);
        order.setStartDate(startDate);
        order.setEndDate(endDate);
        mySqlAppartmentOderDao.create(order);
        return order;
    }
}
