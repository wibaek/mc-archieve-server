package com.mcarchieve.mcarchieve.exception;


public record ErrorResponse(
        String code,
        String message,
        int status
) {

    public ErrorResponse(CustomException e) {
        this(e.getErrorCode().name(), e.getMessage(), e.getErrorCode().getHttpStatus().value());
    }
}
