package com.application.paymybuddy.interfaces;

import org.springframework.stereotype.Service;

import com.application.paymybuddy.exception.ServiceConnectionException;
import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.model.Holder;

/**
 * @author nicolas
 *
 */
@Service
public interface HolderServices {

	/**
	 * @param email
	 * @return Holder
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 */
	public Holder createHolder(String email) throws ServiceEmailException, ServiceHolderException;

	/**
	 * @param email
	 * @param password
	 * @return Holder
	 * @throws ServiceEmailException
	 * @throws ServiceConnectionException
	 * @throws ServiceHolderException 
	 */
	public Holder connection(String email, String password) throws ServiceEmailException, ServiceConnectionException, ServiceHolderException;

	/**
	 * @param email
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 */
	void deleteHolder(String email) throws ServiceEmailException, ServiceHolderException;
	
}