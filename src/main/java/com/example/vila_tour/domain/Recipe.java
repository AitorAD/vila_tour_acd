package com.example.vila_tour.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Un artículo de una receta
 * @author TeamAjo
 * @version Curso 2024-2025
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "recipes")
public class Recipe extends Article {

    @Schema(description = "Determina si la receta está aprobada por un administrador",
            example = "false", required = true)
    @Column(nullable = false)
    private boolean approved;

    @Schema(description = "Determina si la receta es nueva",
            example = "false", required = true)
    @Column(nullable = false)
    private boolean recent;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "id_recipe"),
            inverseJoinColumns = @JoinColumn(name = "id_ingredient"))
    private List<Ingredient> ingredients;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties("createdRecipes")
    private User creator;


}
