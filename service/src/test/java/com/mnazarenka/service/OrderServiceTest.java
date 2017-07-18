package com.mnazarenka.service;

import com.mnazarenka.dao.entity.AppartmentOrder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
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

   /* @Test
    public void testChangeStatus(){
        AppartmentOrder appartmentOrder = service.create(new AppartmentOrder());
        Long id = appartmentOrder.getId();

        service.changeStatus(Status.CONFIRMED, 1L, 1L, appartmentOrder);

        AppartmentOrder updatedOrder = service.find(id);

        assertEquals(updatedOrder.getStatus(), Status.CONFIRMED);
    }

    @Test
    public void testUpdateOrder(){
        AppartmentOrder appartmentOrder = service.find(1L);
        appartmentOrder.setStartDate(LocalDate.MAX);
        service.updateOrder(1L, 1L, appartmentOrder);

        AppartmentOrder updatedOrder = service.find(1L);

        assertEquals(updatedOrder.getStartDate(), LocalDate.MAX);
    }

    @Test
    public void testFindAllByUserId(){
        List<AppartmentOrder> orders = service.findAllByUserId(1L);

        assertThat(orders, hasSize(1));
    }

    @Test
    public void testCreateOrder(){
        AppartmentOrder order = service.createOrder(LuxAppartment.class, 1L, "UserLogin", new AppartmentOrder());

        AppartmentOrder createdOrder = service.find(order.getId());

        assertNotNull(createdOrder);
    }
*/
}
