package com.sda.repository;

import com.sda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> getUserById(Integer id);
    User getUserByEmailAndPassword(String email, String password);
    List<User> findAll();
    User findByEmail(String email);
    Optional<User> getUserByEmail(String email);
}
