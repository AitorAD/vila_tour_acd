package com.example.vila_tour.service;

import com.example.vila_tour.domain.User;

import java.util.Optional;
import java.util.Set;

public interface UserService {

    Set<User> findAll();
    Optional<User> findById(long idUser);
    User addUser(User user);
    User modifyUser(long id, User newUser);
    void deleteUser(long id);
}
