package com.example.vila_tour.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long idUser;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private Role role;

    @Override
    public String toString(){
        return  "ID: " + idUser + "\nNombre: " + username +
        "\nEmail: " + email + "\nPassword: " + password +
        "\nRole: " + role;
    }
}
