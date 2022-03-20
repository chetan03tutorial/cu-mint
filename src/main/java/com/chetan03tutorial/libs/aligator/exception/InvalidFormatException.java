package com.chetan03tutorial.libs.aligator.exception;

public class InvalidFormatException extends AligatorBddException {
    public InvalidFormatException(String message) {
        super(message);
    }

    public InvalidFormatException(String message, Exception e) {
        super(message,e);
    }
}
