package com.mcarchieve.mcarchieve.exception;


public record ErrorResponse(String message) {

    public ErrorResponse(CustomException e) {
        this(e.getMessage());
    }
}
