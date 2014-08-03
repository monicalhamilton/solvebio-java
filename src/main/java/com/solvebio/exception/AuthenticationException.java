package com.solvebio.exception;

public class AuthenticationException extends APIException {

	public AuthenticationException(String message, int code) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
