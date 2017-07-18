package com.mnazarenka.service;

import com.mnazarenka.dao.entity.Hotel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class HotelServiceTest extends BaseServiceTest<Hotel>{
    @Autowired
    @Getter
    private HotelService service;

    @Override
    public void testFindAll() {
        List<Hotel> hotels = service.findAll();
        assertThat(hotels, hasSize(3));
    }

}
