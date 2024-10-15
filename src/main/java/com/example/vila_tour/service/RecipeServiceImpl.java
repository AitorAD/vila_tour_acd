package com.example.vila_tour.service;

import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.domain.Recipe;
import com.example.vila_tour.exception.RecipeNotFoundException;
import com.example.vila_tour.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public Set<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Optional<Recipe> findByIdArticle(long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Set<Recipe> findByName(String name) {
        return recipeRepository.findByName(name);
    }

    @Override
    public Set<Recipe> findByDescription(String description) {
        return recipeRepository.findByDescription(description);
    }

    @Override
    public Set<Recipe> findByAverageScore(double averageScore) {
        return recipeRepository.findByAverageScore(averageScore);
    }

    @Override
    public Set<Recipe> findByIngredient(Ingredient ingredient) {
        return recipeRepository.findByIngredient(ingredient);
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe modifyRecipe(long id, Recipe newRecipe) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        newRecipe.setIdArticle(recipe.getIdArticle());
        return recipeRepository.save(newRecipe);
    }

    @Override
    public void deleteRecipe(long id) {
        recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        recipeRepository.deleteById(id);
    }
}
