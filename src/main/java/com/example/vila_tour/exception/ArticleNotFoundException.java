package com.example.vila_tour.exception;

public class ArticleNotFoundException extends RuntimeException{
    public ArticleNotFoundException() {
        super();
    }

    public ArticleNotFoundException(String message) {
        super(message);
    }

    public ArticleNotFoundException(long id) {
        super("Article not found: " + id);
    }
}
