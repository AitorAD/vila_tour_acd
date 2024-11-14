package com.example.vila_tour.service;

import com.example.vila_tour.domain.CategoryIngredient;
import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.exception.IngredientAlreadyExistsException;
import com.example.vila_tour.exception.IngredientNotFoundException;
import com.example.vila_tour.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public Set<Ingredient> findAll() {
        return ingredientRepository.findAll(); // Asegúrate de que este método en el repositorio funcione correctamente
    }

    @Override
    public Optional<Ingredient> findIngredientById(long idIngredient) {
        return ingredientRepository.findIngredientByIdIngredient(idIngredient);
    }

    @Override
    public Set<Ingredient> findIngredientsByNameLike(String name) {
        return ingredientRepository.findIngredientsByNameLike(name);
    }

    @Override
    public Set<Ingredient> findIngredientsByCategory(CategoryIngredient category) {
        return ingredientRepository.findIngredientsByCategory(category);
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        // Check if the ingredient already exists by name
        if (ingredientRepository.findByName(ingredient.getName()) != null) {
            throw new IngredientAlreadyExistsException("An ingredient with this name already exists.");
        }
        return ingredientRepository.save(ingredient);
    }


    @Override
    public Ingredient modifyIngredient(long id, Ingredient newIngredient) { // Corrección aquí
        Ingredient ingredient = ingredientRepository.findIngredientByIdIngredient(id)
                .orElseThrow(() -> new IngredientNotFoundException(id));
        newIngredient.setIdIngredient(ingredient.getIdIngredient());
        return ingredientRepository.save(newIngredient);
    }

    @Override
    public void deleteIngredient(long idIngredient) {
        ingredientRepository.findIngredientByIdIngredient(idIngredient)
                .orElseThrow(() -> new IngredientNotFoundException(idIngredient));
        ingredientRepository.deleteById(idIngredient);
    }
}
