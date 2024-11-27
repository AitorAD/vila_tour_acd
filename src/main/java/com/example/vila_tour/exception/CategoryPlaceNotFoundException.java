package com.example.vila_tour.exception;

public class CategoryPlaceNotFoundException extends RuntimeException {

    public CategoryPlaceNotFoundException() {
        super();
    }

    public CategoryPlaceNotFoundException(String message) {
        super(message);
    }

    public CategoryPlaceNotFoundException(long id) {
        super("Category of ingredient not found: " + id);
    }
}