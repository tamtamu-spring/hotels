package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.service.AppartmentService;
import com.mnazarenka.service.OrderService;
import com.mnazarenka.service.UserService;
import com.mnazarenka.service.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class OrdersController {
    private final OrderService orderService;
    private UserService userService;
    private AppartmentService appartmentService;

    @Autowired
    public OrdersController(OrderService orderService, UserService userService, AppartmentService appartmentService) {
        this.orderService = orderService;
        this.userService = userService;
        this.appartmentService = appartmentService;

    }
/*

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException{
                setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }

            @Override
            public String getAsText() throws IllegalArgumentException {
                return DateTimeFormatter.ofPattern("yyyy-MM-dd").format((LocalDate) getValue());
            }
        });
    }
*/

    @ModelAttribute("orders")
    public List<AppartmentOrder> orders(Authentication auth){
        User user = userService.getUserByLogin(auth.getName());
        List<AppartmentOrder> orders = orderService.findAllByUserId(user.getId());
        return orders;
    }

    @ModelAttribute("allOrders")
    public List<AppartmentOrder> allOrders(){
        return orderService.findAll();
    }

    @ModelAttribute("users")
    public List<User> users(){
        return userService.findAll();
    }

    @ModelAttribute("appartments")
    public List<Appartment> appartments(){
        return appartmentService.findAll();
    }

    @PostMapping("/admin/orders/update")
    public String updateOrderByAdmin(long orderId, long userId, long appartId, String startDate, String endDate){
        orderService.updateOrder(orderId, userId, appartId, startDate, endDate);
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/orders")
    public String getAdminOrders(){
        return "admin/orders";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getAdminOrderUpdatePage(@PathVariable long id, Model model){
        AppartmentOrder appartmentOrder = orderService.find(id);
        model.addAttribute("order", appartmentOrder);
        return "update/admin-order";
    }

    @GetMapping("/user/account/orders")
    private String getOrdersPage(){
        return "user/orders";
    }

    @GetMapping("/user/orders/update/{id}")
    public String getUpdatePage(@PathVariable long id, Model model){
        AppartmentOrder appartmentOrder = orderService.find(id);
        model.addAttribute("order", appartmentOrder);

        return "update/user-order";
    }

    @PostMapping("/user/orders/update")
    public String updateOrder(AppartmentOrder order, long userId, long appartId){
       orderService.updateOrder(userId, appartId, order);

        return "redirect:/user/account/orders";
    }

    @GetMapping("/{user}/orders/delete/{id}")
    public String deleteOrder(@PathVariable long id, @PathVariable String user){
        orderService.deleteById(id);

        if(user.equalsIgnoreCase(RoleEnum.USER.toString())){
            return "redirect:/user/account/orders";
        }

        return "redirect:/admin/orders";
    }
}
