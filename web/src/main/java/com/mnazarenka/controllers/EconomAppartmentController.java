package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.EconomAppartment;
import com.mnazarenka.dao.entity.Hotel;
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
public class EconomAppartmentController {
    private AppartmentService appartmentService;
    private HotelService hotelService;

    @Autowired
    public EconomAppartmentController(AppartmentService appartmentService, HotelService hotelService) {
        this.appartmentService = appartmentService;
        this.hotelService = hotelService;
    }

    @ModelAttribute("appartment")
    public EconomAppartment appartment(){
        return new EconomAppartment();
    }

    @ModelAttribute("appartments")
    public List<EconomAppartment> appartments(){
        return appartmentService.findAllEconomAppartments();
    }

    @ModelAttribute("hotels")
    public List<Hotel> hotels(){
        return hotelService.findAll();
    }

    @GetMapping("/admin/econom-appartments")
    public String getEconomAppartment(){
        return "admin/appartments/econom";
    }

    @PostMapping("/admin/econom-appartments")
    public String createEconomAppartment(EconomAppartment appartment, long hotelId){
        appartmentService.createAppartmentWithHotelId(appartment, hotelId);
        return "redirect:/admin/econom-appartments";
    }

    @GetMapping("/admin/econom-appartments/delete/{id}")
    public String deleteStandartAppartment(@PathVariable("id") long id){
        appartmentService.deleteById(id);
        return "redirect:/admin/econom-appartments";
    }

    @GetMapping("/admin/econom-appartments/update/{id}")
    public String goToUpdatePage(@PathVariable("id") long id, Model model){
        EconomAppartment appartment = appartmentService.findEconomAppartment(id);
        model.addAttribute("appartment", appartment);
        return "update/appartments/econom";
    }

    @PostMapping("/admin/econom-appartments/update")
    public String updateEconomAppartment(EconomAppartment appartment, long hotelId){
        appartmentService.updateAppartmentWithHotelId(appartment, hotelId);
        return "redirect:/admin/econom-appartments";
    }

}
