package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.Dish;
import com.mnazarenka.dao.entity.DishOrder;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.service.DishOrderService;
import com.mnazarenka.service.DishServise;
import com.mnazarenka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DishOrdersController {

    private UserService userService;
    private DishServise dishServise;
    private DishOrderService dishOrderService;

    @Autowired
    public DishOrdersController(DishOrderService dishOrderService, DishServise dishServise, UserService userService) {
        this.dishOrderService = dishOrderService;
        this.dishServise = dishServise;
        this.userService = userService;
    }

    @ModelAttribute("dishes")
    public List<Dish> dishes() {
        return dishServise.findAll();
    }

    @ModelAttribute("dishOrders")
    public List<DishOrder> dishOrders() {
        return dishOrderService.findAll();
    }

    @PostMapping("/user/dishes")
    public String saveDishOrder(Authentication auth, long dishId, int dishCount) {
        User user = userService.getUserByLogin(auth.getName());
        dishOrderService.create(user.getId(), dishId, dishCount);
        return "redirect:/user/dishes";
    }

    @GetMapping("/user/dishes")
    public String getDishesPage() {
        return "user/dishes";
    }

    @GetMapping("/admin/dish-orders")
    public String getAdminDishOrdersPage() {
        return "admin/dish-orders";
    }

    @GetMapping("/admin/dish-order/delete/{id}")
    public String deleteDishOrder(@PathVariable long id) {
        dishOrderService.deleteById(id);
        return "redirect:/admin/dish-orders";
    }
}
