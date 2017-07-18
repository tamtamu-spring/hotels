package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.DishOrder;
import com.mnazarenka.dao.entity.User;
import lombok.Getter;
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

public class DishOrderDaoTest extends BaseDaoTest<DishOrder> {
    @Autowired
    @Getter
    private DishOrderDao dao;

    @Test
    public void testFindAll() {
        List<DishOrder> orders = dao.findAll();

        List<LocalDateTime> dishOrderTimes = orders.stream().map(DishOrder::getOrderTime)
                .collect(toList());

        List<User> users = orders.stream().map(DishOrder::getUser).collect(toList());

        assertThat(orders, hasSize(2));
        assertThat(dishOrderTimes, containsInAnyOrder(LocalDateTime.of(LocalDate.now(), LocalTime.MAX),
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN)));
        users.forEach(u -> assertNotNull(u));
    }

    @Override
    public void testUpdate() {
        DishOrder dishOrder = getTestDataImporter().saveDishOrder(null, null, LocalDateTime.of(2017, 10, 10, 10, 10), dao);

        Long id = dishOrder.getId();
        dishOrder.setOrderTime(LocalDateTime.of(2017, 10, 10, 10, 15));
        dao.update(dishOrder);

        dishOrder = dao.find(id);

        assertEquals(LocalDateTime.of(2017, 10, 10, 10, 15), dishOrder.getOrderTime());
    }
}
