package com.example.vila_tour.exception;

import com.example.vila_tour.domain.Route;

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException() {
        super();
    }

    public RouteNotFoundException(String message) {
        super(message);
    }

    public RouteNotFoundException(long id) {
        super("Review not found: " + id);
    }
}
