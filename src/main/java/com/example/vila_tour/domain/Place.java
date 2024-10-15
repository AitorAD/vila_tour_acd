package com.example.vila_tour.domain;


import jakarta.persistence.Column;

public class Place extends Article{
   @Column
   private PlaceCategory placeCategory;
   @Column
   private Coordinate coordinatesPlace;


   //TODO
}
