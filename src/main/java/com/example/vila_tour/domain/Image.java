package com.example.vila_tour.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "images")
public class Image {
    @Schema(description = "Identificador de la imagen", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Esto es válido para TABLE_PER_CLASS
    private Long id;
    @Schema(description = "Imagen en base64", example = "[https://imagen1.es]")
    @Column(length = Integer.MAX_VALUE) // Marco el length para que en la bd el campo se almacene como long text
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    // @JsonIncludeProperties(value = {"id", "name"})
    private Article article;
}
