package nl.zoe.account.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> fields = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName;
            try {
                fieldName = ((FieldError) error).getField();
            } catch (ClassCastException cce) {
                fieldName = error.getObjectName();
            }
            String message = error.getDefaultMessage();
            fields.add(String.format("\"%s\":\"%s\"", fieldName, message));
        });
        StringBuilder errors = new StringBuilder("{").append(String.join(",", fields)).append("}");
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
