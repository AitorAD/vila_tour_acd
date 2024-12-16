package com.example.vila_tour.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "routes")
public class Route {

    @Schema(description = "Identificador de la ruta", example = "1")

    @Id
    private long id;

    @ManyToMany
    private List<Place> places;

    @Schema(description = "Coordenada inicial de la ruta", example = "1")
    private Coordinate initialCoordinate;

    @Schema(description = "Coordenada final de la rurta", example = "1")
    private Coordinate finalCoordinate = places.getLast().getCoordinate();




}
