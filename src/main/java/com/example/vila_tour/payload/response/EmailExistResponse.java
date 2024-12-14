package com.example.vila_tour.payload.response;

// Clase interna para la respuesta JSON
public class EmailExistResponse {
    private boolean exists;

    public EmailExistResponse(boolean exists) {
        this.exists = exists;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
}