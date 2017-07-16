package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.Dish;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.service.DishOrderService;
import com.mnazarenka.service.DishServise;
import com.mnazarenka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public List<Dish> dishes(){
        return dishServise.findAll();
    }

    @PostMapping("/user/dishes")
    public String saveDishOrder(Authentication auth, long dishId, int dishCount){
        User user = userService.getUserByLogin(auth.getName());
        dishOrderService.create(user.getId(), dishId, dishCount);
        return "redirect:/user/dishes";
    }

    @GetMapping("/user/dishes")
    public String getDishesPage(){
        return "user/dishes";
    }
}
