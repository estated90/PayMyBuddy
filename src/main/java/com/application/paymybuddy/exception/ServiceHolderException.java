package com.application.paymybuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author nicolas
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ServiceHolderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public ServiceHolderException(String message) {
		super(message);
	}
}
