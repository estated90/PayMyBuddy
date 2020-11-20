package com.application.paymybuddy.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordManager {
	
	public String passwordEncoder(String passwordToEncode) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordToEncode;
		String encodedPassword = passwordEncoder.encode(password);
		return encodedPassword;
	}
	
	public boolean passwordDecoder(String password, String encodedPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(password, encodedPassword);
	}
	
}
