package com.application.paymybuddy.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.paymybuddy.dao.HolderDao;
import com.application.paymybuddy.dao.MovementDao;
import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.exception.ServiceMovementException;
import com.application.paymybuddy.model.Holder;

/**
 * @author nicolas
 *
 */
@Service
public class Verification {

	@Autowired
	private HolderDao holderDao;
	@Autowired
	private MovementDao movementDao;
	
	private static final Logger logger = LogManager.getLogger("Verification");
	
	/**
	 * @param email
	 * @throws ServiceEmailException
	 */
	public void validateMail(String email) throws ServiceEmailException {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		if (!m.matches())
			throw new ServiceEmailException("String provided is not an email");
	}
	
	public Holder verificationOfData(String email) throws ServiceEmailException, ServiceHolderException {
		validateMail(email);
		Holder holder = holderDao.findByEmail(email);
		if ((holder == null)) {
			logger.error("email has not been found in db: {}", email);
			throw new ServiceHolderException("Email not found");
		}
		return holder;
	}
	
	public void verifyMovementAuthorized(Holder holder, double amount) throws ServiceMovementException {
		double solde = (double) Math.round(movementDao.sumAmounts(holder) * 100) / 100;
		if ((amount < 0) && ((solde + amount) < 0)) {
			logger.error("The amount to sent ({}) is too high for the solde of the account ({})", amount, solde);
			throw new ServiceMovementException("Amount to sent is higher than the solde of the account");
		}
	}
}
