package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RolesService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RolesService rolesService;

    public AdminController(UserService userService, RolesService rolesService) {
        this.userService = userService;
        this.rolesService = rolesService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("allRoles", rolesService.getAllRoles());
        System.out.println(1);
        return "admin/show-users";
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", rolesService.getAllRoles());
        return "admin/create-user";
    }

    @PostMapping("/addUser")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam("roles") String[] roles) {
        System.out.println("1");
            userService.addUser(user, roles);
         return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String returnUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("nowUser", userService.getUserById(id));
        model.addAttribute("allRoles", rolesService.getAllRoles());
        System.out.println(1);
        return "admin/show-users";
    }
    @PatchMapping("edit/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam("roles") String[] roles) {
        userService.updateUser(user, roles);
        System.out.println(1);
        return "redirect:/admin";
    }

    @DeleteMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") int id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
