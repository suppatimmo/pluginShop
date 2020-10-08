package com.sda.controller;

import com.sda.dto.CreateUserDto;
import com.sda.service.MailService;
import com.sda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import javax.validation.Valid;

@Controller
public class RegisterController {
    private UserService service;
    private MailService mailService;

    @Autowired
    public RegisterController(UserService service, MailService mailService) {
        this.service = service;
        this.mailService = mailService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model, Authentication authentication) {
        if (authentication != null)
            return "redirect:index";
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") @Valid CreateUserDto userDto,
                           BindingResult bindingResult, Model model) throws MessagingException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "error");
            return "register";
        }

        if (!service.isEmailExists(userDto.getEmail())) {
            service.registerNewUser(userDto);
            mailService.sendRegisterMail(userDto.getEmail());
            model.addAttribute("email", userDto.getEmail());
            return "redirect:authSend";
        } else {
            model.addAttribute("emailError", "emailError");
            return "register";
        }
    }
}
