package com.example.vila_tour.service;

import com.example.vila_tour.domain.Festival;
import com.example.vila_tour.exception.FestivalNotFoundException;
import com.example.vila_tour.exception.RecipeNotFoundException;
import com.example.vila_tour.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class FestivalServiceImpl implements FestivalService {

    @Autowired
    private FestivalRepository festivalRepository;

    @Override
    public Set<Festival> findAll() {
        return festivalRepository.findAll();
    }

    @Override
    public Optional<Festival> findById(long idFestival) {
        return festivalRepository.findById(idFestival);
    }

    @Override
    public Festival addFestival(Festival festival) {
        return festivalRepository.save(festival);
    }

    @Override
    public Festival modifyFestival(long id, Festival newFestival) {
        if (!festivalRepository.existsById(id)) {
            throw new FestivalNotFoundException(id);
        }

        // Obtener el festival existente
        Festival existingFestival = festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalNotFoundException(id)); // Manejo de excepciones

        newFestival.setIdArticle(existingFestival.getIdArticle()); // Preservar el ID del artículo
        return festivalRepository.save(newFestival); // Guardar el festival modificado
    }

    @Override
    public void deleteFestival(long id) {
        festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalNotFoundException(id));
        festivalRepository.deleteById(id);
    }
}
