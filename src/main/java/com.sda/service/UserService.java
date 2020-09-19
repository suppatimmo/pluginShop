package com.sda.service;

import com.sda.dto.CreateUserDto;
import com.sda.model.User;

import java.util.List;

public interface UserService {
    void registerNewUser(CreateUserDto createUserDto);
    boolean deleteUser(User user);
    boolean isUserValid(String email, String password);
    List<User> findAll();
}
