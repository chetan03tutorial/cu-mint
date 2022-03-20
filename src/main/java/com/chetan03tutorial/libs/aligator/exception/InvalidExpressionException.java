package com.chetan03tutorial.libs.aligator.exception;

public class InvalidExpressionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidExpressionException(String errorMsg) {
		super(errorMsg);
	}

	public InvalidExpressionException(String errorMsg, Throwable th){
		super(errorMsg,th);
	}
}
