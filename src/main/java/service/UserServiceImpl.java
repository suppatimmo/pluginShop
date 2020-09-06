package service;

import dto.CreateUserDto;
import model.ROLE;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;

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
                .Role(ROLE.USER)
                .pluginsCount(0)
                .wPLN(0)
                .build();
        repository.save(user);
    }

    @Override
    public boolean deleteUser(User user) {
        try {
            repository.delete(user);
        }
        catch(NullPointerException e) {
            return false;
        }
        return true;
    }
}
