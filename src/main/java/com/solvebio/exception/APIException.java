package com.solvebio.exception;

public class APIException extends Exception {

	public APIException(String message) {
		super(message, null);
	}

	public APIException(String message, Throwable e) {
		super(message, e);
	}

	public APIException(String message, int code, Throwable e) {
		super(getMessage(message, code), e);
	}
	
	private static String getMessage(String message, int code) {
		return String.format("%s: %s", code, message);
	}

	private static final long serialVersionUID = 1L;

}
