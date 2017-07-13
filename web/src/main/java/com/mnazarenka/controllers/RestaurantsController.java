package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.Restaurant;
import com.mnazarenka.service.HotelService;
import com.mnazarenka.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RestaurantsController {
    private RestaurantService restaurantService;
    private HotelService hotelService;

    @Autowired
    public RestaurantsController(RestaurantService restaurantService, HotelService hotelService) {
        this.restaurantService = restaurantService;
        this.hotelService = hotelService;
    }

    @ModelAttribute("restaurant")
    public Restaurant restaurant(){
        return new Restaurant();
    }

    @ModelAttribute("hotels")
    public List<Hotel> hotels(){
        return hotelService.findAll();
    }

    @ModelAttribute("restaurants")
    public List<Restaurant> restaurants(){
        List<Restaurant> all = restaurantService.findAll();
        return all;
    }

    @GetMapping("/admin/restaurants")
    public String getRestaurants(){
        return "admin/restaurants";
    }

    @PostMapping("/admin/restaurants")
    public String createRestaurant(Restaurant restaurant, long hotelId){
        restaurantService.createRestaurantWithHotelId(restaurant, hotelId);
        return "redirect:/admin/restaurants";
    }

    @GetMapping("/admin/restaurants/delete/{id}")
    public String deleteRestaurant(@PathVariable("id") long id){
        restaurantService.deleteById(id);
        return "redirect:/admin/restaurants";
    }

    @GetMapping("/admin/restaurants/update/{id}")
    public String goToUpdatePage(@PathVariable("id") long id, Model model){
        Restaurant restaurant = restaurantService.find(id);
        model.addAttribute("restaurant", restaurant);
        return "update/update-restaurant";
    }

    @PostMapping("/admin/restaurants/update")
    public String updateRestaurant(Restaurant restaurant){
        restaurantService.update(restaurant);
        return "redirect:/admin/restaurants";
    }
}
