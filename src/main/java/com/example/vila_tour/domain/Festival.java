package com.example.vila_tour.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "festivals")
public class Festival extends Article {
    @Schema(description = "Fecha de inicio del festival o tradición", example = "11/11/2024", required = true)
    @NotNull
    @Column
    private LocalDate startDate;

    @Schema(description = "Fecha que marca el final del festival o tradición", example = "14/11/2024")
    @Column
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties("createdFestivals")
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinate_id")
    private Coordinate coordinate;
}
