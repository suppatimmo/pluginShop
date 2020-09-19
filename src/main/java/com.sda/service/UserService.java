package com.sda.service;

import com.sda.dto.CreateUserDto;
import com.sda.model.User;

public interface UserService {
    void registerNewUser(CreateUserDto createUserDto);
    boolean deleteUser(User user);
    boolean isUserValid(String email, String password);

}
