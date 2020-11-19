package com.application.paymybuddy.dto;

import java.time.LocalDateTime;


/**
 * @author nicolas
 *
 */
public class ReturnTransaction {

	private String user;
	private String toUser;
	private String description;
	private LocalDateTime createdAt;
	private double amount;
	private double fees;
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * @return the toUser
	 */
	public String getToUser() {
		return toUser;
	}
	/**
	 * @param toUser the toUser to set
	 */
	public void setToUser(String toUser) {
		this.toUser = toUser;
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
	 * @return the createdAt
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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
	/**
	 * @return the fees
	 */
	public double getFees() {
		return fees;
	}
	/**
	 * @param fees the fees to set
	 */
	public void setFees(double fees) {
		this.fees = fees;
	}
	
	
}
