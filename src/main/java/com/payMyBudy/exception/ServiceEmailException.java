package com.payMyBudy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ServiceEmailException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8701260326212400049L;

	public ServiceEmailException(String message) {
		super(message);
	}
}
