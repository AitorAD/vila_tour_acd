package com.example.vila_tour.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteRequestDTO {

    private List<List<Double>> coordinates; // Listado de coordenadas
    private String profile; // El perfil de transporte (por ejemplo, "driving-car", "cycling-regular")
}
