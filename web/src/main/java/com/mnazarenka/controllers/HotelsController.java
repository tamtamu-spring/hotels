package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HotelsController {
    private HotelService hotelService;

    @Autowired
    public HotelsController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @ModelAttribute("hotel")
    public Hotel hotel() {
        return new Hotel();
    }

    @ModelAttribute("hotels")
    public List<Hotel> hotels() {
        return hotelService.findAll();
    }

    @GetMapping("/admin/hotels")
    public String getHotels() {
        return "admin/hotels";
    }

    @PostMapping("/admin/hotels")
    public String createHotel(Hotel hotel) {
        hotelService.create(hotel);
        return "redirect:/admin/hotels";
    }

    @GetMapping("/admin/hotels/delete/{id}")
    public String deleteHotel(@PathVariable("id") long id) {
        hotelService.deleteById(id);
        return "redirect:/admin/hotels";
    }

    @GetMapping("/admin/hotels/update/{id}")
    public String goToUpdatePage(@PathVariable("id") long id, Model model) {
        Hotel hotel = hotelService.find(id);
        model.addAttribute("hotel", hotel);
        return "update/hotel";
    }

    @PostMapping("/admin/hotels/update")
    public String updateHotel(Hotel hotel) {
        hotelService.update(hotel);
        return "redirect:/admin/hotels";
    }
}
