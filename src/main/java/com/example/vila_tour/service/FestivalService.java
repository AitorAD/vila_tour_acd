package com.example.vila_tour.service;

import com.example.vila_tour.domain.Festival;
import com.example.vila_tour.domain.User;

import java.util.Optional;
import java.util.Set;

public interface FestivalService {

    Set<Festival> findAll();
    Optional<Festival> findById(long idFestival);
    Festival addFestival(Festival festival);
    Festival modifyFestival(long id, Festival newFestival);
    void deleteFestival(long id);
}
