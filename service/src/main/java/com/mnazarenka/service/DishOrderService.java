package com.mnazarenka.service;

import com.mnazarenka.dao.entity.DishOrder;
import com.mnazarenka.service.common.BaseService;

public interface DishOrderService extends BaseService<DishOrder> {
    void create(long userId, long dishId, int dishCount);
}
