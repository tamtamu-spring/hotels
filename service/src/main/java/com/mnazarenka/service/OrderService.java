package com.mnazarenka.service;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.service.common.BaseService;

import java.util.List;

public interface OrderService extends BaseService<AppartmentOrder> {
    AppartmentOrder createOrder(Class<? extends Appartment> clazz, Long apartId, String userName, String startDate, String endDate);

    List<AppartmentOrder> findAllByUserId(Long userId);

    void updateOrder(long orderId, String startDate, String endDate);

    void updateOrder(long orderId ,long userId, long appartId, String startDate, String endDate);
}
