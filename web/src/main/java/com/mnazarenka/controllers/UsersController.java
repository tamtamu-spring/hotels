package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.service.RoleService;
import com.mnazarenka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UsersController {

    public UserService userService;
    public RoleService roleService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @ModelAttribute("user")
    public User user(){
        return new User();
    }

    @ModelAttribute("users")
    public List<User> allUsers() {
        return userService.findAll();
    }

    @ModelAttribute("roles")
    public List<Role> allRoles() {
        return roleService.findAll();
    }

    @GetMapping("/admin/users")
    public String getUsers(){
        return "admin/users";
    }

    @PostMapping("/admin/users")
    public String createUser(User user, String roleId){
        userService.createUserWithRoleId(user, Long.valueOf(roleId));
        return "redirect:/admin/users";
    }
}
