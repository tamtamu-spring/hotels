package com.mnazarenka.service.impl;

import com.mnazarenka.dao.DishDao;
import com.mnazarenka.dao.DishOrderDao;
import com.mnazarenka.dao.DishOrderDetailsDao;
import com.mnazarenka.dao.UserDao;
import com.mnazarenka.dao.entity.Dish;
import com.mnazarenka.dao.entity.DishOrder;
import com.mnazarenka.dao.entity.DishOrderDetails;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.service.DishOrderService;
import com.mnazarenka.service.common.BaseServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DishOrderServiceImpl extends BaseServiceImpl<DishOrder> implements DishOrderService {
    @Getter
    private DishOrderDao dao;
    private DishOrderDetailsDao dishOrderDetailsDao;
    private UserDao userDao;
    private DishDao dishDao;

    @Autowired
    public DishOrderServiceImpl(DishOrderDao dao, UserDao userDao, DishDao dishDao, DishOrderDetailsDao dishOrderDetailsDao) {
        this.dao = dao;
        this.userDao = userDao;
        this.dishDao = dishDao;
        this.dishOrderDetailsDao = dishOrderDetailsDao;
    }

    @Override
    public void create(long userId, long dishId, int dishCount) {
        User user = userDao.find(userId);
        Dish dish = dishDao.find(dishId);

        DishOrder dishOrder = new DishOrder();
        dishOrder.setUser(user);
        dishOrder.setOrderTime(LocalDateTime.now());

        dao.create(dishOrder);

        DishOrderDetails dishOrderDetails = new DishOrderDetails();
        dishOrderDetails.setCount(dishCount);
        dishOrderDetails.setDish(dish);
        dishOrderDetails.setOrder(dishOrder);

        dishOrderDetailsDao.create(dishOrderDetails);
    }
}
