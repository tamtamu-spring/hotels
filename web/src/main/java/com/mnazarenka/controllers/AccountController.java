package com.mnazarenka.controllers;

import com.mnazarenka.service.UserService;
import com.mnazarenka.service.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class AccountController {
    private UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/my-account")
    public String goToPersonalAccount(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals(RoleEnum.ADMIN.toString()))) {
            return "redirect:/admin/users";
        }

        return "redirect:user/rent-appartments";
    }
}
