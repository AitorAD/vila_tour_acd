package com.example.vila_tour.service;

import com.example.vila_tour.domain.Coordinate;
import com.example.vila_tour.exception.CoordinateNotFoundException;
import com.example.vila_tour.repository.CoordinateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CoordinateServiceImpl implements CoordinateService {

    @Autowired
    private CoordinateRepository coordinateRepository;

    @Override
    public Set<Coordinate> findAll() {
        return new HashSet<>(coordinateRepository.findAll());
    }

    @Override
    public Optional<Coordinate> findById(long id) {
        return coordinateRepository.findById(id);
    }

    @Override
    public Coordinate addCoordinate(Coordinate coordinate) {
        return coordinateRepository.save(coordinate);
    }

    @Override
    public Coordinate modifyCoordinate(long id, Coordinate newCoordinate) {
        Coordinate coordinate = coordinateRepository.findById(id)
                .orElseThrow(() -> new CoordinateNotFoundException(id));

        newCoordinate.setId(coordinate.getId());
        return coordinateRepository.save(newCoordinate);
    }

    @Override
    public void deleteCoordinate(long id) {
        coordinateRepository.findById(id)
                .orElseThrow(() -> new CoordinateNotFoundException(id));
        coordinateRepository.deleteById(id);
    }
}
