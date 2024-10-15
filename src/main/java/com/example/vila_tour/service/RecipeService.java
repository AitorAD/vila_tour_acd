package com.example.vila_tour.service;

import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface RecipeService {
    Set<Recipe> findAll();
    Optional<Recipe> findByIdArticle(long id);
    Set<Recipe> findByName(String name);
    Set<Recipe> findByDescription(String description);
    Set<Recipe> findByAverageScore(double averageScore);
    Set<Recipe> findByIngredient(Ingredient ingredient);

    Recipe addRecipe(Recipe recipe);
    Recipe modifyRecipe(long id, Recipe newRecipe);
    void deleteRecipe(long id);
}
