package com.application.paymybuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author nicolas
 *
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ServiceConnectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7436594807470720935L;

	/**
	 * @param message
	 */
	public ServiceConnectionException(String message) {
		super(message);
	}
	
}
