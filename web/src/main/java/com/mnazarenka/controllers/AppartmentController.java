package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.EconomAppartment;
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

/**
 * Created by Николай on 16.07.2017.
 */
@Controller
public class AppartmentController {
    private AppartmentService appartmentService;
    private HotelService hotelService;

    @Autowired
    public AppartmentController(AppartmentService appartmentService, HotelService hotelService) {
        this.appartmentService = appartmentService;
        this.hotelService = hotelService;
    }
/*
    @ModelAttribute("appartment")
    public EconomAppartment appartment(){
        return new EconomAppartment();
    }*/
/*
    @ModelAttribute("appartments")
    public List<EconomAppartment> appartments(){
        return appartmentService.findAllAppartments(EconomAppartment.class);
    }*/

    @ModelAttribute("hotels")
    public List<Hotel> hotels(){
        return hotelService.findAll();
    }

    @GetMapping("/admin/appartments/{type}")
    public String getEconomAppartment(Model model, @PathVariable String type){

        switch (type) {
            case "lux": {
                List<LuxAppartment> appartments = appartmentService.findAllAppartments(LuxAppartment.class);
                model.addAttribute("appartments", appartments);
                model.addAttribute("appartment", new LuxAppartment());
                break;
            }
            case "standart": {
                List<StandartAppartment> appartments = appartmentService.findAllAppartments(StandartAppartment.class);
                model.addAttribute("appartments", appartments);
                model.addAttribute("appartment", new StandartAppartment());
                break;
            }
            case "econom": {
                List<EconomAppartment> appartments = appartmentService.findAllAppartments(EconomAppartment.class);
                model.addAttribute("appartments", appartments);
                model.addAttribute("appartment", new EconomAppartment());
                break;
            }
        }
        return "/admin/appartments";
    }

    @PostMapping("/admin/appartments/{type}")
    public String createEconomAppartment(Appartment appartment, @PathVariable String type, long hotelId){

        switch (type) {
            case "lux": {
                appartmentService.createAppartmentWithHotelId((LuxAppartment)appartment, hotelId);
                break;
            }
            case "standart": {
                appartmentService.createAppartmentWithHotelId((StandartAppartment)appartment, hotelId);
                break;
            }
            case "econom": {
                appartmentService.createAppartmentWithHotelId((EconomAppartment)appartment, hotelId);
                break;
            }
        }

        return "redirect:/admin/appartments/" + type;
    }

    @GetMapping("/admin/appartments/delete/{id}/{type}")
    public String deleteStandartAppartment(@PathVariable("id") long id, @PathVariable String type){
        appartmentService.deleteById(id);
        return "redirect:/admin/appartments/" + type;
    }

    @GetMapping("/admin/appartments/update/{id}/{type}")
    public String goToUpdatePage(@PathVariable("id") long id, @PathVariable String type, Model model){

        switch (type) {
            case "lux": {
                LuxAppartment appartment = appartmentService.findAppartment(id, LuxAppartment.class);
                model.addAttribute("appartment", appartment);
                break;
            }
            case "standart": {
                StandartAppartment appartment = appartmentService.findAppartment(id, StandartAppartment.class);
                model.addAttribute("appartment", appartment);
                break;
            }
            case "econom": {
                EconomAppartment appartment = appartmentService.findAppartment(id, EconomAppartment.class);
                model.addAttribute("appartment", appartment);
                break;
            }
        }

        return "update/appartments/" + type;
    }

    @PostMapping("/admin/appartments/update")
    public String updateEconomAppartment(Appartment appartment, long hotelId){
        appartmentService.updateAppartmentWithHotelId(appartment, hotelId);
        return "redirect:/admin/update";
    }

}
