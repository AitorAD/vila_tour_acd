package com.example.vila_tour.exception;

public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException() {
        super();
    }

    public IngredientNotFoundException(String message) {
        super(message);
    }

    public IngredientNotFoundException(long id) {
        super("Recipe not found: " + id);
    }
}