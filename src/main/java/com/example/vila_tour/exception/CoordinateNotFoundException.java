package com.example.vila_tour.exception;

public class CoordinateNotFoundException extends RuntimeException {

    public CoordinateNotFoundException() {
        super();
    }

    public CoordinateNotFoundException(String message) {
        super(message);
    }

    public CoordinateNotFoundException(long id) {
        super("Coordinate not found: " + id);
    }
}
