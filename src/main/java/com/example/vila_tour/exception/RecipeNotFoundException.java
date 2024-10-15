package com.example.vila_tour.exception;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException() {
        super();
    }

    public RecipeNotFoundException(String message) {
        super(message);
    }

    public RecipeNotFoundException(long id) {
        super("Recipe not found: " + id);
    }
}