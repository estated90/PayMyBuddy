package com.payMyBudy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author nicolas
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServiceBankException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public ServiceBankException(String message) {
		super(message);
	}
}
