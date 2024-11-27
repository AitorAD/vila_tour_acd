package com.example.vila_tour.service;

import com.example.vila_tour.domain.Festival;
import com.example.vila_tour.domain.Place;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public interface PlaceService {
    Set<Place> findAllPlaces();
    Optional<Place> findPlaceById(long id);
    Set<Place> findPlacesByDescription(String description);
    Set<Place> findPlacesByAverageScore(double averageScore);
    Set<Place> findAllByOrderByName();  // Ascendente
    Set<Place> findAllByOrderByNameDesc(); // Descendente
    Set<Place> findByNameAndAverageScore(String name, double averageScore);
    Set<Place> findByNameContaining(String name);
    Place addPlace(Place place);
    Place modifyPlace(long id, Place newPlace);
    void deletePlace(long id);
}
