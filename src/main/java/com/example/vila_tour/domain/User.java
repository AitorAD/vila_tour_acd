package com.example.vila_tour.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
