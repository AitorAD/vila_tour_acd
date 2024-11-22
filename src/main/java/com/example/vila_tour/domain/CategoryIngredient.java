package com.example.vila_tour.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Las distintas categorías a las que puede pertenecer un ingrediente
 * @author TeamAjo
 * @version Curso 2024-2025
 */
@Data

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "category_ingredient")
public class CategoryIngredient {

    @Schema(description = "Identificador de la categoria del ingrediente", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Nombre de la categoria de ingrediente", example = "Frutas y Verduras", required = true)
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REFRESH, orphanRemoval = false)
    @JsonIgnore
    private List<Ingredient> ingredients;
}
