package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.entity.User;
import lombok.Getter;
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
    @Getter
    private AppartmentOrderDao dao;

    @Test
    public void testFindAll() {
        List<AppartmentOrder> orders = dao.findAll();

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
    }

    @Override
    public void testUpdate() {
        AppartmentOrder order = getTestDataImporter().saveOrder(null, null,
                LocalDate.of(2017, 10, 10), LocalDate.of(2017, 10, 15), dao);

        Long id = order.getId();
        order.setStartDate(LocalDate.of(2017, 10, 1));
        dao.update(order);

        order = dao.find(id);

        assertEquals(LocalDate.of(2017, 10, 1), order.getStartDate());
    }
}
