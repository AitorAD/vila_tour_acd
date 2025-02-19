package com.example.vila_tour.service;

import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.domain.Place;
import com.example.vila_tour.domain.Recipe;
import com.example.vila_tour.exception.PlaceNotFoundException;
import com.example.vila_tour.exception.RecipeNotFoundException;
import com.example.vila_tour.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.midi.Receiver;
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
    public Set<Recipe> findRecipesByDescription(String description) {
        return recipeRepository.findByDescription(description);
    }

    @Override
    public Set<Recipe> findAllByRecent(boolean recent){
        return recipeRepository.findAllByRecent(recent);
    }

    @Override
    public Set<Recipe> findRecipesByAverageScore(double averageScore) {
        return recipeRepository.findByAverageScore(averageScore);
    }

    @Override
    public Set<Recipe> findRecipesByIngredient(Ingredient ingredient) {
        return recipeRepository.findByIngredients(ingredient);
    }


    @Override
    public Set<Recipe> findAllByOrderByName() {
        return recipeRepository.findAllByOrderByName();
    }
    @Override
    public Set<Recipe> findAllByOrderByNameDesc() {
        return recipeRepository.findAllByOrderByNameDesc();
    }
    @Override
    public Set<Recipe> findByNameAndAverageScore(String name, double averageScore) {
        return recipeRepository.findByNameAndAverageScore(name, averageScore);
    }

    @Override
    public Set<Recipe> findByNameContaining(String name) {
        return recipeRepository.findByNameArticleContaining(name);
    }

    @Override
    public Set<Recipe> findRecipesByCreatorId(long creatorId) {
        return recipeRepository.findRecipesByCreatorId(creatorId);
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe modifyRecipe(long id, Recipe newRecipe) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(()-> new RecipeNotFoundException(id));
        newRecipe.setId(recipe.getId());
        return recipeRepository.save(newRecipe);
    }

    @Override
    public void deleteRecipe(long id) {
        recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        recipeRepository.deleteById(id);
    }
}
