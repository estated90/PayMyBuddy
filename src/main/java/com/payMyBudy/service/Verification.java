package com.payMyBudy.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.exception.ServiceHolderException;
import com.payMyBudy.model.Holder;

/**
 * @author nicolas
 *
 */
@Service
public class Verification {

	@Autowired
	private HolderDao holderDao;
	
	private static final Logger logger = LogManager.getLogger("Verification");
	
	/**
	 * @param email
	 * @throws ServiceEmailException
	 */
	public void validateMail(String email) throws ServiceEmailException {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		if (m.matches() == false)
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
}
