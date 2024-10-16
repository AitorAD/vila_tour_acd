package com.example.vila_tour.repository;

import com.example.vila_tour.domain.Article;
import com.example.vila_tour.domain.CategoryIngredient;
import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.domain.Recipe;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    Set<Ingredient> findAll();
    Optional<Ingredient> findIngredientById(long idArticle);
    Set<Ingredient> findIngredienteByName(String name);
    Set<Ingredient> findIngredientsByCategory(CategoryIngredient category);
}

