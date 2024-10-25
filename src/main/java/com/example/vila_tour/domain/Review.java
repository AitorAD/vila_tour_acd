package com.example.vila_tour.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "article_id"})})
public class Review {

    @EmbeddedId
    private ReviewId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("articleId")
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Schema(description = "Puntuacion del usuario al articulo", example = "4.5")
    @Column
    private long rating;

    @Schema(description = "Comentario de la review", example = "Me gustó mucho, Javi RR un crack")
    @Column
    private String comment;

    @Schema(description = "Marca como favorito", example = "false")
    @Column
    private boolean favorite;
}
