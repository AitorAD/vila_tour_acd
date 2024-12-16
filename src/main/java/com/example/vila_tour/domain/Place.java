package com.example.vila_tour.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "places")
public class Place extends Article {

   @Schema(description = "Categoría a la que pertenece el ingredeitne", example = "Monumento Histórico")
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "category_id")
   private CategoryPlace categoryPlace;

   @ManyToOne
   @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
   @JsonIgnoreProperties("createdPlaces")
   private User creator;

   @OneToOne(cascade = CascadeType.MERGE)
   @JoinColumn(name = "coordinate_id", referencedColumnName = "id")
   private Coordinate coordinate;
}
