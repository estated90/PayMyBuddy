package com.payMyBudy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ServiceHolderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceHolderException(String message) {
		super(message);
	}
}
