package com.solvebio.exception;

public class InvalidRequestException extends APIException {

	private static final long serialVersionUID = 1L;

	public InvalidRequestException(String message, Throwable e) {
		super(message, e);
	}
	
	public InvalidRequestException(String message, int code, Throwable e) {
		super(message, code, e);
	}
}
