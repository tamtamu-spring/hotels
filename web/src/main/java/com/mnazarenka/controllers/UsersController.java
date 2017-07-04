package com.mnazarenka.controllers;

import com.mnazarenka.dao.entity.Role;
import com.mnazarenka.dao.entity.User;
import com.mnazarenka.service.RoleService;
import com.mnazarenka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String createUser(User user, long roleId){
        userService.createUserWithRoleId(user, roleId);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable("id") long id){
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/update/{id}")
    public String goToUpdatePage(@PathVariable("id") long id, Model model){
        User user = userService.find(id);
        model.addAttribute("user", user);
        return "update/update-user";
    }

    @PostMapping("/admin/users/update")
    public String updateUser(User user, long roleId){
        userService.updateUserWithRoleId(user, roleId);
        return "redirect:/admin/users";
    }
}
