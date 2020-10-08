package com.sda.service;

import com.sda.dto.CreateUserDto;
import com.sda.model.User;
import com.sda.repository.UserRepository;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerNewUser(CreateUserDto createUserDto) {
        User user = User.builder()
                .userName(createUserDto.getUserName())
                .email(createUserDto.getEmail())
                .login(createUserDto.getEmail())
                .password(passwordEncoder.encode(createUserDto.getPassword()))
                .pluginsCount(0)
                .wPLN(0)
                .keyToAuthorize(Base32.random())
                .enabled(false)
                .build();
        repository.save(user);
    }

    @Override
    public boolean deleteUser(User user) {
        try {
            repository.delete(user);
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmailExists(String email) {
        return repository.getUserByEmail(email).isPresent();
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return repository.getUserById(id);
    }

    @Override
    public Optional<User> getOptionalUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }

    @Override
    public Optional<User> getUserByKeyToAuthorize(String keytoAuthorize) {
        return repository.getUserByKeyToAuthorize(keytoAuthorize);
    }

    @Override
    public void authorizeUser(User user) {
        user.setEnabled(true);
        repository.save(user);
    }

    @Override
    public Optional<User> getLoggedUser(Authentication authentication) {
        if (Optional.ofNullable(authentication).isPresent()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = ((UserDetails) principal).getUsername();
            Optional<User> UserEntity = getOptionalUserByEmail(username);

            if (UserEntity.isPresent()) {
                return UserEntity;
            }
        }
        return Optional.empty();
    }

    @Override
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
