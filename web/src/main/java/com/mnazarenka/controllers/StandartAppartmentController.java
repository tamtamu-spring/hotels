package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;
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
public class StandartAppartmentController {
    private AppartmentService appartmentService;
    private HotelService hotelService;

    @Autowired
    public StandartAppartmentController(AppartmentService appartmentService, HotelService hotelService) {
        this.appartmentService = appartmentService;
        this.hotelService = hotelService;
    }

    @ModelAttribute("appartment")
    public StandartAppartment appartment(){
        return new StandartAppartment();
    }

    @ModelAttribute("appartments")
    public List<StandartAppartment> appartments(){
        return appartmentService.findAllStandartAppartments();
    }

    @ModelAttribute("hotels")
    public List<Hotel> hotels(){
        return hotelService.findAll();
    }

    @GetMapping("/admin/standart-appartments")
    public String getStandartAppartment(){
        return "admin/appartments/standart";
    }

    @PostMapping("/admin/standart-appartments")
    public String createStandartAppartment(StandartAppartment appartment, long hotelId){
        appartmentService.createStandartAppartmentWithHotelId(appartment, hotelId);
        return "redirect:/admin/standart-appartments";
    }

    @GetMapping("/admin/standart-appartments/delete/{id}")
    public String deleteStandartAppartment(@PathVariable("id") long id){
        appartmentService.deleteById(id);
        return "redirect:/admin/standart-appartments";
    }

    @GetMapping("/admin/standart-appartments/update/{id}")
    public String goToUpdatePage(@PathVariable("id") long id, Model model){
        LuxAppartment appartment = appartmentService.findLuxAppartment(id);
        model.addAttribute("appartment", appartment);
        return "update/appartments/standart";
    }

    @PostMapping("/admin/standart-appartments/update")
    public String updateStandartAppartment(StandartAppartment appartment, long hotelId){
        appartmentService.createStandartAppartmentWithHotelId(appartment, hotelId);
        return "redirect:/admin/standart-appartments";
    }
}
