package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.service.AppartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class RentAppartmentController {
    private AppartmentService appartmentService;

    @Autowired
    public RentAppartmentController(AppartmentService appartmentService) {
        this.appartmentService = appartmentService;
    }

    @ModelAttribute("appartments")
    public List<Appartment> getAllAppartments(){
       return appartmentService.findAll();
    }

    @GetMapping("/user/rent-appartments")
    public String goToRentPage(){
        return "user/rent-appartments";
    }
}
