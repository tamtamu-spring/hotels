package com.mnazarenka.dao;

import com.mnazarenka.dao.entity.Dish;
import com.mnazarenka.dao.entity.DishOrder;
import com.mnazarenka.dao.entity.DishOrderDetails;
import lombok.Getter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class DishOrderDetailsDaoTest extends BaseDaoTest<DishOrderDetails> {
    @Autowired
    @Getter
    private DishOrderDetailsDao dao;

    @Test
    public void testFindAll() {
        List<DishOrderDetails> details = dao.findAll();
        List<DishOrder> dishOrders = details.stream().map(DishOrderDetails::getOrder)
                .collect(toList());
        List<Dish> dishes = details.stream().map(DishOrderDetails::getDish).collect(toList());
        List<Integer> counts = details.stream().map(DishOrderDetails::getCount).collect(toList());

        assertThat(details, hasSize(2));
        dishes.forEach(d -> assertNotNull(d));
        dishOrders.forEach(o -> assertNotNull(o));
        assertThat(counts, containsInAnyOrder(1, 2));
    }

    @Override
    public void testUpdate() {
        DishOrderDetails dishOrderDetails = getTestDataImporter().saveDishOrderDetail(null, null, 3, dao);

        Long id = dishOrderDetails.getId();
        dishOrderDetails.setCount(5);
        dao.update(dishOrderDetails);

        dishOrderDetails = dao.find(id);

        assertEquals(5, (long) dishOrderDetails.getCount());
    }
}

