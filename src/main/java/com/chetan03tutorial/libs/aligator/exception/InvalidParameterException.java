package com.chetan03tutorial.libs.aligator.exception;

public class InvalidParameterException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidParameterException(String errorMsg) {
		super(errorMsg);
	}

	public InvalidParameterException(String errorMsg, Throwable th){
		super(errorMsg,th);
	}
}
