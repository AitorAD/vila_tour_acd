package com.example.vila_tour.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Las distintas categorias a las que puede pertenecer un lugar
 * @author TeamAjo
 * @version Curso 2024-2025
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "category_place")
public class CategoryPlace {

    @Schema(description = "Identificador de la categoria del lugar", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Nombre de la categoria del lugar", example = "Monumento histórico")
    @Column
    private String name;

    @OneToMany(mappedBy = "categoryPlace", cascade = CascadeType.REFRESH, orphanRemoval = false)
    @JsonIgnore
    private List<Place> places;

}
