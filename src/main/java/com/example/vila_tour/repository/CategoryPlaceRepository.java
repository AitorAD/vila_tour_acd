package com.example.vila_tour.repository;

import com.example.vila_tour.domain.CategoryIngredient;
import com.example.vila_tour.domain.CategoryPlace;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryPlaceRepository extends CrudRepository<CategoryPlace, Long> {
    Set<CategoryPlace> findAll();
    Optional<CategoryPlace> findById(long id);

    @Query("SELECT c FROM category_place c WHERE c.name LIKE %:name%")
    Set<CategoryPlace> findByNameContaining(@Param("name") String name);
}
