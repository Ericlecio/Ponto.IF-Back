package br.edu.ifpe.pontoif.pontoif.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> body(String message, int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("status", status);
        map.put("error", message);
        return map;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        System.err.println("[400] " + ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(body(ex.getMessage(), 400));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleInvalidJson(HttpMessageNotReadableException ex) {
        System.err.println("[400] Malformed JSON: " + ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(body("Malformed JSON or invalid request body.", 400));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        System.err.println("[400] Validation error");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(e -> errors.put(e.getField(), e.getDefaultMessage())
        );
        Map<String, Object> response = body("Validation failed for one or more fields.", 400);
        response.put("details", errors);
        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrity(DataIntegrityViolationException ex) {
        System.err.println("[409] Data integrity violation: " + ex.getMessage());
        return ResponseEntity
                .status(409)
                .body(body("Database integrity violation.", 409));
    }

    @ExceptionHandler({EntityNotFoundException.class, JpaObjectRetrievalFailureException.class})
    public ResponseEntity<?> handleEntityNotFound(RuntimeException ex) {
        System.err.println("[404] Entity not found: " + ex.getMessage());
        return ResponseEntity
                .status(404)
                .body(body("Referenced entity not found.", 404));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleFound(NotFoundException ex) {
        System.err.println("[404] Internal server error: " + ex.getMessage());
        return ResponseEntity
                .status(404)
                .body(body(ex.getMessage(), 500));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        System.err.println("[500] Internal server error: " + ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity
                .status(500)
                .body(body("Internal server error.", 500));
    }
}