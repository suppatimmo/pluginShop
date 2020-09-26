package com.sda.controller;

import com.sda.service.AccountViewService;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    private UserService service;
    private AccountViewService accountView;

    @Autowired
    public IndexController(UserService service) {
        this.accountView = accountView;
        this.service = service;
    }

    @GetMapping(value = {"/", "/index"})
    public String index(Model model, Authentication authentication) {
        if (authentication != null)
            accountView.getUserAccountView(model, authentication);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, Authentication authentication) {
        if (authentication != null)
            accountView.getUserAccountView(model, authentication);
        return "login";
    }
}

