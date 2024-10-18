package com.example.vila_tour.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @Column
    private Coordinade coordinade;
}
