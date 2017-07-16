package com.mnazarenka.service;

import com.mnazarenka.dao.entity.DishOrderDetails;
import com.mnazarenka.service.common.BaseService;

import java.util.List;

public interface DishOrderDetailsService extends BaseService<DishOrderDetails> {
    List<DishOrderDetails> findByOrderId(long id);
}
