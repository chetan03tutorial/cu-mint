package com.chetan03tutorial.libs.aligator.exception;

public class GherkinParameterFormatException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public GherkinParameterFormatException(String errorMsg) {
		super(errorMsg);
	}

	public GherkinParameterFormatException(String errorMsg, Throwable th){
		super(errorMsg,th);
	}
}
