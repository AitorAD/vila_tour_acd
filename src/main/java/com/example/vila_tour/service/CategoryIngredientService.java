package com.example.vila_tour.service;

import com.example.vila_tour.domain.CategoryIngredient;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface CategoryIngredientService {
    Set<CategoryIngredient> findAll();
    Optional<CategoryIngredient> findById(long id);
    Set<CategoryIngredient> findByNameContaining(String name);

    CategoryIngredient addCategoryIngredient(CategoryIngredient categoryIngredient);
    CategoryIngredient modifyCategoryIngredient(long id, CategoryIngredient newCategoryIngredient);
    void deleteCategoryIngredient(long id);
}
