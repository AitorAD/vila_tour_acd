package com.example.vila_tour.repository;

import com.example.vila_tour.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Set<Recipe> findAll();
}
