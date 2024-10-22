package com.example.vila_tour.repository;

import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findById(long id);

    Set<Recipe> findByName(String name);

    Set<Recipe> findByDescription(String description);

    Set<Recipe> findByAverageScore(double averageScore);

    Set<Recipe> findByIngredient(Ingredient ingredient);

    Set<Recipe> findAllByOrderByName();  // Ascendente

    Set<Recipe> findAllByOrderByNameDesc(); // Descendente

    // Buscar por nombre y puntuación
    Set<Recipe> findByNameAndAverageScore(String name, double averageScore);

    // Buscar por que contenga el string en el nombre
    @Query("SELECT r FROM recipes r WHERE r.name LIKE %:nameArticle%")
    Set<Recipe> findByNameArticleContaining(@Param("nameArticle") String nameArticle);

    @Query("SELECT r FROM Recipe r JOIN r.ingredients i WHERE i IN :ingredients GROUP BY r.id HAVING COUNT(i) = :ingredientCount")
    Set<Recipe> findByIngredientsIn(@Param("ingredients") Set<Ingredient> ingredients, @Param("ingredientCount") long ingredientCount);

}
