package com.example.vila_tour.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Un articulo, clase Padre de FyT, Lugares y Recipes
 * @author TeamAjo
 * @version Curso 2024-2025
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // Usa TABLE_PER_CLASS
public abstract class Article {
    @Schema(description = "identificador del articulo", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)  // Esto es válido para TABLE_PER_CLASS
    private Long id;
    @Schema(description = "Nombre del articulo", example = "Pebrereta", required = true)
    @Column(unique = true)
    private String name;
    @Schema(description = "Descripcion del articulo", example = "La mejor receta")
    @Column
    private String description;
    @Schema(description = "Ruta de la imagen de la receta", example = "https://imagen.es")
    @Column
    private String imagePath;
    @Schema(description = "Puntuacion media del articulo", example = "4,5", defaultValue = "0.00")
    @Column
    private double averageScore;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

}
