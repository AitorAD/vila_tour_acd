package com.example.vila_tour.exception;

public class PlaceNotFoundException extends RuntimeException {
    public PlaceNotFoundException() {
        super();
    }

    public PlaceNotFoundException(String message) {
        super(message);
    }

    public PlaceNotFoundException(long id) {
        super("Recipe not found: " + id);
    }
}