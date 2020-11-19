package com.application.paymybuddy.dto;

/**
 * @author nicolas
 *
 */
public class Solde {

	private String account;
	private double totalMovement;
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the solde
	 */
	public double getSolde() {
		return totalMovement;
	}
	/**
	 * @param amount the solde to set
	 */
	public void setSolde(double amount) {
		this.totalMovement = amount;
	}
	
	
}
