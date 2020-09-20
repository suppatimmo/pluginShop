package com.sda.service;

import com.sda.dto.CreateUserDto;
import com.sda.model.User;
import com.sda.repository.UserRepository;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public void registerNewUser(CreateUserDto createUserDto) {
        User user = User.builder()
                .userName(createUserDto.getUserName())
                .email(createUserDto.getEmail())
                .login(createUserDto.getLogin())
                .password(createUserDto.getPassword())
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
    public boolean isUserValid(String email, String password) {
        return repository.getUserByEmailAndPassword(email, password) != null;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
}
