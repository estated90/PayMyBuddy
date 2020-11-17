package com.payMyBudy.interfaces;

import org.springframework.stereotype.Service;

import com.payMyBudy.exception.ServiceConnectionException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.exception.ServiceHolderException;
import com.payMyBudy.model.Holder;

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
	 */
	public Holder connection(String email, String password) throws ServiceEmailException, ServiceConnectionException;
	
}