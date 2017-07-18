package com.mnazarenka.service;

import com.mnazarenka.dao.entity.DishOrder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Created by Николай on 18.07.2017.
 */
public class DishOrdersServiceTest extends BaseServiceTest<DishOrder> {
    @Getter
    @Autowired
    private DishOrderService service;

    @Override
    public void testFindAll() {
        List<DishOrder> orders = service.findAll();
        assertThat(orders, hasSize(2));
    }

}
