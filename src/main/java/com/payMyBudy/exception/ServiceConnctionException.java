package com.payMyBudy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ServiceConnctionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7436594807470720935L;

	public ServiceConnctionException(String message) {
		super(message);
	}
	
}
