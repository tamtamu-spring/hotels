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

import java.time.LocalDate;
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
    public void updateOrder(long orderId, String startDate, String endDate) {
        AppartmentOrder appartmentOrder = dao.find(orderId);

        appartmentOrder.setStartDate(LocalDate.parse(startDate));
        appartmentOrder.setEndDate(LocalDate.parse(endDate));

        dao.update(appartmentOrder);
    }

    @Override
    public List<AppartmentOrder> findAllByUserId(Long userId) {
        User user = userService.find(userId);

        List<AppartmentOrder> appartments = user.getAppartments();
        Hibernate.initialize(appartments);
        return appartments;
    }

    @Override
    public AppartmentOrder createOrder(Class<? extends Appartment> clazz, Long apartId, String userName, String startDate, String endDate) {
        User user = userService.getUserByLogin(userName);

        AppartmentOrder order = new AppartmentOrder();
        order.setUser(user);
        order.setAppartment(appartmentDao.findAppartment(apartId, clazz));
        order.setStartDate(LocalDate.parse(startDate));
        order.setEndDate(LocalDate.parse(endDate));
        order.setStatus(Status.ADDED);

        return dao.create(order);
    }
}
