package com.example.vila_tour.service;

import com.example.vila_tour.domain.CategoryPlace;
import com.example.vila_tour.domain.Coordinate;

import java.util.Optional;
import java.util.Set;

public interface CoordinateService {
    Set<Coordinate> findAll();
    Optional<Coordinate> findById(long id);

    Coordinate addCoordinate(Coordinate coordinate);
    Coordinate modifyCoordinate(long id, Coordinate newCoordinate);
    void deleteCoordinate(long id);
}
