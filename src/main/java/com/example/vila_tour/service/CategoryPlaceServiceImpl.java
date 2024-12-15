package com.example.vila_tour.service;

import com.example.vila_tour.domain.CategoryPlace;
import com.example.vila_tour.domain.Place;
import com.example.vila_tour.exception.CategoryPlaceAlreadyExist;
import com.example.vila_tour.exception.CategoryPlaceNotFoundException;
import com.example.vila_tour.repository.CategoryPlaceRepository;
import com.example.vila_tour.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CategoryPlaceServiceImpl implements CategoryPlaceService {

    @Autowired
    private CategoryPlaceRepository categoryPlaceRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public Set<CategoryPlace> findAll() {
        return categoryPlaceRepository.findAll();
    }

    @Override
    public Optional<CategoryPlace> findById(long id) {
        return categoryPlaceRepository.findById(id);
    }

    @Override
    public Set<CategoryPlace> findByNameContaining(String name) {
        return categoryPlaceRepository.findByNameContaining(name);
    }

    @Override
    public CategoryPlace addCategoryPlace(CategoryPlace categoryPlace) {
        for (CategoryPlace existingCategory : categoryPlaceRepository.findAll()) {
            if (existingCategory.getName().equalsIgnoreCase(categoryPlace.getName())) {
                throw new CategoryPlaceAlreadyExist("La categoría ya existe.");
            }
        }
        return categoryPlaceRepository.save(categoryPlace);
    }

    @Override
    public CategoryPlace modifyCategoryPlace(long id, CategoryPlace newCategoryPlace) {
        CategoryPlace categoryPlace= categoryPlaceRepository.findById(id)
                .orElseThrow(() -> new CategoryPlaceNotFoundException(id));

        newCategoryPlace.setId(categoryPlace.getId());
        return categoryPlaceRepository.save(newCategoryPlace);
    }

    @Override
    public void deleteCategoryPlace(long id) {
        // Desasociar los places
        Set<Place> places = placeRepository.findPlacesByCategoryPlaceId(id);
        for (Place place: places) {
           place.setCategoryPlace(null);
        }
        placeRepository.saveAll(places);

        // Eliminar la categoría
        categoryPlaceRepository.findById(id)
                .orElseThrow(() -> new CategoryPlaceNotFoundException(id));
        categoryPlaceRepository.deleteById(id);
    }
}
