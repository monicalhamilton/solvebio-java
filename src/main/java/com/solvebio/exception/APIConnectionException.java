package com.solvebio.exception;

public class APIConnectionException extends APIException {

	private static final long serialVersionUID = 1L;

	public APIConnectionException(String message) {
		super(message);
	}

	public APIConnectionException(String message, Throwable e) {
		super(message, e);
	}

}
