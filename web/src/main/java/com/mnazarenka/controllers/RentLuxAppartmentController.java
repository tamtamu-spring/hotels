package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.EconomAppartment;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;
import com.mnazarenka.service.AppartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RentLuxAppartmentController {
    private AppartmentService appartmentService;

    //@Value("#{new Integer.parseInt('${items.counts}')}")
    private Integer pageCounts = 3;

    @Autowired
    public RentLuxAppartmentController(AppartmentService appartmentService) {
        this.appartmentService = appartmentService;
    }


    @GetMapping("/user/rent-lux-appartments")
    public String goToRentLuxPage(Model model) {

        List<LuxAppartment> appartments = appartmentService.getAppartmentsByRange(0, pageCounts, LuxAppartment.class);

        model.addAttribute("appartments", appartments);

        return "user/appartments/rent-lux";
    }

    @GetMapping("/user/rent-lux-appartments/{pageNumber}")
    public String getLuxAppartmentsWithPaging(@PathVariable int pageNumber, Model model) {

        pageNumber = getCurrentPageNumber(pageNumber);

        List<LuxAppartment> appartments = appartmentService.getAppartmentsByRange(pageNumber - 1, pageCounts, LuxAppartment.class);

        model.addAttribute("appartments", appartments);

        return "user/appartments/rent-lux";
    }

    @GetMapping("/user/rent-standart-appartments")
    public String goToStandartRentPage(Model model) {

        List<StandartAppartment> appartments = appartmentService.getAppartmentsByRange(0, pageCounts, StandartAppartment.class);

        model.addAttribute("appartments", appartments);

        return "user/appartments/rent-standart";
    }

    @GetMapping("/user/rent-standart-appartments/{pageNumber}")
    public String getStandartAppartmentsWithPaging(@PathVariable int pageNumber, Model model) {
        pageNumber = getCurrentPageNumber(pageNumber);

        List<StandartAppartment> appartments = appartmentService.getAppartmentsByRange(pageNumber - 1, pageCounts, StandartAppartment.class);

        model.addAttribute("appartments", appartments);

        return "user/appartments/rent-standart";
    }

    @GetMapping("/user/rent-econom-appartments")
    public String goToEconomRentPage(Model model) {

        List<EconomAppartment> appartments = appartmentService.getAppartmentsByRange(0, pageCounts, EconomAppartment.class);

        model.addAttribute("appartments", appartments);

        return "user/appartments/rent-econom";
    }


    @GetMapping("/user/rent-econom-appartments/{pageNumber}")
    public String getEconomAppartmentsWithPaging(@PathVariable int pageNumber, Model model) {
        pageNumber = getCurrentPageNumber(pageNumber);

        List<EconomAppartment> appartments = appartmentService.getAppartmentsByRange(pageNumber - 1, pageCounts, EconomAppartment.class);

        model.addAttribute("appartments", appartments);

        return "user/appartments/rent-econom";
    }

    private int getCurrentPageNumber(int pageNumber) {
        if (pageNumber != 1) {
            pageNumber = (pageNumber - 1) * pageCounts + 1;
        }
        return pageNumber;
    }
}
