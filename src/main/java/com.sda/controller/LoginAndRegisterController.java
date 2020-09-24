package com.sda.controller;


import com.sda.dto.CreateUserDto;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class LoginAndRegisterController {

    private UserService service;

    @Autowired
    public LoginAndRegisterController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {

        model.addAttribute("user", new CreateUserDto());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") @Valid CreateUserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!service.isUserValid(userDto.getEmail(), userDto.getPassword())) {
            service.registerNewUser(userDto);

            return "redirect:index";
        } else {
            model.addAttribute("error", "error");
            return "login";
        }
    }
}
