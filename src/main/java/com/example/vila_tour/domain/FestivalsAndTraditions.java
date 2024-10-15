package com.example.vila_tour.domain;

import jakarta.persistence.Column;

import java.time.LocalDate;


public class FestivalsAndTraditions extends Article{
    @Column
    private LocalDate datFestival;
    @Column
    private  Coordinate coordinate;

    //TODO
}
