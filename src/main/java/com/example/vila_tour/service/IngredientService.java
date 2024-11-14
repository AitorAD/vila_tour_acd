package com.example.vila_tour.service;

import com.example.vila_tour.domain.CategoryIngredient;
import com.example.vila_tour.domain.Ingredient;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface IngredientService {
    Set<Ingredient> findAll();
    Optional<Ingredient> findIngredientById(long idIngredient);
    Set<Ingredient> findIngredientsByNameLike(String name);

    Set<Ingredient> findIngredientsByCategoryId(long idCategoryIngredient);

    Ingredient addIngredient(Ingredient ingredient);
    Ingredient modifyIngredient(long id, Ingredient newIngredient); // Corrección aquí
    void deleteIngredient(long id);
}
