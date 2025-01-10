package com.example.vila_tour.repository;

import com.example.vila_tour.domain.Place;
import com.example.vila_tour.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findAll();
    Optional<Route> findById(long id);

    List<Route> findByPlacesIn(List<Place> places);

    List<Route> findByNameContaining(String name);
}
