package com.example.vila_tour.exception;

public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException() {
        super();
    }

    public ImageNotFoundException(String message) {
        super(message);
    }

    public ImageNotFoundException(long id) {
        super("Image not found: " + id);
    }
}
