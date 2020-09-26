package com.sda.service;

import com.sda.dto.CreateUserDto;
import com.sda.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void registerNewUser(CreateUserDto createUserDto);
    boolean deleteUser(User user);
    boolean isEmailExists(String email);
    List<User> findAll();
    User getUserByEmail(String email);
    Optional<User> getUserById(Integer id);
    Optional<User> getOptionalUserByEmail(String email);
    Optional<User> getUserByKeyToAuthorize(String keytoAuthorize);
    void authorizeUser(User user);
}
