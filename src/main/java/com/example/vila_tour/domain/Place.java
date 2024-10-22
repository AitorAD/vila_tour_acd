package com.example.vila_tour.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "places")
public class Place extends Article {

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private PlaceCategory placeCategory;

   @Column(nullable = false)
   private Coordinade coordinadesPlace;

   // Otros métodos y lógica pueden ir aquí si es necesario
}
