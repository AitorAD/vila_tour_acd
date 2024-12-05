package com.example.vila_tour.repository;

import com.example.vila_tour.domain.Coordinate;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CoordinateRepository extends CrudRepository<Coordinate, Long> {
    Set<Coordinate> findAll();
}
