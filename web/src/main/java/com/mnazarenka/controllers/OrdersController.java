package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.service.OrderService;
import com.mnazarenka.service.UserService;
import com.mnazarenka.service.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @ModelAttribute("allOrders")
    public List<AppartmentOrder> allOrders(){
        return orderService.findAll();
    }


    @GetMapping("/admin/orders")
    public String getAdminOrders(){
        return "admin/orders";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getAdminOrderUpdatePage(@PathVariable long id, Model model){
        AppartmentOrder appartmentOrder = orderService.find(id);
        model.addAttribute("order", appartmentOrder);
        return "update/admin-order";
    }

    @GetMapping("/user/account/orders")
    private String getOrdersPage(){
        return "user/orders";
    }

    @GetMapping("/user/orders/update/{id}")
    public String getUpdatePage(@PathVariable long id, Model model){
        AppartmentOrder appartmentOrder = orderService.find(id);
        model.addAttribute("order", appartmentOrder);
        return "update/user-order";
    }

    @PostMapping("/user/orders/update")
    public String updateOrder(long orderId, String startDate, String endDate){
        orderService.updateOrder(orderId, startDate, endDate);

        return "redirect:/user/account/orders";
    }

    @GetMapping("/{user}/orders/delete/{id}")
    public String deleteOrder(@PathVariable long id, @PathVariable String user){
        orderService.deleteById(id);
        if(user.equalsIgnoreCase(RoleEnum.USER.toString())){
            return "redirect:/user/account/orders";
        }
        return "redirect:/admin/orders";
    }
}
