package com.example.vila_tour.service;

import com.example.vila_tour.domain.Role;
import com.example.vila_tour.domain.User;
import com.example.vila_tour.exception.UserNotFoundException;
import com.example.vila_tour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(long idUser) {
        return userRepository.findById(idUser);
    }

    @Override
    public Set<User> findByRole(Role role){
        return userRepository.findByRole(role);
    }

    @Override
    public Set<User> findByUsernameContaining(String username){
        return userRepository.findByUsernameContaining(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public Set<User> findByNameContaining(String name){
        return userRepository.findByNameContaining(name);
    }

    @Override
    public Set<User> findBySurnameContaining(String surname){
        return userRepository.findBySurnameContaining(surname);
    }



    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User modifyUser(long idUser, User newUser) {
       User user = userRepository.findById(idUser)
               .orElseThrow(() -> new UserNotFoundException(idUser));

       newUser.setId(user.getId());
       return userRepository.save(newUser);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean updatePasswordByEmail(String email, String newPassword) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }
}
