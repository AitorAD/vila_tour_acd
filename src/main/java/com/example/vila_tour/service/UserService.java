package com.example.vila_tour.service;

import com.example.vila_tour.domain.Role;
import com.example.vila_tour.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface UserService {

    Set<User> findAll();
    Optional<User> findById(long idUser);
    Set<User> findByUsername(String username);
    Set<User> findByEmail(String email);
    Set<User> findByRole(Role role);
    Set<User> findByName(String name);
    Set<User> findBySurname(String surname);

    Set<User> findByUsernameContaining(String username);
    Set<User> findByEmailContaining(String email);
    Set<User> findByNameContaining(String name);
    Set<User> findBySurnameContaining(String surname);


    User addUser(User user);
    User modifyUser(long id, User newUser);
    void deleteUser(long id);
}
