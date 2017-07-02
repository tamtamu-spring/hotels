package com.mnazarenka.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {

    @GetMapping("/admin/users")
    public String getUsers(){
        return "admin/users";
    }

}
