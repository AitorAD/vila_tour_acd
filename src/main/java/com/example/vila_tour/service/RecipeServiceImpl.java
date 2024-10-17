package com.example.vila_tour.service;

import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.domain.Recipe;
import com.example.vila_tour.exception.RecipeNotFoundException;
import com.example.vila_tour.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public Set<Recipe> findAllRecipes() {
        return new HashSet<>(recipeRepository.findAll()); // Convertir la lista a un conjunto.
    }

    @Override
    public Optional<Recipe> findRecipeById(long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Set<Recipe> findRecipesByName(String name) {
        return recipeRepository.findByNameArticle(name);
    }

    @Override
    public Set<Recipe> findRecipesByDescription(String description) {
        return recipeRepository.findByDescriptionArticle(description);
    }

    @Override
    public Set<Recipe> findRecipesByAverageScore(double averageScore) {
        return recipeRepository.findByAverageScoreArticle(averageScore);
    }

    @Override
    public Set<Recipe> findRecipesByIngredient(Ingredient ingredient) {
        return recipeRepository.findByIngredients(ingredient);
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe modifyRecipe(long id, Recipe newRecipe) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));

        // Actualiza solo los campos que deseas cambiar
        recipe.setNameArticle(newRecipe.getNameArticle());
        recipe.setDescriptionArticle(newRecipe.getDescriptionArticle());
        recipe.setAverageScoreArticle(newRecipe.getAverageScoreArticle());

        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(long id) {
        recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        recipeRepository.deleteById(id);
    }
}
