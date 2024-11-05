package com.example.vila_tour.repository;

import com.example.vila_tour.domain.Role;
import com.example.vila_tour.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Set<User> findAll();
    Optional<User> findById();
    Set<User> findByUsername(String username);
    Set<User> findByEmail(String email);
    Set<User> findByRole(Role role);
    Set<User> findByName(String name);
    Set<User> findBySurname(String surname);

    // Buscar por que contenga el string en el username
    @Query("SELECT u FROM users u WHERE u.username LIKE %:usernameUser%")
    Set<User> findByUsernameContaining(@Param("usernameUser") String userName);

    // Buscar por que contenga el string en el email
    @Query("SELECT u FROM users u WHERE u.email LIKE %:emailUser%")
    Set<User> findByEmailContaining(@Param("emailUser") String email);

    // Buscar por que contenga el string en el name
    @Query("SELECT u FROM users u WHERE u.name LIKE %:nameUser%")
    Set<User> findByNameContaining(@Param("nameUser") String name);

    // Buscar por que contenga el string en el apellidos
    @Query("SELECT u FROM users u WHERE u.surname LIKE %:surnameUser%")
    Set<User> findBySurnameContaining(@Param("surnameUser") String surname);
}
