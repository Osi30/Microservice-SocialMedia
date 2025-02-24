package com.nam.message_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(MessageException.class)
    public ResponseEntity<MessageResponse> handleMessageException(MessageException e, WebRequest request) {
        MessageResponse response = new MessageResponse();
        response.setMessage(e.getMessage());
        response.setError(request.getDescription(false));
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
