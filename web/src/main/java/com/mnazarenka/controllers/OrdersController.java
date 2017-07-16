package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.service.OrderService;
import com.mnazarenka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class OrdersController {
    private final OrderService orderService;
    private UserService userService;

    @Autowired
    public OrdersController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @ModelAttribute("orders")
    public List<AppartmentOrder> orders(Authentication auth){
        User user = userService.getUserByLogin(auth.getName());
        List<AppartmentOrder> orders = orderService.findAllByUserId(user.getId());
        return orders;
    }

    @GetMapping("/user/account/orders")
    private String getOrdersPage(){
        return "user/orders";
    }

    @GetMapping("/user/orders/delete/{id}")
    public String deleteOrder(@PathVariable long id){
        orderService.deleteById(id);
        return "redirect:/user/account/orders";
    }
}
