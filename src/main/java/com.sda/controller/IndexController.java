package com.sda.controller;

import com.sda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.sda.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@Controller
public class IndexController {
    private UserService service;

    @Autowired
    public IndexController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = {"/", "/index"})
    public String index(Model model, Authentication authentication) {
        getUserAccountView(model, authentication);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    private Optional<User> getLoggedUser(Authentication authentication) {
        if (Optional.ofNullable(authentication).isPresent()) {
            Optional<User> userDetailsOptional = Optional.ofNullable((User) authentication.getPrincipal());
            if (userDetailsOptional.isPresent()) {
                User userDetails = userDetailsOptional.get();
                return service.getUserById(userDetails.getId());// Ta metoda może się różnie nazwyać w zalezności jak dałeś w serwisie Usera np. getById albo get itd.
            }
        }
        return Optional.empty();
    }

    public void getUserAccountView(final Model model, Authentication authentication) {
        Optional<User> loggedUserOptional = getLoggedUser(authentication);
        if (loggedUserOptional.isPresent()) {
            User loggedUser = loggedUserOptional.get();
            model.addAttribute("userName", loggedUser.getUserName());
            System.out.println(loggedUser.getUserName());
            System.out.println(loggedUser.getWPLN());
        }
    }
}

