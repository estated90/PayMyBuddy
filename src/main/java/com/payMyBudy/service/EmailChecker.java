package com.payMyBudy.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.payMyBudy.exception.ServiceEmailException;

@Service
public class EmailChecker {

	public void validateMail(String email) throws ServiceEmailException {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		if (m.matches() == false)
			throw new ServiceEmailException("String provided is not an email");
	}
}
