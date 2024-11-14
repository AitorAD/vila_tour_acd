package com.example.vila_tour.exception;

public class CategoryIngredientNotFoundException extends RuntimeException {

    public CategoryIngredientNotFoundException() {
        super();
    }

    public CategoryIngredientNotFoundException(String message) {
        super(message);
    }

    public CategoryIngredientNotFoundException(long id) {
        super("Category of ingredient not found: " + id);
    }
}