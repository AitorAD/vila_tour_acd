package com.example.vila_tour.repository;

import com.example.vila_tour.domain.CategoryPlace;
import com.example.vila_tour.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findById(long id);

    Place findByName(String name);

    Set<Place> findByDescription(String description);

    Set<Place> findByAverageScore(double averageScore);

    Set<Place> findPlacesByCategoryPlaceId(long categoryId);

    Set<Place> findByCategoryPlace(CategoryPlace categoryPlace);

    Set<Place> findAllByOrderByName();  // Ascendente

    Set<Place> findAllByOrderByNameDesc(); // Descendente

    // Buscar por nombre y puntuación
    Set<Place> findByNameAndAverageScore(String name, double averageScore);

    // Buscar por que contenga el string en el nombre
    @Query("SELECT r FROM places r WHERE r.name LIKE %:nameArticle%")
    Set<Place> findByNameArticleContaining(@Param("nameArticle") String nameArticle);

}
