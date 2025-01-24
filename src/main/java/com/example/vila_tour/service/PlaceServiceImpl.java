package com.example.vila_tour.service;

import com.example.vila_tour.domain.Place;
import com.example.vila_tour.exception.PlaceNotFoundException;
import com.example.vila_tour.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PlaceServiceImpl implements PlaceService{

    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public Set<Place> findAllPlaces(){return  new HashSet<>(placeRepository.findAll());}

    @Override
    public Optional<Place> findPlaceById(long id){return placeRepository.findById(id);}

    @Override
    public Set<Place> findPlacesByDescription(String description){
        return placeRepository.findByDescription(description);
    }

    @Override
    public Set<Place> findPlacesByAverageScore(double averageScore){
        return placeRepository.findByAverageScore(averageScore);
    }

    @Override
    public Set<Place> findAllByOrderByName(){
        return placeRepository.findAllByOrderByName();
    }

    @Override
    public Set<Place> findAllByOrderByNameDesc() {
        return placeRepository.findAllByOrderByNameDesc();
    }

    @Override
    public Set<Place> findByNameAndAverageScore(String name, double averageScore){
        return placeRepository.findByNameAndAverageScore(name, averageScore);
    }

    @Override
    public Set<Place> findByNameContaining(String name){
        return placeRepository.findByNameArticleContaining(name);
    }

    @Override
    public Place addPlace(Place place){
        return placeRepository.save(place);
    }

    @Override
    public Place modifyPlace(long id, Place newPlace){
        Place place = placeRepository.findById(id)
                .orElseThrow(()-> new PlaceNotFoundException(id));
        newPlace.setId(place.getId());
        return placeRepository.save(newPlace);
    }

    @Override
    public void deletePlace(long id){
        placeRepository.findById(id)
                .orElseThrow(()-> new PlaceNotFoundException(id));
        placeRepository.deleteById(id);
    }
}

