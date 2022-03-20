package com.chetan03tutorial.libs.aligator.exception;

public class AligatorBddException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AligatorBddException(String errorMsg) {
		super(errorMsg);
	}

	public AligatorBddException(String errorMsg, Throwable th){
		super(errorMsg,th);
	}
}
