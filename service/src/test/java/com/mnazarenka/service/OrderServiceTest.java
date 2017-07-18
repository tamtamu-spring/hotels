package com.mnazarenka.service;

import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.enums.Status;
import lombok.Getter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class OrderServiceTest extends BaseServiceTest<AppartmentOrder>{
    @Autowired
    @Getter
    private OrderService service;

    @Override
    public void testFindAll() {
        List<AppartmentOrder> orders = service.findAll();
        assertThat(orders, hasSize(2));
    }

}
