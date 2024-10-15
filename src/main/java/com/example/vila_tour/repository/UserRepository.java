package com.example.vila_tour.repository;

import com.example.vila_tour.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {
    Set<User> findAll();
}
