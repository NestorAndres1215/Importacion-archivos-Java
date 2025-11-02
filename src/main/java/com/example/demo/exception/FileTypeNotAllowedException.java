package com.example.demo.exception;

public class FileTypeNotAllowedException extends RuntimeException {
    public FileTypeNotAllowedException(String message) {
        super(message);
    }
}
