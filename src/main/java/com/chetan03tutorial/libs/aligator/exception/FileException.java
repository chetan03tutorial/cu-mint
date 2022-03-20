package com.chetan03tutorial.libs.aligator.exception;

public class FileException extends AligatorBddException {
    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Exception e) {
        super(message,e);
    }
}
