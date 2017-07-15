package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.service.AppartmentService;
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
public class LuxAppartmentsController {
    private AppartmentService appartmentService;
    private HotelService hotelService;

    @Autowired
    public LuxAppartmentsController(AppartmentService appartmentService, HotelService hotelService) {
        this.appartmentService = appartmentService;
        this.hotelService = hotelService;
    }

    @ModelAttribute("appartment")
    public LuxAppartment appartment(){
        return new LuxAppartment();
    }

    @ModelAttribute("appartments")
    public List<LuxAppartment> appartments(){
        return appartmentService.findAllLuxAppartments();
    }

    @ModelAttribute("hotels")
    public List<Hotel> hotels(){
        return hotelService.findAll();
    }

    @GetMapping("/admin/lux-appartments")
    public String getLuxAppartment(){
        return "admin/appartments/lux";
    }

    @PostMapping("/admin/lux-appartments")
    public String createLuxAppartment(LuxAppartment appartment, long hotelId){
        appartmentService.createAppartmentWithHotelId(appartment, hotelId);
        return "redirect:/admin/lux-appartments";
    }

    @GetMapping("/admin/lux-appartments/delete/{id}")
    public String deleteLuxAppartment(@PathVariable("id") long id){
        appartmentService.deleteById(id);
        return "redirect:/admin/lux-appartments";
    }

    @GetMapping("/admin/lux-appartments/update/{id}")
    public String goToUpdatePage(@PathVariable("id") long id, Model model){
        LuxAppartment appartment = appartmentService.findLuxAppartment(id);
        model.addAttribute("appartment", appartment);
        return "update/appartments/lux";
    }

    @PostMapping("/admin/lux-appartments/update")
    public String updateLuxAppartment(LuxAppartment appartment, long hotelId){
        appartmentService.updateAppartmentWithHotelId(appartment, hotelId);
        return "redirect:/admin/lux-appartments";
    }
}
