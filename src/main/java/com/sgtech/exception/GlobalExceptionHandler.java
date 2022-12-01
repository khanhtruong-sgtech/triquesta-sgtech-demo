package com.sgtech.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(RuntimeException ex) {
        return error(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorDetails> handleBadRequestException(RuntimeException ex) {
        return error(HttpStatus.BAD_GATEWAY, ex);
    }

    @ExceptionHandler({ServerErrorException.class})
    public ResponseEntity<ErrorDetails> handleServerErrorException(RuntimeException ex) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(BindException ex) {
        return error(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex) {
        log.error("aaa" + ex.getMessage(), ex);
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "This service is temporarily unavailable. Please try again later!");
    }

    private ResponseEntity<ErrorDetails> error(HttpStatus status, Exception ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage());
        return ResponseEntity.status(status).body(errorDetails);
    }

    private ResponseEntity<ErrorDetails> error(HttpStatus status, String message) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message);
        return ResponseEntity.status(status).body(errorDetails);
    }

}
