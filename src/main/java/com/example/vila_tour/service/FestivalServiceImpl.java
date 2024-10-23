package com.example.vila_tour.service;

import com.example.vila_tour.domain.Festival;
import com.example.vila_tour.exception.FestivalNotFoundException;
import com.example.vila_tour.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class FestivalServiceImpl implements FestivalService {

    @Autowired
    private FestivalRepository festivalRepository;

    @Override
    public Set<Festival> findAllFestivals() {
        return new HashSet<>(festivalRepository.findAll());
    }

    @Override
    public Optional<Festival> findFestivalById(long id) {
        return festivalRepository.findById(id);
    }

    @Override
    public Set<Festival> findFestivalsByDescription(String description) {
        return festivalRepository.findByDescription(description);
    }

    @Override
    public Set<Festival> findFestivalsByAverageScore(double averageScore) {
        return festivalRepository.findByAverageScore(averageScore);
    }

    @Override
    public Set<Festival> findFestivalsByStartDate(LocalDate startDate) {
        return festivalRepository.findByStartDate(startDate);
    }

    @Override
    public Set<Festival> findAllByOrderByName() {
        return festivalRepository.findAllByOrderByName();
    }

    @Override
    public Set<Festival> findAllByOrderByNameDesc() {
        return festivalRepository.findAllByOrderByNameDesc();
    }

    @Override
    public Set<Festival> findByNameAndAverageScore(String name, double averageScore) {
        return festivalRepository.findByNameAndAverageScore(name, averageScore);
    }

    @Override
    public Set<Festival> findByNameContaining(String name) {
        return festivalRepository.findByNameArticleContaining(name);
    }

    @Override
    public Festival addFestival(Festival festival) {
        return festivalRepository.save(festival);
    }

    @Override
    public Festival modifyFestival(long id, Festival newFestival) {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalNotFoundException(id));

        newFestival.setId(festival.getId());
        return festivalRepository.save(newFestival);
    }

    @Override
    public void deleteFestival(long id) {
        festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalNotFoundException(id));
        festivalRepository.deleteById(id);
    }
}
