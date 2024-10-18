package com.example.vila_tour.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // Usa TABLE_PER_CLASS
public abstract class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)// Esto es válido para TABLE_PER_CLASS
    private Long id;
    @Column(unique = true)
    private String name;
    @Column
    private String description;
    @Column
    private String imagePath;
    @Column
    private double averageScore;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "reviews",
                joinColumns = {@JoinColumn(name = "id_article")},
                inverseJoinColumns = {@JoinColumn(name = "id_user")})
    private List<User> reviewers;

    //TODO metodos

}
