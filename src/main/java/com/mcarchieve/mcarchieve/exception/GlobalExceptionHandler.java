package com.mcarchieve.mcarchieve.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        log.error("CustomException: {}", ex.getErrorCode(), ex);
        ErrorResponse errorResponse = new ErrorResponse(ex);

        return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(errorResponse);
    }

    @Nullable
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        log.error("Spring Exception: ", ex);
        // TODO: 스프링 오류 일괄 처리하지 않고, 각각의 오류에 대한 처리를 추가해야 함
        ErrorResponse errorResponse = new ErrorResponse(
                "SPRING_EXCEPTION", "스프링 오류가 발생했습니다.", statusCode.value()
        );
        return ResponseEntity.status(statusCode).body(errorResponse);

    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Unhandled exception occurred: ", ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "INTERNAL_SERVER_ERROR", "예상하지 못한 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
