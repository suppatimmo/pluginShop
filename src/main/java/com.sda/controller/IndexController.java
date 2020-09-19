package com.sda.controller;

import com.sda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.sda.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    private UserService service;

    @Autowired
    public IndexController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = {"/", "/index"})
    public String index(@RequestParam(value = "user", required = false) User user, Model model) {
        model.addAttribute("user", user);
        {
            return "index";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

