package com.sda.service;

import com.sda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;
    private UserService userService;

    @Autowired
    public MailService(JavaMailSender javaMailSender, TemplateEngine templateEngine, UserService userService) throws MessagingException {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.userService = userService;
    }

    public void sendMail(String to,
                         String subject,
                         String text,
                         boolean isHtmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        javaMailSender.send(mimeMessage);
    }

    public void sendRegisterMail(String to) throws MessagingException {
        User user = userService.getUserByEmail(to);
        Context context = new Context();
        context.setVariable("header", "Rejestracja w serwisie smPlugins.pl");
        context.setVariable("title", "Witaj " + user.getUserName() + "!");
        context.setVariable("main", "Otrzymałeś tego maila, ponieważ ten adres e-mail został podany podczas" +
                "rejestracji w serwisie smPlugins.pl");
        context.setVariable("body", "Jeśli to nie Ty zakładałeś konto, zignoruj tę wiadomość.");
        context.setVariable("underBody", "By aktywować konto kliknij w link poniżej!");
        context.setVariable("key", user.getKeyToAuthorize());
        String body = templateEngine.process("mailTemplate", context);
        sendMail(to, "smPlugins.pl - potwierdzenie rejestracji", body, true);
    }
}
