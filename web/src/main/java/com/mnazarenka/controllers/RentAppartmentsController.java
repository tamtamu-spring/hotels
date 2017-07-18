package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.utils.AppartmentUtil;
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
    private AppartmentUtil appartmentUtil;

    //@Value("#{new Integer.parseInt('${items.counts}')}")
    private Integer itemCountsOnPage = 3;

    @Autowired
    public RentAppartmentsController(AppartmentService appartmentService, OrderService orderService, AppartmentUtil appartmentUtil) {
        this.appartmentService = appartmentService;
        this.orderService = orderService;
        this.appartmentUtil = appartmentUtil;
    }

    @ModelAttribute("order")
    public AppartmentOrder order(){
        return new AppartmentOrder();
    }

    @PostMapping("/user/order")
    public String saveOrder(long apartId, AppartmentOrder order, Authentication auth, String appartType){
        String userName = auth.getName();
        Class appartmentClass = appartmentUtil.getAppartmentClass(appartType);

        orderService.createOrder(appartmentClass, apartId, userName, order);

        return "redirect:/user/account/orders";
    }

    @GetMapping("/user/rent/{type}")
    public String goToRentPage(@PathVariable String type, Model model) {
        Class appartmentClass = appartmentUtil.getAppartmentClass(type);

        long appCounts = appartmentService.getAppartmentsCount(appartmentClass);
        List<Integer> pages = getPages(appCounts);
        List<? extends Appartment> appartments = appartmentService.getAppartmentsByRange(0, itemCountsOnPage, appartmentClass);
        model.addAttribute("appartments", appartments);
        model.addAttribute("pages", pages);

        return "user/appartments";
    }

    @GetMapping("/user/rent/{type}/{pageNumber}")
    public String goToRentPageWithPaging(@PathVariable String type, @PathVariable int pageNumber, Model model) {
        Class appartmentClass = appartmentUtil.getAppartmentClass(type);

        long appCounts = appartmentService.getAppartmentsCount(appartmentClass);
        List<Integer> pages = getPages(appCounts);
        pageNumber = getCurrentPageNumber(pageNumber);
        List<? extends Appartment> appartments = appartmentService.getAppartmentsByRange(pageNumber - 1, itemCountsOnPage, appartmentClass);
        model.addAttribute("appartments", appartments);
        model.addAttribute("pages", pages);

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
