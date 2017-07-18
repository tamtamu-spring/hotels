package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.Appartment;
import com.mnazarenka.dao.entity.AppartmentOrder;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.dao.enums.Status;
import com.mnazarenka.service.AppartmentService;
import com.mnazarenka.service.OrderService;
import com.mnazarenka.service.UserService;
import com.mnazarenka.service.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @ModelAttribute("orders")
    public List<AppartmentOrder> orders(Authentication auth) {
        User user = userService.getUserByLogin(auth.getName());
        List<AppartmentOrder> orders = orderService.findAllByUserId(user.getId());
        return orders;
    }

    @ModelAttribute("allOrders")
    public List<AppartmentOrder> allOrders() {
        return orderService.findAll();
    }

    @ModelAttribute("users")
    public List<User> users() {
        return userService.findAll();
    }

    @ModelAttribute("appartments")
    public List<Appartment> appartments() {
        return appartmentService.findAll();
    }

    @PostMapping("/admin/orders/update")
    public String updateOrderByAdmin(long appartId, long userId, AppartmentOrder order) {
        try {
            orderService.updateOrder(appartId, userId, order);
        } catch (HibernateOptimisticLockingFailureException e) {
            e.printStackTrace();
            System.out.println("optimistic lock exception");
        }
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/orders")
    public String getAdminOrders() {
        return "admin/orders";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getAdminOrderUpdatePage(@PathVariable long id, Model model) {
        AppartmentOrder appartmentOrder = orderService.find(id);
        model.addAttribute("order", appartmentOrder);
        return "update/admin-order";
    }

    @GetMapping("/user/account/orders")
    private String getOrdersPage() {
        return "user/orders";
    }

    @GetMapping("/user/orders/update/{id}")
    public String getUpdatePage(@PathVariable long id, Model model) {
        AppartmentOrder appartmentOrder = orderService.find(id);
        model.addAttribute("order", appartmentOrder);

        return "update/user-order";
    }

    @PostMapping("/user/orders/update")
    public String updateOrder(@Valid AppartmentOrder order, long userId, long appartId, Model model, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "redirect:" + request.getHeader("referer");
        }

        if (order.getStartDate().isAfter(order.getEndDate())) {
            bindingResult.addError(new ObjectError("dateRangeErr", "Неверный период дат"));
            return "redirect:" + request.getHeader("referer");
        }

        try {
            orderService.updateOrder(appartId, userId, order);
        } catch (HibernateOptimisticLockingFailureException e) {
            e.printStackTrace();
            bindingResult.addError(new ObjectError("lockError", "Ваши данные устарели"));
            return "redirect:" + request.getHeader("referer");
        }

        return "redirect:/user/account/orders";
    }

    @GetMapping("/{user}/orders/delete/{id}")
    public String deleteOrder(@PathVariable long id, @PathVariable String user) {
        orderService.deleteById(id);

        if (user.equalsIgnoreCase(RoleEnum.USER.toString())) {
            return "redirect:/user/account/orders";
        }

        return "redirect:/admin/orders";
    }

    @PostMapping("admin/orders/reject")
    public String rejectOrder(long appartId, long userId, AppartmentOrder order) {
        orderService.changeStatus(Status.REJECTED, appartId, userId, order);
        return "redirect:/admin/orders";
    }

    @PostMapping("admin/orders/confirm")
    public String confirmOrder(long appartId, long userId, AppartmentOrder order) {
        orderService.changeStatus(Status.CONFIRMED, appartId, userId, order);
        return "redirect:/admin/orders";
    }
}
