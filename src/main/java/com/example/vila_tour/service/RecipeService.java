package com.example.vila_tour.service;

import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.domain.Recipe;

import java.util.Optional;
import java.util.Set;

public interface RecipeService {
    Set<Recipe> findAllRecipes(); // Encuentra todas las recetas.
    Optional<Recipe> findRecipeById(long id); // Encuentra una receta por su ID.
    Set<Recipe> findRecipesByName(String name); // Encuentra recetas por nombre.
    Set<Recipe> findRecipesByDescription(String description); // Encuentra recetas por descripción.
    Set<Recipe> findRecipesByAverageScore(double averageScore); // Encuentra recetas por puntuación promedio.
    Set<Recipe> findRecipesByIngredient(Ingredient ingredient); // Encuentra recetas por ingrediente.

    Recipe addRecipe(Recipe recipe); // Agrega una nueva receta.
    Recipe modifyRecipe(long id, Recipe newRecipe); // Modifica una receta existente.
    void deleteRecipe(long id); // Elimina una receta por su ID.
}
