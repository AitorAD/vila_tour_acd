package com.example.vila_tour.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long idIngredient;

    @Column
    private String name;

    @Column
    private CategoryIngredient category;

    @ManyToMany(cascade = CascadeType.DETACH, mappedBy = "ingredients")
    private List<Recipe> recipes;
}
