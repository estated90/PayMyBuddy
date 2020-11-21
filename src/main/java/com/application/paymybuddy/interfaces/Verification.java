package com.application.paymybuddy.interfaces;

import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.exception.ServiceMovementException;
import com.application.paymybuddy.model.Holder;

public interface Verification {

	/**
	 * @param email
	 * @throws ServiceEmailException
	 */
	void validateMail(String email) throws ServiceEmailException;

	/**
	 * @param email
	 * @return
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 */
	Holder verificationOfData(String email) throws ServiceEmailException, ServiceHolderException;

	/**
	 * @param holder
	 * @param amount
	 * @throws ServiceMovementException
	 */
	void verifyMovementAuthorized(Holder holder, double amount) throws ServiceMovementException;

}