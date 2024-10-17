package com.example.vila_tour.service;

import com.example.vila_tour.domain.User;
import com.example.vila_tour.exception.RecipeNotFoundException;
import com.example.vila_tour.exception.UserNotFoundException;
import com.example.vila_tour.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Set<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(long idUser) {
        return userRepository.findById(idUser);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User modifyUser(long idUser, User newUser) {
        if (!userRepository.existsById(idUser)) {
            throw new UserNotFoundException("User ID " + idUser + " not found");
        } else {
            Optional<User> user = userRepository.findById(idUser);
            assert user.isPresent();
            newUser.setIdUser(user.get().getIdUser());
            return userRepository.save(newUser);
        }
    }

    @Override
    public void deleteUser(long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
    }
}
