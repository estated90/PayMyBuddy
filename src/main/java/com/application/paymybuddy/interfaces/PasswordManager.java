package com.application.paymybuddy.interfaces;

/**
 * @author nicol
 *
 */
public interface PasswordManager {

	/**
	 * @param passwordToEncode
	 * @return
	 */
	String passwordEncoder(String passwordToEncode);

	/**
	 * @param password
	 * @param encodedPassword
	 * @return
	 */
	boolean passwordDecoder(String password, String encodedPassword);

}