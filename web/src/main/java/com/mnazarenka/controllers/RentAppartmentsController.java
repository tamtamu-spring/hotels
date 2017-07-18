package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.entity.EconomAppartment;
import com.mnazarenka.dao.entity.LuxAppartment;
import com.mnazarenka.dao.entity.StandartAppartment;
import com.mnazarenka.service.AppartmentService;
import com.mnazarenka.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RentAppartmentsController {
    private AppartmentService appartmentService;
    private OrderService orderService;

    //@Value("#{new Integer.parseInt('${items.counts}')}")
    private Integer itemCountsOnPage = 3;

    @Autowired
    public RentAppartmentsController(AppartmentService appartmentService, OrderService orderService) {
        this.appartmentService = appartmentService;
        this.orderService = orderService;
    }

    @ModelAttribute("order")
    public AppartmentOrder order(){
        return new AppartmentOrder();
    }

    @PostMapping("/user/order")
    public String saveOrder(long apartId, AppartmentOrder order, Authentication auth, String appartType){
        String userName = auth.getName();
        switch (appartType) {
            case "lux": {
                orderService.createOrder(LuxAppartment.class, apartId, userName, order);
                break;
            }
            case "standart": {
                orderService.createOrder(StandartAppartment.class, apartId, userName, order);
                break;
            }
            case "econom": {
                orderService.createOrder(EconomAppartment.class, apartId, userName, order);
                break;
            }
        }

        return "redirect:/user/account/orders";
    }

    @GetMapping("/user/rent/{type}")
    public String goToRentPage(@PathVariable String type, Model model) {

        switch (type) {
            case "lux": {
                long appCounts = appartmentService.getAppartmentsCount(LuxAppartment.class);
                List<Integer> pages = getPages(appCounts);
                List<LuxAppartment> appartments = appartmentService.getAppartmentsByRange(0, itemCountsOnPage, LuxAppartment.class);
                model.addAttribute("appartments", appartments);
                model.addAttribute("pages", pages);
                break;
            }
            case "standart": {
                long appCounts = appartmentService.getAppartmentsCount(StandartAppartment.class);
                List<Integer> pages = getPages(appCounts);
                List<StandartAppartment> appartments = appartmentService.getAppartmentsByRange(0, itemCountsOnPage, StandartAppartment.class);
                model.addAttribute("appartments", appartments);
                model.addAttribute("pages", pages);
                break;
            }
            case "econom": {
                long appCounts = appartmentService.getAppartmentsCount(EconomAppartment.class);
                List<Integer> pages = getPages(appCounts);
                List<EconomAppartment> appartments = appartmentService.getAppartmentsByRange(0, itemCountsOnPage, EconomAppartment.class);
                model.addAttribute("appartments", appartments);
                model.addAttribute("pages", pages);
                break;
            }
        }
        return "user/appartments";
    }

    @GetMapping("/user/rent/{type}/{pageNumber}")
    public String goToRentPageWithPaging(@PathVariable String type, @PathVariable int pageNumber, Model model) {

        switch (type) {
            case "lux": {
                long appCounts = appartmentService.getAppartmentsCount(LuxAppartment.class);
                List<Integer> pages = getPages(appCounts);
                pageNumber = getCurrentPageNumber(pageNumber);
                List<LuxAppartment> appartments = appartmentService.getAppartmentsByRange(pageNumber - 1, itemCountsOnPage, LuxAppartment.class);
                model.addAttribute("appartments", appartments);
                model.addAttribute("pages", pages);
                break;
            }
            case "standart": {
                long appCounts = appartmentService.getAppartmentsCount(StandartAppartment.class);
                List<Integer> pages = getPages(appCounts);
                pageNumber = getCurrentPageNumber(pageNumber);
                List<StandartAppartment> appartments = appartmentService.getAppartmentsByRange(pageNumber - 1, itemCountsOnPage, StandartAppartment.class);
                model.addAttribute("appartments", appartments);
                model.addAttribute("pages", pages);

                break;
            }
            case "econom": {
                long appCounts = appartmentService.getAppartmentsCount(EconomAppartment.class);
                List<Integer> pages = getPages(appCounts);
                pageNumber = getCurrentPageNumber(pageNumber);
                List<EconomAppartment> appartments = appartmentService.getAppartmentsByRange(pageNumber - 1, itemCountsOnPage, EconomAppartment.class);
                model.addAttribute("appartments", appartments);
                model.addAttribute("pages", pages);
                break;
            }
        }


        return "user/appartments";
    }

    private int getCurrentPageNumber(int pageNumber) {
        if (pageNumber != 1) {
            pageNumber = (pageNumber - 1) * itemCountsOnPage + 1;
        }
        return pageNumber;
    }

    private List<Integer> getPages(long appCounts) {

        long pageCounts = appCounts / itemCountsOnPage;

        if (appCounts % itemCountsOnPage != 0) {
            pageCounts++;
        }

        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= pageCounts; i++) {
            pages.add(i);
        }

        return pages;
    }
}
