package com.example.vila_tour.service;

import com.example.vila_tour.domain.Place;
import com.example.vila_tour.domain.Route;
import com.example.vila_tour.exception.RouteNotFoundException;
import com.example.vila_tour.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class RouteServiceImpl implements RouteService{

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    @Override
    public Optional<Route> findById(long id) {
        return routeRepository.findById(id);
    }


    @Override
    public List<Route> findByPlacesIn(List<Place> places) {
        return routeRepository.findByPlacesIn(places);
    }

    @Override
    public List<Route> findByNameContaining(String name) {
        return routeRepository.findByNameContaining(name);
    }

    @Override
    public Route addRoute(Route route) {
        return routeRepository.save(route);
    }

    @Override
    public Route modifyRoute(long id, Route newRoute) {
        Route route = routeRepository.findById(id)
                .orElseThrow(()-> new RouteNotFoundException(id));
        newRoute.setId(route.getId());
        return routeRepository.save(newRoute);
    }

    @Override
    public void deleteRoute(long id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(()-> new RouteNotFoundException(id));
        routeRepository.deleteById(id);
    }
}
