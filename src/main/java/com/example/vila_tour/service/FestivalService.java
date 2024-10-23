package com.example.vila_tour.service;

import com.example.vila_tour.domain.Festival;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface FestivalService {
    Set<Festival> findAllFestivals();
    Optional<Festival> findFestivalById(long id);
    Set<Festival> findFestivalsByDescription(String description);
    Set<Festival> findFestivalsByAverageScore(double averageScore);
    Set<Festival> findFestivalsByStartDate(LocalDate startDate);
    Set<Festival> findAllByOrderByName();  // Ascendente
    Set<Festival> findAllByOrderByNameDesc(); // Descendente
    Set<Festival> findByNameAndAverageScore(String name, double averageScore);
    Set<Festival> findByNameContaining(String name);
    Festival addFestival(Festival festival);
    Festival modifyFestival(long id, Festival newFestival);
    void deleteFestival(long id);
}
