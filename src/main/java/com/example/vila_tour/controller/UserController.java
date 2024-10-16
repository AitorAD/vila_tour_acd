package com.example.vila_tour.controller;

import com.example.vila_tour.domain.User;
import com.example.vila_tour.exception.UserNotFoundException;
import com.example.vila_tour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/users")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "")
    public ResponseEntity<Set<User>> getUser() {
        Set<User> users;
        users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id){
        User user = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User addedUser = userService.addUser(user);
        return new ResponseEntity<>(addedUser, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> modifyUser(@PathVariable long id,
                                           @RequestBody User user){
        User newUser = userService.modifyUser(id, user);
        return new ResponseEntity<>(newUser,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response>
    handleException(UserNotFoundException unfe) {
        Response response = Response.errorResponse(Response.NOT_FOUND,
                unfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
