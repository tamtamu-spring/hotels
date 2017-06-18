package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.DishOrder;
import com.mnazarenka.dao.entity.Restaurant;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.dao.mysql.BaseDao;
import com.mnazarenka.dao.mysql.MySqlAppartmentsDao;
import com.mnazarenka.dao.mysql.MySqlDishOrderDao;
import com.mnazarenka.dao.mysql.MySqlRestaurantDao;
import com.mnazarenka.dao.mysql.MySqlUserDao;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class DishOrderDaoTest extends BaseDaoTest<DishOrder> {

    @Before
    public void initDb() {
        //sessionFactory = new Configuration().configure().buildSessionFactory();
        //TestDataImporter.getInstance().importTestData(sessionFactory);
    }

    @Test
    public void findAllDishesTest() {
        MySqlDishOrderDao mySqlDishOrderDao = new MySqlDishOrderDao();
        MySqlUserDao mySqlUserDao = new MySqlUserDao();
        MySqlAppartmentsDao mySqlAppartmentsDao = new MySqlAppartmentsDao();

        User user = new User();
        Appartment appartment = new Appartment();
        user = mySqlUserDao.create(user);
        appartment = mySqlAppartmentsDao.create(appartment);

        DishOrder firstDishOrder = saveDishOrder(user, appartment, LocalDateTime.of(LocalDate.now(), LocalTime.MAX), mySqlDishOrderDao);
        DishOrder secondDishOrder = saveDishOrder(user, appartment, LocalDateTime.of(LocalDate.now(), LocalTime.MIN), mySqlDishOrderDao);


        List<DishOrder> orders = mySqlDishOrderDao.findAll();

        List<LocalDateTime> dishOrderTimes = orders.stream().map(DishOrder::getOrderTime)
                .collect(toList());

        List<User> users = orders.stream().map(DishOrder::getUser).collect(toList());
        List<Appartment> appartments = orders.stream().map(DishOrder::getAppartment).collect(toList());

        assertThat(orders, hasSize(2));
        assertThat(dishOrderTimes, containsInAnyOrder(LocalDateTime.of(LocalDate.now(), LocalTime.MAX),
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN)));
        users.forEach(u -> assertNotNull(u));
        appartments.forEach(a -> assertNotNull(a));

        mySqlDishOrderDao.delete(firstDishOrder);
        mySqlDishOrderDao.delete(secondDishOrder);
        mySqlAppartmentsDao.delete(appartment);
        mySqlUserDao.delete(user);
    }

    @After
    public void destroy() {
        //sessionFactory.close();
    }

    @Override
    public DishOrder getEntity() {
        return new DishOrder();
    }

    @Override
    public BaseDao<DishOrder> getCurrentDao() {
        return new MySqlDishOrderDao();
    }

    @Override
    public void testUpdate() {
        MySqlDishOrderDao mySqlDishOrderDao = new MySqlDishOrderDao();
        DishOrder dishOrder = saveDishOrder(null, null, LocalDateTime.of(2017, 10,10,10,10), mySqlDishOrderDao);

        Long id = dishOrder.getId();
        dishOrder.setOrderTime(LocalDateTime.of(2017, 10,10,10,15));
        mySqlDishOrderDao.update(dishOrder);

        dishOrder = mySqlDishOrderDao.find(id);

        assertEquals(LocalDateTime.of(2017, 10,10,10,15), dishOrder.getOrderTime());

        mySqlDishOrderDao.delete(dishOrder);

    }

    private DishOrder saveDishOrder(User user, Appartment apartment, LocalDateTime time, MySqlDishOrderDao mySqlDishOrderDao) {
        DishOrder dishOrder = new DishOrder();
        dishOrder.setUser(user);
        dishOrder.setAppartment(apartment);
        dishOrder.setOrderTime(time);
        mySqlDishOrderDao.create(dishOrder);
        return dishOrder;
    }
}
