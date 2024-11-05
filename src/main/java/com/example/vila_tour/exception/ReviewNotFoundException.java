package com.example.vila_tour.exception;

import com.example.vila_tour.domain.ReviewId;

public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException() {
        super();
    }

    public ReviewNotFoundException(String message) {
        super(message);
    }

    public ReviewNotFoundException(ReviewId id) {
        super("Review not found: " + id);
    }
}
