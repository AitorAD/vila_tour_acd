package com.example.vila_tour.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "recipes")
public class Recipe extends Article {

    @Column(name = "is_approved") // Cambiar a is_approved
    private boolean isApproved;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "id_recipe"),
            inverseJoinColumns = @JoinColumn(name = "id_ingredient"))
    private List<Ingredient> ingredients; // Mantener la lista de ingredientes.
}
