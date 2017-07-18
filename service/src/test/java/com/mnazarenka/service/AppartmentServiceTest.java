package com.mnazarenka.service;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.LuxAppartment;
import lombok.Getter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class AppartmentServiceTest extends BaseServiceTest<Appartment> {
    @Autowired
    @Getter
    private AppartmentService service;

    @Override
    public void testFindAll() {
        List<Appartment> appartments = service.findAll();
        assertThat(appartments, hasSize(3));
    }

    @Test
    public void testGetCount() {
        Long appartmentsCount = service.getAppartmentsCount(LuxAppartment.class);

        assertThat(appartmentsCount, equalTo(1L));
    }

  /*  @Test
    public void testFindAppartment(){
        Appartment appartment = service.findAppartment(1L, EconomAppartment.class);

        assertNotNull(appartment);
    }

    @Test
    public void testCreateAppartmentWithHotelId(){
        Appartment newAppartment = new Appartment();
        service.createAppartmentWithHotelId(newAppartment, 1L);

        Appartment appartment = service.find(4L);

        assertNotNull(appartment);
    }

    @Test
    public void testUpdateAppartmentWithHotelId(){
        Appartment newAppartment = new Appartment();
        service.createAppartmentWithHotelId(newAppartment, 1L);

        Appartment appartment = service.find(4L);
        appartment.setName("New name");

        service.updateAppartmentWithHotelId(appartment, 1L);

        Appartment updatedAppartment = service.find(4L);

        assertEquals(updatedAppartment.getName(), "New name");
    }*/
}
