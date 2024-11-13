package com.example.vila_tour.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Schema(description = "Identificador del articulo", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)  // Esto es válido para TABLE_PER_CLASS
    private Long id;
    @Schema(description = "Nombre del articulo", example = "Pebrereta", required = true)
    @Column(unique = true)
    private String name;
    @Schema(description = "Descripcion del articulo", example = "La mejor receta")
    @Column
    private String description;
    @Schema(description = "Ruta de las imagenes dela rtículo", example = "[https://imagen1.es, https://imagen1.es]")
    @Column
    private List<String> imagensPaths;
    @Schema(description = "Puntuacion media del articulo", example = "4,5", defaultValue = "0.00")
    @Column
    private double averageScore;
    @Schema(description = "Fecha de creación del articulo", example = "12/12/2024 12:00")
    @Column
    private LocalDateTime creationDate;
    @Schema(description = "fecha de modificacion del articulo", example = "12/12/2024 12:00")
    @Column
    private LocalDateTime lastModificationDate;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

}
