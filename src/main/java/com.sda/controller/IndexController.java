package com.sda.controller;

import com.sda.model.User;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = ((UserDetails)principal).getUsername();
            Optional<User> UserEntity = service.getOptionalUserByEmail(username);

            if (UserEntity.isPresent()) {
                return UserEntity;
            }
        }
        return Optional.empty();
    }

    public void getUserAccountView(final Model model, Authentication authentication) {
        Optional<User> loggedUserOptional = getLoggedUser(authentication);
        if (loggedUserOptional.isPresent()) {
            User loggedUser = loggedUserOptional.get();
            model.addAttribute("userName", loggedUser.getUserName());
            model.addAttribute("pluginsCount", loggedUser.getPluginsCount());
            model.addAttribute("wPLN", loggedUser.getWPLN());
        }
    }
}

