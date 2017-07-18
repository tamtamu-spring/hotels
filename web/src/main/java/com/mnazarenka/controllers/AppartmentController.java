package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.EconomAppartment;
import com.mnazarenka.dao.entity.Hotel;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;
import com.mnazarenka.dao.utils.AppartmentUtil;
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
public class AppartmentController {
    private AppartmentService appartmentService;
    private HotelService hotelService;
    private AppartmentUtil appartmentUtil;

    @Autowired
    public AppartmentController(AppartmentService appartmentService, HotelService hotelService, AppartmentUtil appartmentUtil) {
        this.appartmentService = appartmentService;
        this.hotelService = hotelService;
        this.appartmentUtil = appartmentUtil;
    }

    @ModelAttribute("hotels")
    public List<Hotel> hotels() {
        return hotelService.findAll();
    }

    @GetMapping("/admin/appartments/{type}")
    public String getEconomAppartment(Model model, @PathVariable String type) throws IllegalAccessException, InstantiationException {
        Class appartmentClass = appartmentUtil.getAppartmentClass(type);

        List<? extends Appartment> appartments = appartmentService.findAllAppartments(appartmentClass);
        model.addAttribute("appartments", appartments);
        model.addAttribute("appartment", appartmentClass.newInstance());

        return "/admin/appartments";
    }

    @GetMapping("/admin/appartments/delete/{id}/{type}")
    public String deleteStandartAppartment(@PathVariable("id") long id, @PathVariable String type) {
        appartmentService.deleteById(id);
        return "redirect:/admin/appartments/" + type;
    }

    @GetMapping("/admin/appartments/update/{id}/{type}")
    public String goToUpdatePage(@PathVariable("id") long id, @PathVariable String type, Model model) {
        Class appartmentClass = appartmentUtil.getAppartmentClass(type);

        model.addAttribute("appartment", appartmentService.findAppartment(id, appartmentClass));
        return "update/appartments/" + type;
    }

    @PostMapping("/admin/econom-appartments/update")
    public String updateEconomAppartment(EconomAppartment appartment, long hotelId) {
        appartmentService.updateAppartmentWithHotelId(appartment, hotelId);
        return "redirect:/admin/appartments/econom";
    }

    @PostMapping("/admin/standart-appartments/update")
    public String updateStandartAppartment(StandartAppartment appartment, long hotelId) {
        appartmentService.updateAppartmentWithHotelId(appartment, hotelId);
        return "redirect:/admin/appartments/standart";
    }

    @PostMapping("/admin/lux-appartments/update")
    public String updateLuxAppartment(LuxAppartment appartment, long hotelId) {
        appartmentService.updateAppartmentWithHotelId(appartment, hotelId);
        return "redirect:/admin/appartments/lux";
    }

    @PostMapping("/admin/appartments/econom")
    public String createEconomAppartment(EconomAppartment appartment, long hotelId) {
        appartmentService.createAppartmentWithHotelId(appartment, hotelId);
        return "redirect:/admin/appartments/econom";
    }

    @PostMapping("/admin/appartments/standart")
    public String createStandartppartment(StandartAppartment appartment, long hotelId) {
        appartmentService.createAppartmentWithHotelId(appartment, hotelId);
        return "redirect:/admin/appartments/standart";
    }

    @PostMapping("/admin/appartments/lux")
    public String createLuxAppartment(LuxAppartment appartment, long hotelId) {
        appartmentService.createAppartmentWithHotelId(appartment, hotelId);
        return "redirect:/admin/appartments/lux";
    }

    @GetMapping("/admin/appartments/create/{type}")
    public String goToCreatePage(@PathVariable String type, Model model) throws IllegalAccessException, InstantiationException {
        Class appartmentClass = appartmentUtil.getAppartmentClass(type);

        model.addAttribute("appartment", appartmentClass.newInstance());
        return "create/appartments/" + type;
    }
}
