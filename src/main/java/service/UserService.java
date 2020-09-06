package service;

import dto.CreateUserDto;
import model.User;

public interface UserService {
    void RegisterNewUser(CreateUserDto createUserDto);
    boolean DeleteUser(User user);

}
