package com.example.vila_tour.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "places")
public class Place extends Article {

   @Schema(description = "Categoría a la que pertenece el ingredeitne", example = "Monumento Histórico")
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "category_id")
   private CategoryPlace categoryPlace;

   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "coordenada_id", referencedColumnName = "id")
   private Coordinade coordinade;

}
