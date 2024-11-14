package com.example.vila_tour.service;

import com.example.vila_tour.domain.CategoryIngredient;
import com.example.vila_tour.exception.CategoryIngredientNotFoundException;
import com.example.vila_tour.repository.CategoryIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CategoryIngredientServiceImpl implements CategoryIngredientService {

    @Autowired
    private CategoryIngredientRepository categoryIngredientRepository;

    @Override
    public Set<CategoryIngredient> findAll() {
        return categoryIngredientRepository.findAll();
    }

    @Override
    public Optional<CategoryIngredient> findById(long id) {
        return categoryIngredientRepository.findById(id);
    }

    @Override
    public Set<CategoryIngredient> findByNameContaining(String name) {
        return categoryIngredientRepository.findByNameContaining(name);
    }

    @Override
    public CategoryIngredient addCategoryIngredient(CategoryIngredient categoryIngredient) {
        return categoryIngredientRepository.save(categoryIngredient);
    }

    @Override
    public CategoryIngredient modifyCategoryIngredient(long id, CategoryIngredient newCategoryIngredient) {
        CategoryIngredient categoryIngredient = categoryIngredientRepository.findById(id)
                .orElseThrow(() -> new CategoryIngredientNotFoundException(id));

        newCategoryIngredient.setId(categoryIngredient.getId());
        return categoryIngredientRepository.save(newCategoryIngredient);
    }

    @Override
    public void deleteCategoryIngredient(long id) {
        categoryIngredientRepository.findById(id)
                .orElseThrow(() -> new CategoryIngredientNotFoundException(id));
        categoryIngredientRepository.deleteById(id);
    }
}
