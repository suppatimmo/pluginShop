package com.sda.controller;


import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginAndRegisterController {

    private UserService service;

    @Autowired
    public LoginAndRegisterController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(defaultValue = " ") String email, @RequestParam(defaultValue = " ") String password, Model model) {
        if (service.isUserValid(email, password)) {
            return "index";
        } else {
            model.addAttribute("error", "error");
            return "login";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam(defaultValue = " ") String email, @RequestParam(defaultValue = " ") String password, Model model) {
        if (service.isUserValid(email, password)) {
            return "index";
        } else {
            model.addAttribute("error", "error");
            return "login";
        }
    }

    @GetMapping("/register")
    public String login() {
        return "register";
    }
}
