package com.mnazarenka.controllers;

import com.mnazarenka.dao.DishDao;
import com.mnazarenka.dao.entity.Dish;
import com.mnazarenka.service.DishServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DishesController {
    private DishServise dishService;

    @Autowired
    public DishesController(DishServise dishService) {
        this.dishService = dishService;
    }

    @ModelAttribute("dish")
    public Dish hotel(){
        return new Dish();
    }

    @ModelAttribute("dishes")
    public List<Dish> dishes(){
        return dishService.findAll();
    }

    @GetMapping("/admin/dishes")
    public String getDishes(){
        return "admin/dishes";
    }

    @PostMapping("/admin/dishes")
    public String createDish(Dish dish){
        dishService.create(dish);
        return "redirect:/admin/dishes";
    }

    @GetMapping("/admin/dishes/delete/{id}")
    public String deleteDish(@PathVariable("id") long id){
        dishService.deleteById(id);
        return "redirect:/admin/dishes";
    }

    @GetMapping("/admin/dishes/update/{id}")
    public String goToUpdatePage(@PathVariable("id") long id, Model model){
        Dish dish = dishService.find(id);
        model.addAttribute("dish", dish);
        return "update/update-dish";
    }

    @PostMapping("/admin/dishes/update")
    public String updateDish(Dish dish){
        dishService.update(dish);
        return "redirect:/admin/dishes";
    }
}
