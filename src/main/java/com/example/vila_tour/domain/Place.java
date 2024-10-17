package com.example.vila_tour.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@DiscriminatorValue("PLACE")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "places")
public class Place extends Article {

   @Enumerated(EnumType.STRING) // Asumiendo que PlaceCategory es un enum
   @Column(nullable = false) // Asegúrate de que no sea nulo
   private PlaceCategory placeCategory;

   @Column(nullable = false) // Asegúrate de que no sea nulo
   private Coordinate coordinatesPlace;

   // Otros métodos y lógica pueden ir aquí si es necesario
}
