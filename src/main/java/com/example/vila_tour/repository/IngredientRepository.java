package com.example.vila_tour.repository;

import com.example.vila_tour.domain.CategoryIngredient;
import com.example.vila_tour.domain.Ingredient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    Set<Ingredient> findAll();
    Optional<Ingredient> findIngredientByIdIngredient(long idIngredient);
    Set<Ingredient> findIngredientsByCategory(CategoryIngredient category);
    Ingredient findByName(String name);

    @Query(value = "SELECT * FROM ingredients WHERE name LIKE %:nameIngredient%", nativeQuery = true)
    Set<Ingredient> findIngredientsByNameLike(@Param("nameIngredient") String nameIngredient);

}

