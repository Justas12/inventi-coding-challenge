package com.example.demo.helper.handler.exception;

import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.ErrorView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<ErrorView> handleEntityNotFound(EntityNotFoundException exception) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorView.builder()
                .message(exception.getMessage())
                .build()
            );
    }

    @ExceptionHandler(value = Throwable.class)
    protected ResponseEntity<ErrorView> handleAny(Throwable throwable) {
        String uuid = UUID.randomUUID().toString();
        log.error(uuid, throwable);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorView.builder()
                .message(String.format("%s (%s)", throwable.getMessage(), uuid))
                .build()
            );
    }

}
