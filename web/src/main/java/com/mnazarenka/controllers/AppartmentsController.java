package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.service.AppartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AppartmentsController {
    @Autowired
    private AppartmentService appartmentService;

    @GetMapping(path = "/appartments")
    public String showAllAppartments(Model model){
        List<Appartment> appartments = appartmentService.findAll();
        model.addAttribute("appartments", appartments);
        return "appartments";
    }
}
