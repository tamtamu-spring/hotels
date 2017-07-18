package com.mnazarenka.service;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.enums.Status;
import com.mnazarenka.service.common.BaseService;

import java.util.List;

public interface OrderService extends BaseService<AppartmentOrder> {
    AppartmentOrder createOrder(Class<? extends Appartment> clazz, Long apartId, String userName, AppartmentOrder order);

    List<AppartmentOrder> findAllByUserId(Long userId);

    void updateOrder(long appartId ,long userId, AppartmentOrder order);

    void changeStatus(Status status, long appartId, long userId, AppartmentOrder order);
}
