package com.mnazarenka.service;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.service.common.BaseService;

import java.util.List;

public interface OrderService extends BaseService<AppartmentOrder> {
    AppartmentOrder createOrder(Class<? extends Appartment> clazz, Long apartId, String userName, String startDate, String endDate);

    List<AppartmentOrder> findAllByUserId(Long userId);

}
