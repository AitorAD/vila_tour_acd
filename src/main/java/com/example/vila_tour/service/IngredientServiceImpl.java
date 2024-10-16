package com.example.vila_tour.service;

import com.example.vila_tour.domain.CategoryIngredient;
import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.domain.Recipe;
import com.example.vila_tour.exception.IngredientNotFoundException;
import com.example.vila_tour.exception.RecipeNotFoundException;
import com.example.vila_tour.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService{

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public Set<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public Optional<Ingredient> findIngredientById(long idIngredient) {
        return ingredientRepository.findIngredientById(idIngredient);
    }

    @Override
    public Set<Ingredient> findIngredientByName(String name) {
        return ingredientRepository.findIngredienteByName(name);
    }

    @Override
    public Set<Ingredient> findIngredientsByCategory(CategoryIngredient category) {
        return ingredientRepository.findIngredientsByCategory(category);
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public Ingredient modifyIngrediet(long id, Ingredient newIngredient) {
        Ingredient ingredient = ingredientRepository.findIngredientById(id)
                .orElseThrow(() -> new IngredientNotFoundException(id));
        newIngredient.setIdIngredient(ingredient.getIdIngredient());
        return ingredientRepository.save(newIngredient);
    }

    @Override
    public void deleteIngredient(long id) {

    }
}
