package com.sda.service;

import com.sda.dto.CreateUserDto;
import com.sda.model.User;
import com.sda.repository.UserRepository;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
