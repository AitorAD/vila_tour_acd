package com.example.vila_tour.repository;

import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Set<Recipe> findByIdArticle(long id); // Este método se basará en el campo idArticle, que ahora debe ser parte de Article.

    Set<Recipe> findByNameArticle(String nameArticle); // Busca recetas por nombre.

    Set<Recipe> findByDescriptionArticle(String descriptionArticle); // Busca recetas por descripción.

    Set<Recipe> findByAverageScoreArticle(double averageScoreArticle); // Busca recetas por puntuación promedio.

    Set<Recipe> findByIngredients(Ingredient ingredient); // Busca recetas que contengan un ingrediente específico.
}
