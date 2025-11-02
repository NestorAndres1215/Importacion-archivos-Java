package com.example.demo.exception;

public class FileEmptyException extends RuntimeException {
  public FileEmptyException(String message) {
    super(message);
  }
}
