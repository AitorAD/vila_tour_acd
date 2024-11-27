package com.example.vila_tour.service;

import com.example.vila_tour.domain.CategoryPlace;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface CategoryPlaceService {
    Set<CategoryPlace> findAll();
    Optional<CategoryPlace> findById(long id);
    Set<CategoryPlace> findByNameContaining(String name);

    CategoryPlace addCategoryPlace(CategoryPlace categoryPlace);
    CategoryPlace modifyCategoryPlace(long id, CategoryPlace newCategoryPlace);
    void deleteCategoryPlace(long id);
}
