package com.example.vila_tour.repository;

import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.domain.Recipe;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.Set;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Set<Recipe> findAll();
    Set<Recipe> findByIdArticle(long id);
    Set<Recipe> findByName(String name);
    Set<Recipe> findByDescription(String description);
    Set<Recipe> findByAverageScore(double averageScore);
    Set<Recipe> findByIngredient(Ingredient ingredient);

}
