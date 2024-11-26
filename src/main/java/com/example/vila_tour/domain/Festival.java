package com.example.vila_tour.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Schema(description = "Coordenadas del lugar", example = "0,0")
    @Column
    private Coordinade coordinade;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
    private User creator;
}
