package com.example.vila_tour.repository;

import com.example.vila_tour.domain.CategoryIngredient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryIngredientRepository extends CrudRepository<CategoryIngredient, Long> {
    Set<CategoryIngredient> findAll();
    Optional<CategoryIngredient> findById(long id);

    @Query("SELECT c FROM category_ingredient c WHERE c.name LIKE %:name%")
    Set<CategoryIngredient> findByNameContaining(@Param("name") String name);
}
