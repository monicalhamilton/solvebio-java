package com.solvebio.exception;

public class APIException extends Exception {

	public APIException(String message) {
		super(message, null);
	}

	public APIException(String message, Throwable e) {
		super(message, e);
	}

	private static final long serialVersionUID = 1L;

}
