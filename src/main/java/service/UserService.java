package service;

import dto.CreateUserDto;
import model.User;

public interface UserService {
    void registerNewUser(CreateUserDto createUserDto);
    boolean deleteUser(User user);

}
