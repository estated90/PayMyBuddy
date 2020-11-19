package com.paymybuddy.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateTransaction {
	@Email
	@NotNull
	private String friendEmail;
	@Size(max=50)
	@NotNull
	private String description;
	@NotNull
	private double amount;
	
	/**
	 * @param friendEmail
	 * @param description
	 * @param amount
	 */
	public CreateTransaction(@Email @NotNull String friendEmail, @Size(max = 50) @NotNull String description,
			@NotNull double amount) {
		super();
		this.friendEmail = friendEmail;
		this.description = description;
		this.amount = amount;
	}
	/**
	 * @return the friendEmail
	 */
	public String getFriendEmail() {
		return friendEmail;
	}
	/**
	 * @param friendEmail the friendEmail to set
	 */
	public void setFriendEmail(String friendEmail) {
		this.friendEmail = friendEmail;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	
}
