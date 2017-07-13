package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.User;
import com.mnazarenka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.annotation.SessionScope;

@Controller
public class LoginController {
    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public User user(){
        return new User();
    }

    @GetMapping("/login")
    public String login(){
        return "autorization";
    }

    @GetMapping("/registration")
    public String registrationForm(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(User user, String confirmPassword){
        userService.createUserWithUserRole(user);
        return "autorization";
    }
}
