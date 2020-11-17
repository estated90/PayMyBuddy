package com.payMyBudy.dto;

import com.googlecode.jmapper.annotations.JMap;

/**
 * @author nicolas
 *
 */
public class EditProfile {

	private String email;
	private String password;
	@JMap
	private String firstName;
	@JMap
	private String lastName;
	@JMap
	private String address;
	@JMap
	private String phone;

	/**
	 * 
	 */
	public EditProfile() {
		super();
	}
	
	

	/**
	 * @param email
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param phone
	 */
	public EditProfile(String email, String password, String firstName, String lastName, String address, String phone) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
	}


	/**
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param phone
	 */
	public EditProfile(String firstName, String lastName, String address, String phone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
	}

	
	/**
	 * @param email
	 * @param password
	 */
	public EditProfile(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
