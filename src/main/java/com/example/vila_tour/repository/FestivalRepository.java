package com.example.vila_tour.repository;

import com.example.vila_tour.domain.Festival;
import com.example.vila_tour.domain.Ingredient;
import com.example.vila_tour.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FestivalRepository extends CrudRepository<Festival, Long> {

    Optional<Festival> findById(long id);

    Set<Festival> findByDescription(String description);

    Set<Festival> findByAverageScore(double averageScore);

    Set<Festival> findByStartDate(LocalDate startDate);

    Set<Festival> findAllByOrderByName();  // Ascendente

    Set<Festival> findAllByOrderByNameDesc(); // Descendente

    Set<Festival> findByNameAndAverageScore(String name, double averageScore);

    // Buscar por que contenga el string en el nombre
    @Query("SELECT f FROM festivals f WHERE f.name LIKE %:nameArticle%")
    Set<Festival> findByNameArticleContaining(@Param("nameArticle") String nameArticle);
}
