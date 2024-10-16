package com.example.vila_tour.exception;

public class FestivalNotFoundException extends RuntimeException{
    public FestivalNotFoundException() {
        super();
    }

    public FestivalNotFoundException(String message) {
        super(message);
    }

    public FestivalNotFoundException(long id) {
        super("Festival or tradition not found: " + id);
    }
}
