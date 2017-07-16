package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.DishOrderDetails;
import com.mnazarenka.service.DishOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DishOrderDetailsControlles {

    private final DishOrderDetailsService dishOrderDetailsService;


    @Autowired
    public DishOrderDetailsControlles(DishOrderDetailsService dishOrderDetailsService) {
        this.dishOrderDetailsService = dishOrderDetailsService;
    }

    @GetMapping("/admin/dish-order-details/{id}")
    public String getDetails(@PathVariable long id, Model model) {
        List<DishOrderDetails> details = dishOrderDetailsService.findByOrderId(id);
        model.addAttribute("details", details);
        return "admin/details";
    }
}
