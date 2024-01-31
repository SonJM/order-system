package com.practice.ordersystem.domain.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerClass {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> entityNotFoundHandler(EntityNotFoundException e){
        log.error("EntityNotFoundException message : " + e.getMessage());
        return this.errResponseMessage(HttpStatus.NOT_FOUND, e.getMessage());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> IllegalArgHandler(IllegalArgumentException e){
        log.error("EntityNotFoundException message : " + e.getMessage());
        return this.errResponseMessage(HttpStatus.BAD_REQUEST, e.getMessage());
    }
    private ResponseEntity<Map<String, Object>> errResponseMessage(HttpStatus httpStatus, String s){
        Map<String, Object> body = new HashMap<>();
        body.put("status", Integer.toString(httpStatus.value()));
        body.put("status message", s);
        body.put("error message", httpStatus.getReasonPhrase());
        return new ResponseEntity<>(body, httpStatus);
    }
}
