package com.application.paymybuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author nicolas
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConnectionsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5068537330691633128L;

	/**
	 * @param message
	 */
	public ConnectionsException(String message) {
		super(message);
	}
	
}
