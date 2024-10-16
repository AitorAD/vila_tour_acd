package com.example.vila_tour.repository;

import com.example.vila_tour.domain.Festival;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface FestivalRepository extends CrudRepository<Festival, Long> {
    Set<Festival> findAll();
}
