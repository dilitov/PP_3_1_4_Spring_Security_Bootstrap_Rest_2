package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


}
