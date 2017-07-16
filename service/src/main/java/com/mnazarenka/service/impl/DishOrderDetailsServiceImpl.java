package com.mnazarenka.service.impl;

import com.mnazarenka.dao.DishOrderDao;
import com.mnazarenka.dao.DishOrderDetailsDao;
import com.mnazarenka.dao.entity.DishOrder;
import com.mnazarenka.dao.entity.DishOrderDetails;
import com.mnazarenka.service.DishOrderDetailsService;
import com.mnazarenka.service.common.BaseServiceImpl;
import lombok.Getter;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishOrderDetailsServiceImpl extends BaseServiceImpl<DishOrderDetails> implements DishOrderDetailsService {
    @Getter
    private DishOrderDetailsDao dao;
    private DishOrderDao dishOrderDao;

    @Autowired
    public DishOrderDetailsServiceImpl(DishOrderDetailsDao dao, DishOrderDao dishOrderDao) {
        this.dao = dao;
        this.dishOrderDao = dishOrderDao;
    }

    @Override
    public List<DishOrderDetails> findByOrderId(long id) {
        DishOrder dishOrder = dishOrderDao.find(id);

        List<DishOrderDetails> details = dishOrder.getDetails();
        Hibernate.initialize(details);

        return details;
    }
}
