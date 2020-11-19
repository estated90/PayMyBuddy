package com.application.paymybuddy.dto;

import java.time.LocalDateTime;


/**
 * @author nicolas
 *
 */
public class ReturnMovement {

	private String bankName;
	private String transactionDescription;
	private LocalDateTime createdAt;
	private double amount;
	/**
	 * 
	 */
	public ReturnMovement() {
		//default constructor
	}
	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}
	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	/**
	 * @return the transactionDescription
	 */
	public String getTransactionDescription() {
		return transactionDescription;
	}
	/**
	 * @param transactionDescription the transactionDescription to set
	 */
	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
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
	
}
