package com.sda.service;

import com.sda.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.util.Optional;

public class AccountViewService {

    private UserService service;

    private Optional<User> getLoggedUser(Authentication authentication) {
        if (Optional.ofNullable(authentication).isPresent()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = ((UserDetails) principal).getUsername();
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