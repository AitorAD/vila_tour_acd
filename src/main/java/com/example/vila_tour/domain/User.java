package com.example.vila_tour.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long idUser;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "email", unique = true)
    private String email;
    @JsonIgnore
    @Column
    private String password;
    @Column
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Override
    public String toString(){
        return  "ID: " + idUser + "\nNombre: " + username +
        "\nEmail: " + email + "\nPassword: " + password +
        "\nRole: " + role;
    }
}
