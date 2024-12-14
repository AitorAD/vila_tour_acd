package com.example.vila_tour.payload.response;

public class EmailResponse {
    private String message;
    private boolean success;
    private String newPassword;

    public EmailResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // Getters y Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
