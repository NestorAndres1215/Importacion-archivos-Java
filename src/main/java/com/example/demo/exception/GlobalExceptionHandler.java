package com.example.demo.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleFileNotFound(FileNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(FileTypeNotAllowedException.class)
    public ResponseEntity<String> handleFileType(FileTypeNotAllowedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(FileEmptyException.class)
    public ResponseEntity<String> handleEmptyFile(FileEmptyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Cualquier otra excepción
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno en el servidor");
    }
}