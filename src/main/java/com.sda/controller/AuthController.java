package com.sda.controller;

import com.sda.model.User;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class AuthController {

    private UserService service;

    @Autowired
    public AuthController(UserService service) {
        this.service = service;
    }

    @RequestMapping("/auth={key}")
    public String auth(@PathVariable(value = "key") String authKey) {
        Optional<User> user = service.getUserByKeyToAuthorize(authKey);
        if (user.isPresent()) {
            service.authorizeUser(user.get());
            return "redirect:authSuccess";
        } else
            return "redirect:authFailure";
    }

    @GetMapping(value = {"authSuccess"})
    public String authSuccess(Model model) {
        return "authSuccess";
    }

    @GetMapping(value = {"authFailure"})
    public String authFailure(Model model) {
        return "authFailure";
    }

    @GetMapping(value = {"/authSend"})
    public String authSend(Model model) {
        return "authSend";
    }
}
