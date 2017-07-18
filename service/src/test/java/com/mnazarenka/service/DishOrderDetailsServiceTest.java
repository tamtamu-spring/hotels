package com.mnazarenka.service;

import com.mnazarenka.dao.entity.DishOrderDetails;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class DishOrderDetailsServiceTest extends BaseServiceTest<DishOrderDetails> {
    @Getter
    @Autowired
    private DishOrderDetailsService service;

    @Override
    public void testFindAll() {
        List<DishOrderDetails> details = service.findAll();
        assertThat(details, hasSize(2));
    }

}
