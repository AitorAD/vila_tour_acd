package com.example.vila_tour.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "article_id"})})
public class Review {

    @EmbeddedId
    private ReviewId id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @MapsId("article_id")
    @JoinColumn(name = "article_id", nullable = false)
    @JsonIgnore
    private Article article;

    @Schema(description = "Puntuacion del usuario al articulo", example = "4.5")
    @Column
    @Min(value = 0, message = "Debe ser mayo o igual a 0")
    @Max(value = 5, message = "Debe ser menor o igual a 5")
    private long rating;

    @Schema(description = "Comentario de la review", example = "Me gustó mucho, Javi RR un crack")
    @Column
    private String comment;
  
    @Schema(description = "Fecha de publicación", example = "11/11/2024")
    @Column
    private LocalDateTime postDate;
  

    @Schema(description = "Marca como favorito", example = "false")
    @Column
    private boolean favorite;

    // Método para sincronizar cambios en la puntuación
    @PostPersist
    @PostUpdate
    private void onPostPersistOrUpdate() {
        if (article != null) {
            article.updateAverageScore();
        }
    }

    @PostRemove
    private void onPostRemove() {
        if (article != null) {
            article.updateAverageScore();
        }
    }
}
