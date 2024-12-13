package com.example.vila_tour.service;

import com.example.vila_tour.domain.Role;
import com.example.vila_tour.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    List<User> findAll();
    Optional<User> findById(long idUser);
    Set<User> findByRole(Role role);
    Optional<User> findByUsername(String username);
    Set<User> findByUsernameContaining(String username);
    Optional<User> findByEmail(String email);
    Set<User> findByNameContaining(String name);
    Set<User> findBySurnameContaining(String surname);
    Boolean existsByEmail(String email);

    User addUser(User user);
    User modifyUser(long id, User newUser);
    void deleteUser(long id);
}
