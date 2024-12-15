package com.example.vila_tour.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Ingrediente para incluir en recetas
 * @author TeamAjo
 * @version Curso 2024-2025
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ingredients")
public class Ingredient {

    @Schema(description = "Identificador del ingrediente", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idIngredient;

    @Schema(description = "Nombre del ingrediente", example = "Pimiento", required = true)
    @Column(unique = true)
    private String name;

    @Schema(description = "Categoría a la que pertenece el ingrediente", example = "Frutas y Verduras")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryIngredient category;

    @ManyToMany(cascade = CascadeType.DETACH, mappedBy = "ingredients")
    @JsonIgnore
    private List<Recipe> recipes;
}
