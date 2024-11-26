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
    Optional<User> findById(long id);
    Set<User> findByRole(Role role);
    Optional<User> findByUsername(String username);

    // Buscar por que contenga el string en el username
    @Query("SELECT u FROM user u WHERE u.username LIKE %:username%")
    Set<User> findByUsernameContaining(@Param("username") String userName);

    // Buscar por que contenga el string en el email
    @Query("SELECT u FROM user u WHERE u.email LIKE %:email%")
    Set<User> findByEmailContaining(@Param("email") String email);

    // Buscar por que contenga el string en el name
    @Query("SELECT u FROM user u WHERE u.name LIKE %:name%")
    Set<User> findByNameContaining(@Param("name") String name);

    // Buscar por que contenga el string en el apellidos
    @Query("SELECT u FROM user u WHERE u.surname LIKE %:surname%")
    Set<User> findBySurnameContaining(@Param("surname") String surname);
}
