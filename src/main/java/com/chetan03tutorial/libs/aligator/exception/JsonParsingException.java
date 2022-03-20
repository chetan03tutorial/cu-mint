package com.chetan03tutorial.libs.aligator.exception;


public class JsonParsingException extends AligatorBddException {
    private static final String PARSING_ERROR = "Error Parsing Json";

    public JsonParsingException(){
        super(PARSING_ERROR);
    }
    public JsonParsingException(String message, Throwable t) {
        super(message,t);
    }

    public JsonParsingException(Throwable th) {
        super(PARSING_ERROR, th);
    }

}
