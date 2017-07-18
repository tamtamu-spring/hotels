package com.mnazarenka.service.impl;

import com.mnazarenka.dao.AppartmentDao;
import com.mnazarenka.dao.AppartmentOrderDao;
import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.dao.enums.Status;
import com.mnazarenka.service.OrderService;
import com.mnazarenka.service.UserService;
import com.mnazarenka.service.common.BaseServiceImpl;
import lombok.Getter;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends BaseServiceImpl<AppartmentOrder> implements OrderService {
    @Getter
    private final AppartmentOrderDao dao;
    private UserService userService;
    private AppartmentDao appartmentDao;

    @Autowired
    public OrderServiceImpl(AppartmentOrderDao dao, UserService userService, AppartmentDao appartmentDao) {
        this.dao = dao;
        this.userService = userService;
        this.appartmentDao = appartmentDao;
    }

    @Override
    public void changeStatus(Status status, long appartId, long userId, AppartmentOrder order) {
        order.setStatus(status);

        updateOrder(appartId, userId, order);
    }

    @Override
    public void updateOrder(long appartId, long userId, AppartmentOrder order) {
        User user = userService.find(userId);
        Appartment appartment = appartmentDao.find(appartId);

        order.setUser(user);
        order.setAppartment(appartment);

        dao.update(order);
    }


    @Override
    public List<AppartmentOrder> findAllByUserId(Long userId) {
        User user = userService.find(userId);

        List<AppartmentOrder> appartments = user.getAppartments();
        Hibernate.initialize(appartments);
        return appartments;
    }

    @Override
    public AppartmentOrder createOrder(Class<? extends Appartment> clazz, Long apartId, String userName, AppartmentOrder order) {
        User user = userService.getUserByLogin(userName);

        order.setUser(user);
        order.setAppartment(appartmentDao.findAppartment(apartId, clazz));
        order.setStatus(Status.ADDED);

        return dao.create(order);
    }
}
